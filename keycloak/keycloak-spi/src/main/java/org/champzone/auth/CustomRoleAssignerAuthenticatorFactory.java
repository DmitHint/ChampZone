package org.champzone.auth;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.Collections;
import java.util.List;

import static org.keycloak.provider.ProviderConfigProperty.STRING_TYPE;

public class CustomRoleAssignerAuthenticatorFactory implements AuthenticatorFactory {
    public static final String PROVIDER_ID = "custom-role-assigner";
    static final String CUSTOM_ROLE_ASSIGNER_CONFIG = "custom_role_assigner";
    static final String HELP_TEXT = "Assigns roles to users based on the 'user_role' attribute ('client' or 'coach') specified during registration.";

    @Override
    public Authenticator create(KeycloakSession session) {
        return new CustomRoleAssignerAuthenticator();
    }

    @Override
    public void init(Config.Scope scope) {
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "Custom Role Assigner Authenticator";
    }

    @Override
    public String getReferenceCategory() {
        return "Role Assignment";
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return new AuthenticationExecutionModel.Requirement[]{
                AuthenticationExecutionModel.Requirement.REQUIRED,
                AuthenticationExecutionModel.Requirement.ALTERNATIVE,
                AuthenticationExecutionModel.Requirement.DISABLED
        };
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getHelpText() {
        return HELP_TEXT;
    }


    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        ProviderConfigProperty name = new ProviderConfigProperty();

        name.setType(STRING_TYPE);
        name.setName(CUSTOM_ROLE_ASSIGNER_CONFIG);
        name.setLabel("HAHAHA");
        name.setHelpText(HELP_TEXT);

        return Collections.singletonList(name);
    }
//    @Override
//    public List<ProviderConfigProperty> getConfigProperties() {
//        return List.of();
//    }
}
