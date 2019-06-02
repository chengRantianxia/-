package com.briup.log.impl;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.briup.log.Log;

public class LogImpl implements Log{
	private static final Logger logger = Logger.getLogger(LogImpl.class);
//	private static final Logger logger1 = Logger.getRootLogger();//根logger对象
	
	@Override
	public void init(Properties properties) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(String message) {
		// TODO Auto-generated method stub
		//输出调试信息
		logger.debug(message);
	}

	@Override
	public void info(String message) {
		// TODO Auto-generated method stub
		//输出系统提示
		logger.info(message);
	}

	@Override
	public void warn(String message) {
		// TODO Auto-generated method stub
		//输出警告信息
		logger.warn(message);
	}

	@Override
	public void error(String message) {
		// TODO Auto-generated method stub
		//输出错误信息
		logger.error(message);
	}

	@Override
	public void fatal(String message) {
		// TODO Auto-generated method stub
		//输出系统错误
		logger.fatal(message);
	}

}
