package com.momsitter.ui.account.dto;

import com.momsitter.domain.Child;

import java.time.LocalDate;

public class ChildResponse {
    private final Long id;
    private final LocalDate dateOfBirth;
    private final String gender;

    public ChildResponse(Long id, LocalDate dateOfBirth, String gender) {
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public static ChildResponse of(Child child) {
        return new ChildResponse(child.getId(), child.getDateOfBirth().getValue(), child.getGender().getGenderName());
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }
}
