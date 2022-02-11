package com.momsitter.service.dto;

import com.momsitter.domain.Account;
import com.momsitter.service.dto.parent.ParentInfoResponse;
import com.momsitter.service.dto.sitter.SitterInfoResponse;

public class AccountInfoResponse {
    private AccountResponse account;
    private SitterInfoResponse sitter;
    private ParentInfoResponse parent;
    private boolean sitterAccount;
    private boolean parentAccount;

    protected AccountInfoResponse() {
    }

    public AccountInfoResponse(AccountResponse account, SitterInfoResponse sitter, ParentInfoResponse parent, boolean sitterAccount, Boolean parentAccount) {
        this.account = account;
        this.sitter = sitter;
        this.parent = parent;
        this.sitterAccount = sitterAccount;
        this.parentAccount = parentAccount;
    }

    public static AccountInfoResponse of(Account account) {
        AccountResponse accountResponse = AccountResponse.of(account);
        SitterInfoResponse sitterInfoResponse = null;
        ParentInfoResponse parentInfoResponse = null;
        if (account.isSitter())
            sitterInfoResponse = SitterInfoResponse.of(account.getSitterInfo());
        if (account.isParent())
            parentInfoResponse = ParentInfoResponse.of(account.getParentInfo());
        return new AccountInfoResponse(
                accountResponse,
                sitterInfoResponse,
                parentInfoResponse,
                account.isSitter(),
                account.isParent()
        );
    }

    public AccountResponse getAccount() {
        return account;
    }

    public SitterInfoResponse getSitter() {
        return sitter;
    }

    public ParentInfoResponse getParent() {
        return parent;
    }

    public boolean isSitterAccount() {
        return sitterAccount;
    }

    public boolean isParentAccount() {
        return parentAccount;
    }
}
