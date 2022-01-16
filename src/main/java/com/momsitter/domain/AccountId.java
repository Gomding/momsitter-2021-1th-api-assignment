package com.momsitter.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public class AccountId {
    public static final String NULL_EXCEPTION_MESSAGE = "사용자의 계정 ID는 null일 수 없습니다.";
    public static final String LENGTH_EXCEPTION_MESSAGE_FORMAT = "사용자의 계정 ID는 6글자 이상 20글자 이하여야 합니다. (현재 길이 : %d)";
    public static final String BLANK_EXCEPTION_MESSAGE = "사용자의 계정 ID는 공백을 포함할 수 없습니다.";
    public static final String PATTERN_EXCEPTION_MESSAGE = "사용자의 계정 ID는 영어 또는 숫자만 입력 가능합니다.";
    public static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9]*$");
    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 20;
    private static final String BLANK = " ";

    private final String value;

    public AccountId(String value) {
        this.value = Objects.requireNonNull(value, NULL_EXCEPTION_MESSAGE);
        validateLength(this.value.length());
        validateContainBlank(this.value);
        validatePattern(this.value);
    }

    private void validatePattern(String value) {
        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(PATTERN_EXCEPTION_MESSAGE);
        }
    }

    private void validateLength(int length) {
        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            throw new IllegalArgumentException(String.format(LENGTH_EXCEPTION_MESSAGE_FORMAT, length));
        }
    }

    private void validateContainBlank(String value) {
        if (value.contains(BLANK)) {
            throw new IllegalArgumentException(BLANK_EXCEPTION_MESSAGE);
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountId accountId = (AccountId) o;
        return Objects.equals(value, accountId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
