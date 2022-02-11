package com.momsitter.service.dto.sitter;

import com.momsitter.service.dto.AccountCreateRequest;

public class SitterCreateRequest {
    AccountCreateRequest account;
    private SitterInfoRequest sitterInfo;

    protected SitterCreateRequest() {
    }

    public SitterCreateRequest(AccountCreateRequest account, SitterInfoRequest sitterInfo) {
        this.account = account;
        this.sitterInfo = sitterInfo;
    }

    public AccountCreateRequest getAccount() {
        return account;
    }

    public SitterInfoRequest getSitterInfo() {
        return sitterInfo;
    }
}
