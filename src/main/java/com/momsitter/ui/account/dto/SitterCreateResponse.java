package com.momsitter.ui.account.dto;

import com.momsitter.domain.Account;
import com.momsitter.domain.SitterInfo;

import java.time.LocalDate;

public class SitterCreateResponse {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String accountId;
    private String password;
    private String email;
    private SitterInfoResponse sitterInfo;

    public SitterCreateResponse(Long id, String name, LocalDate dateOfBirth, String gender, String accountId,
                                String password, String email, SitterInfoResponse sitterInfo) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.accountId = accountId;
        this.password = password;
        this.email = email;
        this.sitterInfo = sitterInfo;
    }

    public static SitterCreateResponse of(Account account) {
        SitterInfo sitterInfo = account.getSitterInfo();
        return new SitterCreateResponse(
                account.getId(),
                account.getName().getValue(),
                account.getDateOfBirth().getValue(),
                account.getGender().getGenderName(),
                account.getAccountId().getValue(),
                account.getPassword().getValue(),
                account.getEmail().getValue(),
                new SitterInfoResponse(sitterInfo.getCareAgeRange().getMinAge(),
                        sitterInfo.getCareAgeRange().getMaxAge(), sitterInfo.getAboutMe())
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public SitterInfoResponse getSitterInfo() {
        return sitterInfo;
    }
}
