package com.github.candyacao.monitor.logger;

import java.util.Properties;

import com.github.candyacao.monitor.config.ModuleInit;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogImpl implements Log, ModuleInit {
	private Logger logger = Logger.getRootLogger();
	@Override
	public void init(Properties properties) {
		PropertyConfigurator.configure(properties.getProperty("log_prop"));
	}

	@Override
	public void debug(String msg) {
		logger.debug(msg);
	}

	@Override
	public void info(String msg) {
		logger.info(msg);
	}

	@Override
	public void warn(String msg) {
		logger.warn(msg);
	}

	@Override
	public void error(String msg) {
		logger.error(msg);
	}

}
