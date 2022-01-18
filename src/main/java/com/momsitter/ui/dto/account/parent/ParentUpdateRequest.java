package com.momsitter.ui.dto.account.parent;

import java.util.List;

public class ParentUpdateRequest {
    private List<ChildUpdateRequest> children;
    private String careRequestInfo;

    protected ParentUpdateRequest() {
    }

    public ParentUpdateRequest(List<ChildUpdateRequest> children, String careRequestInfo) {
        this.children = children;
        this.careRequestInfo = careRequestInfo;
    }

    public List<ChildUpdateRequest> getChildren() {
        return children;
    }

    public String getCareRequestInfo() {
        return careRequestInfo;
    }
}
