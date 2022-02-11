package com.momsitter.domain;

import java.util.Arrays;

public enum Gender {
    MALE("남"),
    FEMALE("여");

    private final String genderName;

    Gender(String genderName) {
        this.genderName = genderName;
    }

    public static Gender fromGenderName(String genderName) {
        return Arrays.stream(values())
                .filter(gender -> gender.genderName.equals(genderName))
                .findAny().orElseThrow(() -> new IllegalArgumentException("유효한 성별이 아닙니다."));
    }

    public String getGenderName() {
        return genderName;
    }
}
