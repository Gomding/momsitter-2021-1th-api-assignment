package com.momsitter.domain;

import java.util.Objects;

public class Child {

    private Long id;
    private DateOfBirth dateOfBirth;
    private Gender gender;

    protected Child() {
    }

    public Child(DateOfBirth dateOfBirth, Gender gender) {
        this(null, dateOfBirth, gender);
    }

    public Child(Long id, DateOfBirth dateOfBirth, Gender gender) {
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Child child = (Child) o;
        return Objects.equals(id, child.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
