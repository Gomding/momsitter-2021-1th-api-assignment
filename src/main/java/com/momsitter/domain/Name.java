package com.momsitter.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public class Name {
    public static final String NULL_EXCEPTION_MESSAGE = "이름은 null일 수 없습니다.";
    public static final String BLANK_EXCEPTION_MESSAGE = "이름은 공백을 포함할 수 없습니다.";
    public static final String INVALID_NAME_CHARACTER_EXCEPTION = "이름에 특수문자는 포함할 수 업습니다.";

    private static final Pattern PATTERN = Pattern.compile("^(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).*$");
    private static final String BLANK = " ";

    private final String value;

    public Name(String value) {
        this.value = Objects.requireNonNull(value, NULL_EXCEPTION_MESSAGE);
        validateContainsBlank(this.value);
        validateNestedInvalidCharacter(this.value);
    }

    private void validateContainsBlank(String value) {
        if (value.contains(BLANK))
            throw new IllegalArgumentException(BLANK_EXCEPTION_MESSAGE);
    }

    private void validateNestedInvalidCharacter(String value) {
        if (PATTERN.matcher(value).matches())
            throw new IllegalArgumentException(INVALID_NAME_CHARACTER_EXCEPTION);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
