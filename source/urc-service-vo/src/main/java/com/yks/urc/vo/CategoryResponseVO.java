package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;

public class CategoryResponseVO implements Serializable {
    private static final long serialVersionUID = 3003166758646712288L;
    private List<CategoryResponseVO> children;
    private String cateId;
    private String cateNameCn;

    public List<CategoryResponseVO> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryResponseVO> children) {
        this.children = children;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getCateNameCn() {
        return cateNameCn;
    }

    public void setCateNameCn(String cateNameCn) {
        this.cateNameCn = cateNameCn;
    }
}
