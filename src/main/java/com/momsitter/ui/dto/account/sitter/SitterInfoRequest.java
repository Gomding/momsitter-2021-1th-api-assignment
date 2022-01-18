package com.momsitter.ui.dto.account.sitter;

import com.momsitter.domain.CareAgeRange;
import com.momsitter.domain.SitterInfo;

public class SitterInfoRequest {
    private int minAge;
    private int maxAge;
    private String aboutMe;

    protected SitterInfoRequest() {
    }

    public SitterInfoRequest(int minAge, int maxAge, String aboutMe) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.aboutMe = aboutMe;
    }

    public SitterInfo toEntity() {
        return new SitterInfo(new CareAgeRange(minAge, maxAge), aboutMe);
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public String getAboutMe() {
        return aboutMe;
    }
}
