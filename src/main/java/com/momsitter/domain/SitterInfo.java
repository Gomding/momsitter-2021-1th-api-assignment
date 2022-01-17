package com.momsitter.domain;

import java.util.Objects;

public class SitterInfo {
    public static final String INVALID_CARE_AGE_MESSAGE = "케어 가능한 연령의 최소 연령은 최대 연령보다 작거나 같아야합니다.";
    public static final String ABOUT_ME_NULL_EXCEPTION_MESSAGE = "자기 소개는 null이 될 수 없습니다.";

    private Long id;
    private int minAgeOfCaringChild;
    private int maxAgeOfCaringChild;
    private String aboutMe;

    protected SitterInfo() {
    }

    public SitterInfo(int minAgeOfCaringChild, int maxAgeOfCaringChild, String aboutMe) {
        this(null, minAgeOfCaringChild, maxAgeOfCaringChild, aboutMe);
    }

    public SitterInfo(Long id, int minAgeOfCaringChild, int maxAgeOfCaringChild, String aboutMe) {
        this.id = id;
        this.minAgeOfCaringChild = minAgeOfCaringChild;
        this.maxAgeOfCaringChild = maxAgeOfCaringChild;
        this.aboutMe = Objects.requireNonNull(aboutMe, ABOUT_ME_NULL_EXCEPTION_MESSAGE);
        validateAgeOfCaringChild(this.maxAgeOfCaringChild, this.minAgeOfCaringChild);
    }

    private void validateAgeOfCaringChild(int maxAgeOfCaringChild, int minAgeOfCaringChild) {
        if (minAgeOfCaringChild > maxAgeOfCaringChild)
            throw new IllegalArgumentException(INVALID_CARE_AGE_MESSAGE);
    }

    public int getMinAgeOfCaringChild() {
        return minAgeOfCaringChild;
    }

    public int getMaxAgeOfCaringChild() {
        return maxAgeOfCaringChild;
    }

    public String getAboutMe() {
        return aboutMe;
    }
}
