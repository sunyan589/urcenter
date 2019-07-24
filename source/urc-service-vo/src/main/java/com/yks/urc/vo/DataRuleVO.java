/**
 * 〈一句话功能简述〉<br>
 * 〈数据权限总VO〉
 *
 * @author 31076
 * @create 2018/6/5
 * @since 1.0.0
 */
package com.yks.urc.vo;

import java.io.Serializable;
import java.util.List;

public class DataRuleVO implements Serializable {
    private static final long serialVersionUID = 7325128956766823671L;
    /**
     * 用户名
     */
    public String userName;
    /**
     * 一个系统一个VO
     */
    public List<DataRuleSysVO> lstDataRuleSys;

    public String t;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<DataRuleSysVO> getLstDataRuleSys() {
        return lstDataRuleSys;
    }

    public void setLstDataRuleSys(List<DataRuleSysVO> lstDataRuleSys) {
        this.lstDataRuleSys = lstDataRuleSys;
    }
}
