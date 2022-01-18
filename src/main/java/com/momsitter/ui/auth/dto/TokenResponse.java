package com.momsitter.ui.auth.dto;

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