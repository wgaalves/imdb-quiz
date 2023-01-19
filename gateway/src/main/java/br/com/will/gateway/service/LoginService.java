package br.com.will.gateway.service;

import br.com.will.gateway.client.KeycloakClient;
import br.com.will.gateway.client.dto.LoginDTO;
import br.com.will.gateway.client.dto.LoginResponseDTO;
import br.com.will.gateway.config.Oauth2Config;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class LoginService {

    private final KeycloakClient keycloakClient;
    private final Oauth2Config oauth2Config;

    public LoginResponseDTO login(LoginDTO loginDTO){
        Map<String, Object> loginCredentials = new HashMap<>();
        loginCredentials.put("username", loginDTO.getUsername());
        loginCredentials.put("password", loginDTO.getPassword());
        loginCredentials.put("client_id", oauth2Config.getClient());
        loginCredentials.put("client_secret", oauth2Config.getSecret());
        loginCredentials.put("grant_type", "password");
        return keycloakClient.login(loginCredentials);

    }
}
