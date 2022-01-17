package com.momsitter.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public class Password {
    public static final String NULL_EXCEPTION_MESSAGE = "비밀번호는 null일 수 없습니다.";
    public static final String LENGTH_EXCEPTION_MESSAGE = "비밀번호는 6글자 이상 16글자 이하여야 합니다.";
    public static final String BLANK_EXCEPTION_MESSAGE = "비밀번호는 공백을 포함할 수 없습니다.";
    public static final String PATTERN_EXCEPTION_MESSAGE = "비밀번호는 영어,숫자,특수문자 3가지로 구성되어야 합니다.";

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 16;
    private static final String BLANK = " ";
    private static final Pattern PATTERN =
            Pattern.compile("^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\\-_=+])(?!.*[^a-zA-z0-9$`~!@$!%*#^?&\\(\\)\\-_=+]).*$");

    private final String value;

    public Password(String value) {
        this.value = Objects.requireNonNull(value, NULL_EXCEPTION_MESSAGE);
        validateLength(this.value.length());
        validateContainsBlank(this.value);
        validatePattern(this.value);
    }

    private void validateContainsBlank(String value) {
        if (value.contains(BLANK))
            throw new IllegalArgumentException(BLANK_EXCEPTION_MESSAGE);
    }

    private void validateLength(int length) {
        if (length < MIN_LENGTH || length > MAX_LENGTH)
            throw new IllegalArgumentException(LENGTH_EXCEPTION_MESSAGE);
    }

    private void validatePattern(String value) {
        if (!PATTERN.matcher(value).matches())
            throw new IllegalArgumentException(PATTERN_EXCEPTION_MESSAGE);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
