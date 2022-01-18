package com.momsitter.ui.dto.account;

import com.momsitter.domain.SitterInfo;

public class SitterInfoResponse {
    private Long id;
    private int minCareAge;
    private int maxCareAge;
    private String aboutMe;

    protected SitterInfoResponse() {
    }

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

    public Long getId() {
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
