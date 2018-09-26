package com.github.candyacao.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 处理日志
 * @author candyacao
 * @created 2018年9月25日 下午2:54:27
 */
public class Log {
	private static Logger logger;
	static {
		logger = Logger.getRootLogger();
		// 手动指定配置文件加载路径
		PropertyConfigurator.configure("src/log4j.properties");
	}
	/**
	 * 设置debug级别
	 * @param msg
	 */
	public void debug(String msg) {
		logger.debug(msg);
	}
	/**
	 * 设置info级别
	 * @param msg
	 */
	public void info(String msg) {
		logger.info(msg);
	}
	/**
	 * 设置warn级别
	 * @param msg
	 */
	public void warn(String msg) {
		logger.warn(msg);
	}
	/**
	 * 设置error级别
	 * @param msg
	 */
	public void error(String msg) {
		logger.error(msg);
	}
}
