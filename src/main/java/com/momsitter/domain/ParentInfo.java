package com.momsitter.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class ParentInfo {
    public static final String CARE_REQUEST_INFO_NULL_EXCEPTION_MESSAGE = "돌봄 신청 정보는 null이 될 수 없습니다.";
    public static final String CHILDREN_NULL_EXCEPTION_MESSAGE = "아이 정보는 null이 될 수 없습니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "parentInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> children = new ArrayList<>();

    @Lob
    private String careRequestInfo;

    protected ParentInfo() {
    }

    public ParentInfo(String careRequestInfo) {
        this(null, new ArrayList<>(), careRequestInfo);
    }

    public ParentInfo(List<Child> children, String careRequestInfo) {
        this(null, children, careRequestInfo);
    }

    public ParentInfo(Long id, List<Child> children, String careRequestInfo) {
        this.id = id;
        this.children = Objects.requireNonNull(children, CHILDREN_NULL_EXCEPTION_MESSAGE);
        this.careRequestInfo = Objects.requireNonNull(careRequestInfo, CARE_REQUEST_INFO_NULL_EXCEPTION_MESSAGE);
    }

    public void addChild(Child child) {
        this.children.add(child);
        child.addParentInfo(this);
    }

    public void updateInfo(List<Child> children, String careRequestInfo) {
        this.children = children;
        this.careRequestInfo = careRequestInfo;
    }

    public Long getId() {
        return id;
    }

    public List<Child> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public String getCareRequestInfo() {
        return careRequestInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentInfo that = (ParentInfo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
