package com.github.candyacao;


import com.github.candyacao.bean.Environment;
import com.github.candyacao.client.Client;
import com.github.candyacao.client.ClientImpl;
import com.github.candyacao.client.Gather;
import com.github.candyacao.client.GatherImpl;
import com.github.candyacao.config.Configuration;
import com.github.candyacao.config.ConfigurationImpl;
import com.github.candyacao.server.DBStore;
import com.github.candyacao.server.DBStoreImp;
import com.github.candyacao.server.Server;
import com.github.candyacao.server.ServerImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class GatherTest {
	private Configuration configuration;
	@Before
	public void init() {
		configuration = new ConfigurationImpl();
	}
	@Test
	public void client() {

		Gather gather = configuration.getGather();
		Collection<Environment> collection = gather.gather();
		Client client = configuration.getClient();
		client.send(collection);
		
	}
	@Test
	public void server() {

		Server server = configuration.getServer();
		Collection<Environment> collection = server.receiver();
		DBStore dbStore = configuration.getDBStore();
		dbStore.saveToDB(collection);

	}
}
