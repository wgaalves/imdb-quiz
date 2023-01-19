package br.com.will.gateway;

import br.com.will.gateway.client.KeycloakClient;
import br.com.will.gateway.client.dto.LoginDTO;
import br.com.will.gateway.client.dto.LoginResponseDTO;
import br.com.will.gateway.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.web.servlet.OAuth2LoginDsl;
import org.springframework.security.oauth2.client.OAuth2AuthorizationContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import javax.annotation.security.PermitAll;

@Slf4j
@EnableFeignClients
@RestController
@SpringBootApplication
public class GatewayApplication {

    @Autowired
    private LoginService loginService;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @PermitAll
    @PostMapping(value = "/login", consumes = "application/json")
    public Mono<LoginResponseDTO> getHome(@RequestBody LoginDTO loginDTO) {
        log.info(loginDTO.toString());
        return Mono.just(loginService.login(loginDTO));
    }


    @GetMapping(value = "/token")
    public Mono<String> getHome(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        return Mono.just(authorizedClient.getAccessToken().getTokenValue());
    }

    @GetMapping("/")
    public Mono<String> index(WebSession session) {
        return Mono.just(session.getId());
    }

}
