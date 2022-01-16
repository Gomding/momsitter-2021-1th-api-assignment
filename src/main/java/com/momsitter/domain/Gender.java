package com.momsitter.domain;

public enum Gender {
    MALE("남"),
    FEMALE("여");

    private final String genderName;

    Gender(String genderName) {
        this.genderName = genderName;
    }

    public String getGenderName() {
        return genderName;
    }
}
