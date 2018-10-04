package com.github.candyacao.monitor.config;
/**
 * 1. 初始化其他模块
 * 2. 获得其他模块的对象
 * @author candyacao
 * @created 2018年9月26日 下午3:04:29
 */

import com.github.candyacao.monitor.client.Client;
import com.github.candyacao.monitor.client.Gather;
import com.github.candyacao.monitor.logger.Log;
import com.github.candyacao.monitor.server.DBStore;
import com.github.candyacao.monitor.server.Server;

public interface Configuration {
	Client getClient();
	Server getServer();
	Gather getGather();
	DBStore getDBStore();
	Log getLog();
}
