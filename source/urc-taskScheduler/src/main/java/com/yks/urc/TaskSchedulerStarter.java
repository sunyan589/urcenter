package com.yks.urc;

import com.yks.urc.constant.Constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//@SpringBootApplication
//@EnableScheduling
public class TaskSchedulerStarter {
	private static Logger LOG = LoggerFactory.getLogger(TaskSchedulerStarter.class);
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				new String[]{Constant.TASKSERVICE_STARTER_CONFIG});
		LOG.info("Urc Task Scheduler start...");
	}
}
