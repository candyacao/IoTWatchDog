package com.github.candyacao.logger;

import java.util.Properties;

import org.apache.log4j.Logger;
import com.github.candyacao.config.ModuleInit;
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
