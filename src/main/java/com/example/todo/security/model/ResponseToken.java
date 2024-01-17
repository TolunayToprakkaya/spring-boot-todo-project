package com.example.todo.security.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseToken {
    private String accessToken;
    private String refreshToken;
    private String error;

    private ResponseToken() {
    }

    private ResponseToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    private ResponseToken(String error) {
        this.error = error;
    }

    public static ResponseToken ok(String accessToken, String refreshToken) {
        return new ResponseToken(accessToken, refreshToken);
    }

    public static ResponseToken error(String error) {
        return new ResponseToken(error);
    }
}
