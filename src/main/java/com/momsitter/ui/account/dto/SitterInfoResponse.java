package com.momsitter.ui.account.dto;

public class SitterInfoResponse {
    private final int minCareAge;
    private final int maxCareAge;
    private final String aboutMe;

    public SitterInfoResponse(int minCareAge, int maxCareAge, String aboutMe) {
        this.minCareAge = minCareAge;
        this.maxCareAge = maxCareAge;
        this.aboutMe = aboutMe;
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
