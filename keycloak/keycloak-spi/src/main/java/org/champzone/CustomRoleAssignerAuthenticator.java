package org.champzone;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import org.jboss.logging.Logger;

public class CustomRoleAssignerAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(CustomRoleAssignerAuthenticator.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        UserModel user = context.getUser();
        String userRole = user.getFirstAttribute("userRole");

        if (userRole == null) {
            logger.warn("User attribute 'user_role' is missing. Authentication failed.");
            context.failure(AuthenticationFlowError.INVALID_USER);
            return;
        }

        try {
            switch (userRole.toUpperCase()) {
                case "COACH":
                    assignRoleToUser(context, user, "COACH");
                    break;
                case "CLIENT":
                    assignRoleToUser(context, user, "CLIENT");
                    break;
                default:
                    logger.warnf("Unknown user role '%s'. Authentication failed.", userRole);
                    context.failure(AuthenticationFlowError.INVALID_USER);
                    return;
            }
            context.success();
        } catch (Exception e) {
            logger.error("Error assigning role to user", e);
            context.failure(AuthenticationFlowError.INTERNAL_ERROR);
        }
    }

    private void assignRoleToUser(AuthenticationFlowContext context, UserModel user, String roleName) {
        KeycloakSession session = context.getSession();
        RealmModel realm = context.getRealm();

        var role = realm.getRole(roleName);
        if (role == null) {
            logger.errorf("Role '%s' not found in realm '%s'.", roleName, realm.getName());
            throw new IllegalArgumentException("Role not found");
        }

        user.grantRole(role);
        logger.infof("Role '%s' assigned to user '%s'.", roleName, user.getUsername());
    }

    @Override
    public void action(AuthenticationFlowContext context) {
    }

    @Override
    public void close() {
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
    }
}
