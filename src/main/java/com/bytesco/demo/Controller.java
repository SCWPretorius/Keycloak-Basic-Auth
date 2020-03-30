package com.bytesco.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class Controller {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${config.keycloak.secret}")
    private String clientSecret;
    @Value("${config.keycloak.client}")
    private String clientId;
    @Value("${config.keycloak.tokenUrl}")
    private String keycloakTokenUrl;

    @GetMapping("sec")
    public String getSecure() {

        return "secured";
    }

    @GetMapping("pub")
    public String getPublic() {

        return "public";
    }

    @PostMapping("login")
    public TokenModel login(@RequestParam("username") String username,
                            @RequestParam("password") String password) {

        return webClientBuilder.build()
                .post()
                .uri(keycloakTokenUrl)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(BodyInserters.fromFormData("client_id", clientId)
                    .with("username", username)
                    .with("password", password)
                    .with("grant_type", "password")
                    .with("client_secret", clientSecret))
                .retrieve()
                .bodyToMono(TokenModel.class)
                .block();
    }
}
