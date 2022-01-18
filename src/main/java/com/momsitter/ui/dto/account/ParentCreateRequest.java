package com.momsitter.ui.dto.account;

public class ParentCreateRequest {
    private AccountCreateRequest account;
    private ParentInfoRequest parentInfo;

    protected ParentCreateRequest() {
    }

    public ParentCreateRequest(AccountCreateRequest account, ParentInfoRequest parentInfo) {
        this.account = account;
        this.parentInfo = parentInfo;
    }

    public AccountCreateRequest getAccount() {
        return account;
    }

    public ParentInfoRequest getParentInfo() {
        return parentInfo;
    }
}
