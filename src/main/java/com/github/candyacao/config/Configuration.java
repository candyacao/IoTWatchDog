package com.github.candyacao.config;
/**
 * 1. 初始化其他模块
 * 2. 获得其他模块的对象
 * @author candyacao
 * @created 2018年9月26日 下午3:04:29
 */

import com.github.candyacao.client.Client;
import com.github.candyacao.client.Gather;
import com.github.candyacao.logger.Log;
import com.github.candyacao.server.DBStore;
import com.github.candyacao.server.Server;

public interface Configuration {
	Client getClient();
	Server getServer();
	Gather getGather();
	DBStore getDBStore();
	Log getLog();
}
