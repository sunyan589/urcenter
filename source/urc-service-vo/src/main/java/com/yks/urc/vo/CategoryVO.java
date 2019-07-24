package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;

public class CategoryVO  implements Serializable{
    private static final long serialVersionUID = -8549160579033725751L;
    private List<CategoryVO> subCategorys;
    private String cateId;
    private String cateNameCn;

    public List<CategoryVO> getSubCategorys() {
        return subCategorys;
    }

    public void setSubCategorys(List<CategoryVO> subCategorys) {
        this.subCategorys = subCategorys;
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
