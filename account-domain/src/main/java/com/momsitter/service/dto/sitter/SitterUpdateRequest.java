package com.momsitter.service.dto.sitter;

public class SitterUpdateRequest {
    private int minCareAge;
    private int maxCareAge;
    private String aboutMe;

    protected SitterUpdateRequest() {
    }

    public SitterUpdateRequest(int minCareAge, int maxCareAge, String aboutMe) {
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
