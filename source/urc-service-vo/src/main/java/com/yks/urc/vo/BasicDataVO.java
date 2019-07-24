package com.yks.urc.vo;


import java.io.Serializable;

public class BasicDataVO implements Serializable {
    private static final long serialVersionUID = -6571197953018726347L;
    private SkuCategoryVO basicData;

    public SkuCategoryVO getBasicData() {
        return basicData;
    }

    public void setBasicData(SkuCategoryVO basicData) {
        this.basicData = basicData;
    }
}
