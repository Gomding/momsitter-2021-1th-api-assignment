package com.momsitter.service.dto.sitter;

public class SitterAccountUpdateRequest {
    private String name;
    private String email;
    private int minCareAge;
    private int maxCareAge;
    private String aboutMe;

    protected SitterAccountUpdateRequest() {
    }

    public SitterAccountUpdateRequest(String name, String email, int minCareAge, int maxCareAge, String aboutMe) {
        this.name = name;
        this.email = email;
        this.minCareAge = minCareAge;
        this.maxCareAge = maxCareAge;
        this.aboutMe = aboutMe;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
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
