package com.momsitter.domain;

import java.util.Objects;

public class CareAgeRange {
    public static final String NEGATIVE_CARE_AGE_MESSAGE = "케어 가능한 연령은 0보다 크거나 같아야합니다.";
    public static final String INVALID_CARE_AGE_MESSAGE = "케어 가능한 연령의 최소 연령은 최대 연령보다 작거나 같아야합니다.";

    private final int minAge;
    private final int maxAge;

    public CareAgeRange(int minAge, int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        validateAge(this.minAge, this.maxAge);
        validateRange(this.minAge, this.maxAge);
    }

    private void validateAge(int minAge, int maxAge) {
        if (minAge < 0 || maxAge < 0)
            throw new IllegalArgumentException(NEGATIVE_CARE_AGE_MESSAGE);
    }

    private void validateRange(int minAge, int maxAge) {
        if (minAge > maxAge)
            throw new IllegalArgumentException(INVALID_CARE_AGE_MESSAGE);
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CareAgeRange that = (CareAgeRange) o;
        return minAge == that.minAge && maxAge == that.maxAge;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minAge, maxAge);
    }
}
