package com.momsitter.authiorization.ui.dto.auth;

public class TokenRequest {

    private String accountId;
    private String password;

    protected TokenRequest() {
    }

    public TokenRequest(String accountId, String password) {
        this.accountId = accountId;
        this.password = password;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getPassword() {
        return password;
    }
}
