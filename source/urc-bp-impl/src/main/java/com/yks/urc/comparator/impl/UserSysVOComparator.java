package com.yks.urc.comparator.impl;

import com.yks.urc.fw.StringUtility;
import com.yks.urc.vo.UserSysVO;

import java.util.Comparator;

public class UserSysVOComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        if (o1 == null || o2 == null) return 0;
        UserSysVO u1 = (UserSysVO) o1;
        UserSysVO u2 = (UserSysVO) o2;
        return StringUtility.addEmptyString(u1.sysKey).compareToIgnoreCase(u2.sysKey);
    }
}
