package com.momsitter.ui.dto.account.parent;

import com.momsitter.domain.Child;

import java.time.LocalDate;

public class ChildResponse {
    private Long id;
    private LocalDate dateOfBirth;
    private String gender;

    protected ChildResponse() {
    }

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
