package com.momsitter.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Email {
    public static final String NULL_EXCEPTION_MESSAGE = "이메일은 null일 수 없습니다.";
    public static final String LOCAL_LENGTH_EXCEPTION_MESSAGE = "이메일의 로컬 부분은 64자를 초과할 수 없습니다.";
    public static final String DOMAIN_LENGTH_EXCEPTION_MESSAGE = "이메일은 도메인 부분은 255자를 초과할 수 없습니다.";
    public static final String BLANK_EXCEPTION_MESSAGE = "이메일은 공백을 포함할 수 없습니다.";
    public static final String PATTERN_EXCEPTION_MESSAGE = "이메일 형식에 맞게 입력해주세요";

    private static final int LOCAL_INDEX = 0;
    private static final int DOMAIN_INDEX = 1;
    private static final int LOCAL_MAX_LENGTH = 64;
    private static final int DOMAIN_MAX_LENGTH = 255;
    private static final String BLANK = " ";
    private static final Pattern PATTERN =
            Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9]+\\.[a-zA-Z]+$");

    @Column(name = "email", nullable = false)
    private String value;

    protected Email() {
    }

    public Email(String value) {
        this.value = Objects.requireNonNull(value, NULL_EXCEPTION_MESSAGE);
        validateContainsBlank(this.value);
        validatePattern(this.value);
        validateLength(this.value);
    }

    private void validateContainsBlank(String value) {
        if (value.contains(BLANK))
            throw new IllegalArgumentException(BLANK_EXCEPTION_MESSAGE);
    }

    private void validatePattern(String value) {
        if (!PATTERN.matcher(value).matches())
            throw new IllegalArgumentException(PATTERN_EXCEPTION_MESSAGE);
    }

    private void validateLength(String value) {
        String[] splitEmail = value.split("@");
        if (splitEmail[LOCAL_INDEX].length() > LOCAL_MAX_LENGTH)
            throw new IllegalArgumentException(LOCAL_LENGTH_EXCEPTION_MESSAGE);
        if (splitEmail[DOMAIN_INDEX].length() > DOMAIN_MAX_LENGTH)
            throw new IllegalArgumentException(DOMAIN_LENGTH_EXCEPTION_MESSAGE);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
