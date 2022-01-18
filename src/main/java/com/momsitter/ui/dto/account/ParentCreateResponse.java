package com.momsitter.ui.dto.account;

import com.momsitter.domain.Account;

public class ParentCreateResponse {
    private AccountResponse account;
    private ParentInfoResponse parentInfo;

    public ParentCreateResponse(AccountResponse account, ParentInfoResponse parentInfo) {
        this.account = account;
        this.parentInfo = parentInfo;
    }

    public static ParentCreateResponse of(Account account) {
        return new ParentCreateResponse(
                AccountResponse.of(account),
                ParentInfoResponse.of(account.getParentInfo())
        );
    }

    public AccountResponse getAccount() {
        return account;
    }

    public ParentInfoResponse getParentInfo() {
        return parentInfo;
    }
}
