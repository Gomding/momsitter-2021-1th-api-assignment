package com.momsitter.ui.dto.account.parent;

import java.time.LocalDate;

public class ChildUpdateRequest {
    private Long id;
    private LocalDate dateOfBirth;
    private String gender;

    protected ChildUpdateRequest() {
    }

    public ChildUpdateRequest(Long id, LocalDate dateOfBirth, String gender) {
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
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
