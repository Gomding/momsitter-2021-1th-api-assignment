package com.momsitter.ui.dto.account.parent;

import com.momsitter.domain.Child;
import com.momsitter.domain.ParentInfo;

import java.util.List;
import java.util.stream.Collectors;

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
        ParentInfo parentInfo = new ParentInfo(careRequestInfo);
        for (ChildRequest child : children) {
            child.toEntity().addParentInfo(parentInfo);
        }
        return parentInfo;
    }

    public List<ChildRequest> getChildren() {
        return children;
    }

    public String getCareRequestInfo() {
        return careRequestInfo;
    }
}
