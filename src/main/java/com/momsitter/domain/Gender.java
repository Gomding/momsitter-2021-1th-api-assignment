package com.momsitter.domain;

public enum Gender {
    MALE("남"),
    FEMALE("여");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
