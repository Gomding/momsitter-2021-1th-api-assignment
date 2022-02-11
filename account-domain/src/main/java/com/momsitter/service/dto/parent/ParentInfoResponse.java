package com.momsitter.service.dto.parent;

import com.momsitter.domain.ParentInfo;

import java.util.List;
import java.util.stream.Collectors;

public class ParentInfoResponse {
    private Long id;
    private List<ChildResponse> children;
    private String careRequestInfo;

    protected ParentInfoResponse() {
    }

    public ParentInfoResponse(Long id, List<ChildResponse> children, String careRequestInfo) {
        this.id = id;
        this.children = children;
        this.careRequestInfo = careRequestInfo;
    }

    public static ParentInfoResponse of(ParentInfo parentInfo) {
        return new ParentInfoResponse(
                parentInfo.getId(),
                parentInfo.getChildren().stream()
                        .map(ChildResponse::of)
                        .collect(Collectors.toList()),
                parentInfo.getCareRequestInfo()
        );
    }

    public Long getId() {
        return id;
    }

    public List<ChildResponse> getChildren() {
        return children;
    }

    public String getCareRequestInfo() {
        return careRequestInfo;
    }
}
