package br.com.will.gateway.client.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class KeyCloakFormDTO {

    @feign.form.FormProperty("username")
    private String username;

    @feign.form.FormProperty("password")
    private String password;

    @feign.form.FormProperty("grant_type")
    @Builder.Default
    private String grant_type ="password";

    @feign.form.FormProperty("client_id")
    @Builder.Default
    private String client_id ="quiz-gateway";

    @feign.form.FormProperty("client_secret")
    @Builder.Default
    private String client_secret = "c6480137-1526-4c3e-aed3-295aabcb7609";
}
