package com.bytesco.demo;

import lombok.Data;

@Data
public class TokenModel {

    private String access_token;
    private String expires_in;
    private String refresh_expires_in;
    private String refresh_token;
    private String token_type;

}
