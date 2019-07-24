package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;

public class OrgTreeAndUserVO implements Serializable {

    private static final long serialVersionUID = 3747794350516702622L;
    public Integer isUser;
    public String key;
    public String title;
    public String parentDingOrgId;
    public List<OrgTreeAndUserVO> children;
}
