package com.momsitter.service.dto.parent;

import com.momsitter.domain.Account;
import com.momsitter.domain.ParentInfo;
import com.momsitter.service.dto.AccountCreateRequest;

public class ParentCreateRequest {
    private AccountCreateRequest account;
    private ParentInfoRequest parentInfo;

    protected ParentCreateRequest() {
    }

    public ParentCreateRequest(AccountCreateRequest account, ParentInfoRequest parentInfo) {
        this.account = account;
        this.parentInfo = parentInfo;
    }

    public Account toAccount() {
        return account.toEntity();
    }

    public ParentInfo toParentInfo() {
        return parentInfo.toEntity();
    }

    public AccountCreateRequest getAccount() {
        return account;
    }

    public ParentInfoRequest getParentInfo() {
        return parentInfo;
    }
}
