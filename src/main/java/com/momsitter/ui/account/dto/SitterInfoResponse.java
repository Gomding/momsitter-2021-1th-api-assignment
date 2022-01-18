package com.momsitter.ui.account.dto;

import com.momsitter.domain.SitterInfo;

public class SitterInfoResponse {
    private final Long id;
    private final int minCareAge;
    private final int maxCareAge;
    private final String aboutMe;

    public SitterInfoResponse(Long id, int minCareAge, int maxCareAge, String aboutMe) {
        this.id = id;
        this.minCareAge = minCareAge;
        this.maxCareAge = maxCareAge;
        this.aboutMe = aboutMe;
    }

    public static SitterInfoResponse of(SitterInfo sitterInfo) {
        return new SitterInfoResponse(sitterInfo.getId(), sitterInfo.getCareAgeRange().getMinAge(),
                sitterInfo.getCareAgeRange().getMaxAge(), sitterInfo.getAboutMe());
    }

    public long getId() {
        return id;
    }

    public int getMinCareAge() {
        return minCareAge;
    }

    public int getMaxCareAge() {
        return maxCareAge;
    }

    public String getAboutMe() {
        return aboutMe;
    }
}
