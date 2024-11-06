package org.champzone.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class RegisterEventListenerProvider implements EventListenerProvider {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RegisterEventListenerProvider.class);
    private final KeycloakSession session;

    private static final Logger logger = Logger.getLogger(RegisterEventListenerProviderFactory.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RegisterEventListenerProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void onEvent(Event event) {
        if (event.getType() == EventType.REGISTER) {
            handleUserRegistration(event.getUserId());
        } else if (event.getType() == EventType.DELETE_ACCOUNT) {
            handleUserDeletion(event.getUserId());
        }
    }

    private void handleUserRegistration(String userId) {
        try {
            UserModel user = session.users().getUserById(session.getContext().getRealm(), userId);
            String userRole = user.getFirstAttribute("userRole");
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String email = user.getEmail();

            Map<String, String> requestData = Map.of(
                    "userId", userId,
                    "firstName", firstName,
                    "lastName", lastName,
                    "email", email
            );

            log.debug("Handling registration for userId: " + userId + " with data: " + mapToJson(requestData));

            sendRequestToApiGateway(userRole, requestData, "register");
        } catch (Exception e) {
            logger.error("Error handling user registration for userId: " + userId, e);
        }
    }

    private void handleUserDeletion(String userId) {
        try {
            Map<String, String> requestData = Map.of("userId", userId);
            log.debug("Handling deletion for userId: " + userId + " with data: " + mapToJson(requestData));
            sendRequestToApiGateway(null, requestData, "delete");
        } catch (Exception e) {
            logger.error("Error handling user deletion for userId: " + userId, e);
        }
    }

    private void sendRequestToApiGateway(String userRole, Map<String, String> requestData, String action) throws IOException, InterruptedException {
        String requestBody = mapToJson(requestData);
        HttpRequest request;
        String url;

        if ("register".equals(action)) {
            if ("COACH".equalsIgnoreCase(userRole)) {
                url = "http://api-gateway:8080/api/v1/coach/register";
            } else if ("CLIENT".equalsIgnoreCase(userRole)) {
                url = "http://api-gateway:8080/api/v1/client/register";
            } else {
                logger.info("User role not recognized for userId: " + requestData.get("userId"));
                return;
            }
        } else if ("delete".equals(action)) {
            url = "http://api-gateway:8080/api/v1/user/delete";
        } else {
            logger.info("Action not recognized for userId: " + requestData.get("userId"));
            return;
        }

        request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        log.debug("Sending " + action + " request to API Gateway with URL: " + url);
        log.debug("Request data: " + request.toString());
        log.debug("Request body: " + requestBody);

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        logger.info("Response status for " + action + ": " + response.statusCode());
        logger.info("Response body for " + action + ": " + response.body());
    }

    private String mapToJson(Map<String, String> map) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.error("Error converting map to JSON", e);
            return map.toString();
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
    }

    @Override
    public void close() {
    }
}
