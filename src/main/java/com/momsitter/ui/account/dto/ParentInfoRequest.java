package com.momsitter.ui.account.dto;

import com.momsitter.domain.ParentInfo;

import java.util.List;

public class ParentInfoRequest {
    private List<ChildRequest> children;
    private String careRequestInfo;

    protected ParentInfoRequest() {
    }

    public ParentInfoRequest(List<ChildRequest> children, String careRequestInfo) {
        this.children = children;
        this.careRequestInfo = careRequestInfo;
    }

    public ParentInfo toEntity() {
        return new ParentInfo(
                careRequestInfo
        );
    }

    public List<ChildRequest> getChildren() {
        return children;
    }

    public String getCareRequestInfo() {
        return careRequestInfo;
    }
}
