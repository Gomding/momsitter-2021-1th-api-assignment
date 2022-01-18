package com.momsitter.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private DateOfBirth dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ParentInfo parentInfo;

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

    public void addParentInfo(ParentInfo parentInfo) {
        if (Objects.nonNull(this.parentInfo))
            return;
        this.parentInfo = parentInfo;
        parentInfo.addChild(this);
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

    public ParentInfo getParentInfo() {
        return parentInfo;
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
