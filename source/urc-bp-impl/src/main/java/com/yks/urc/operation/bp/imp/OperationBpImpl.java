package com.yks.urc.operation.bp.imp;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yks.urc.entity.OperationLog;
import com.yks.urc.fw.StringUtility;
import com.yks.urc.mapper.IOperationLogMapper;
import com.yks.urc.operation.bp.api.IOperationBp;
import com.yks.urc.vo.ResultVO;
import com.yks.urc.vo.helper.VoHelper;

@Component
public class OperationBpImpl implements IOperationBp {
	

    @Value("${maven.package.time}")
    private String mavenPackageTime;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);

	@Autowired
	IOperationLogMapper operationLogMapper;

	@Override
	public void addLog(String strLogger, String msg, Exception ex) {
		OperationLog log = new OperationLog();
		log.setLogger(strLogger);
		log.setLogLevel("INFO");
		log.setMsg(msg);
		if (ex != null)
			log.setExceptionDetail(ex.getMessage());
		log.setOperatorTime(new Date());

		fixedThreadPool.execute(new Runnable() {

			@Override
			public void run() {
				try {
					log.setCreateTime(new Date());
					log.setModifiedTime(log.getCreateTime());
					operationLogMapper.addLog(log);
				} catch (Exception ex) {
					logger.error(StringUtility.toJSONString_NoException(log), ex);
				}
			}
		});
	}

	
	
	public ResultVO getMavenPackageTime() {
		if(StringUtility.isNullOrEmpty(mavenPackageTime)){
			return VoHelper.getErrorResult();
		}
		return VoHelper.getSuccessResult(mavenPackageTime);
	}

}
