package com.momsitter.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ParentInfo {
    public static final String CARE_REQUEST_INFO_NULL_EXCEPTION_MESSAGE = "돌봄 신청 정보는 null이 될 수 없습니다.";
    public static final String CHILDREN_NULL_EXCEPTION_MESSAGE = "아이 정보는 null이 될 수 없습니다.";

    private Long id;
    private List<Child> children = new ArrayList<>();
    private String careRequestInfo;

    protected ParentInfo() {
    }

    public ParentInfo(List<Child> children, String careRequestInfo) {
        this(null, children, careRequestInfo);
    }

    public ParentInfo(Long id, List<Child> children, String careRequestInfo) {
        this.id = id;
        this.children = Objects.requireNonNull(children, CHILDREN_NULL_EXCEPTION_MESSAGE);
        this.careRequestInfo = Objects.requireNonNull(careRequestInfo, CARE_REQUEST_INFO_NULL_EXCEPTION_MESSAGE);
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
