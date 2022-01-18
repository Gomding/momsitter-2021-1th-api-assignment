package com.momsitter.ui.dto.auth;

public class TokenResponse {

    private String value;

    protected TokenResponse() {
    }

    public TokenResponse(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}