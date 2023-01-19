package br.com.will.gateway.client;

import br.com.will.gateway.client.dto.LoginResponseDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



import java.util.Map;

@FeignClient(name = "keycloak", url = "keycloak:8080/")
public interface KeycloakClient {

  @Headers("Content-Type: application/x-www-form-urlencoded")
  @PostMapping(
      path = "auth/realms/master/protocol/openid-connect/token",
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  LoginResponseDTO login(@RequestBody Map<String, ?> form) ;

}
