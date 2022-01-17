package com.momsitter.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class SitterInfo {
    public static final String CARE_AGE_RANGE_NULL_EXCEPTION_MESSAGE = "케어 가능 연령 범위는 null이 될 수 없습니다.";
    public static final String ABOUT_ME_NULL_EXCEPTION_MESSAGE = "자기 소개는 null이 될 수 없습니다.";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CareAgeRange careAgeRange;

    @Lob
    private String aboutMe;

    protected SitterInfo() {
    }

    public SitterInfo(CareAgeRange careAgeRange, String aboutMe) {
        this(null, careAgeRange, aboutMe);
    }

    public SitterInfo(Long id, CareAgeRange careAgeRange, String aboutMe) {
        this.id = id;
        this.careAgeRange = Objects.requireNonNull(careAgeRange, CARE_AGE_RANGE_NULL_EXCEPTION_MESSAGE);
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
