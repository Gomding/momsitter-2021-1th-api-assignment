package com.momsitter.domain;

import java.util.Objects;

public class SitterInfo {
    public static final String ABOUT_ME_NULL_EXCEPTION_MESSAGE = "자기 소개는 null이 될 수 없습니다.";

    private Long id;
    private CareAgeRange careAgeRange;
    private String aboutMe;

    protected SitterInfo() {
    }

    public SitterInfo(CareAgeRange careAgeRange, String aboutMe) {
        this(null, careAgeRange, aboutMe);
    }

    public SitterInfo(Long id, CareAgeRange careAgeRange, String aboutMe) {
        this.id = id;
        this.careAgeRange = careAgeRange;
        this.aboutMe = Objects.requireNonNull(aboutMe, ABOUT_ME_NULL_EXCEPTION_MESSAGE);
    }

    public Long getId() {
        return id;
    }

    public CareAgeRange getCareAgeRange() {
        return careAgeRange;
    }

    public String getAboutMe() {
        return aboutMe;
    }
}
