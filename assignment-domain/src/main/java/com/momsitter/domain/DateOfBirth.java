package com.momsitter.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Embeddable
public class DateOfBirth {
    public static final String INVALID_DATE_TIME_EXCEPTION_MESSAGE = "유효하지 않은 월[1-12],일[1-31]입니다.";
    public static final String NULL_EXCEPTION_MESSAGE = "생년월일은 null일 수 없습니다.";
    public static final String AFTER_DATE_EXCEPTION_MESSAGE = "생년월일은 현재로부터 이전의 생년월일이 돼야합니다.";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Column(name = "date_of_birth", nullable = false)
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate value;

    protected DateOfBirth() {
    }

    public DateOfBirth(LocalDate value) {
        this.value = Objects.requireNonNull(value, NULL_EXCEPTION_MESSAGE);
        validateLocalDate(this.value);
    }

    private void validateLocalDate(LocalDate value) {
        if (value.isAfter(LocalDate.now()))
            throw new IllegalArgumentException(AFTER_DATE_EXCEPTION_MESSAGE);
    }

    public static DateOfBirth of(int year, int month, int dayOfMonth) {
        try {
            return new DateOfBirth(LocalDate.of(year, month, dayOfMonth));
        } catch (DateTimeException dte) {
            throw new IllegalArgumentException(INVALID_DATE_TIME_EXCEPTION_MESSAGE);
        }
    }

    /**
     * 문자열로 생년월일 객체를 생성하기 위한 정적 팩터리 메서드입니다.
     * 만약 2월에 28일까지 있는데 이를 초과하는 "19920231"을 인자로 받으면 예외가 발생하지 않고, "1992-02-29"와 같이 최대 날짜로 저장됩니다.
     *
     * @param value - yyyyMMdd 포맷의 문자열이어야 합니다. ex)"19920530"
     * @return 생년월일 객체를 반환합니다.
     */
    public static DateOfBirth of(String value) {
        Objects.requireNonNull(value, NULL_EXCEPTION_MESSAGE);
        try {
            return new DateOfBirth(LocalDate.parse(value, DATE_TIME_FORMATTER));
        } catch (DateTimeException dte) {
            throw new IllegalArgumentException(INVALID_DATE_TIME_EXCEPTION_MESSAGE);
        }
    }

    public LocalDate getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateOfBirth that = (DateOfBirth) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
