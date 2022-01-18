package com.momsitter.ui.dto.account;

public class AccountUpdateRequest {
    private String gender;
    private String password;
    private String email;

    protected AccountUpdateRequest() {
    }

    public AccountUpdateRequest(String gender, String password, String email) {
        this.gender = gender;
        this.password = password;
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
