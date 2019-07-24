package com.yks.urc.mapper;

import com.yks.urc.entity.UserLoginLogDO;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserLoginLogMapper {

	Integer insertUserLoginLog(UserLoginLogDO userLoginLogPO);

	UserLoginLogDO selectUserLoginLog();

	int insertLogs(UserLoginLogDO logDO);
}
