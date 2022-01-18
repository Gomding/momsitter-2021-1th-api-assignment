package com.momsitter.ui.account.dto;

import com.momsitter.domain.Child;
import com.momsitter.domain.DateOfBirth;
import com.momsitter.domain.Gender;

import java.time.LocalDate;

public class ChildRequest {
    private LocalDate dateOfBirth;
    private String gender;

    protected ChildRequest() {
    }

    public ChildRequest(LocalDate dateOfBirth, String gender) {
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public Child toEntity() {
        return new Child(new DateOfBirth(dateOfBirth), Gender.fromGenderName(gender));
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }
}
