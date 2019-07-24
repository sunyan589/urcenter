package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SkuCategoryVO  implements Serializable{

    private static final long serialVersionUID = -7413428975523456565L;


    private List<CategoryResponseVO> firstCategory;
    private  List<DataColumnVO>   dataColumn;

    public List<DataColumnVO> getDataColumn() {
        return dataColumn;
    }

    public void setDataColumn(List<DataColumnVO> dataColumn) {
        this.dataColumn = dataColumn;
    }

    public List<CategoryResponseVO> getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(List<CategoryResponseVO> firstCategory) {
        this.firstCategory = firstCategory;
    }
}
