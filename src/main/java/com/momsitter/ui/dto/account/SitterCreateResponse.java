package com.momsitter.ui.dto.account;

import com.momsitter.domain.Account;
import com.momsitter.domain.SitterInfo;

public class SitterCreateResponse {
    private AccountResponse account;
    private SitterInfoResponse sitterInfo;

    protected SitterCreateResponse() {
    }

    public SitterCreateResponse(AccountResponse account, SitterInfoResponse sitterInfo) {
        this.account = account;
        this.sitterInfo = sitterInfo;
    }

    public static SitterCreateResponse of(Account account) {
        SitterInfo sitterInfo = account.getSitterInfo();
        return new SitterCreateResponse(
                AccountResponse.of(account),
                new SitterInfoResponse(sitterInfo.getId(), sitterInfo.getCareAgeRange().getMinAge(),
                        sitterInfo.getCareAgeRange().getMaxAge(), sitterInfo.getAboutMe())
        );
    }


    public AccountResponse getAccount() {
        return account;
    }

    public SitterInfoResponse getSitterInfo() {
        return sitterInfo;
    }
}
