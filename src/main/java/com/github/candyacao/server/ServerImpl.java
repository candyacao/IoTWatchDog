package com.github.candyacao.server;

import com.github.candyacao.bean.Environment;
import com.github.candyacao.config.ModuleInit;
import com.github.candyacao.logger.Log;
import com.github.candyacao.logger.LogImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;


public class ServerImpl implements Server, ModuleInit {
	private int port;
	private static Log log = new LogImpl();

	@Override
	public void init(Properties properties) {
		port = Integer.parseInt(properties.getProperty("port"));
	}

	@Override
	public Collection<Environment> receiver() {
		ServerSocket ss = null;
		InputStream is = null;
		ObjectInputStream ois = null;
		Collection<Environment> collection = null;
		try {
			log.info("开始创建服务器端");
			ss = new ServerSocket(port);
			// 监听客户端是否连接
			log.info("服务器开始等待客户端连接。。。。");
			Socket socket = ss.accept();
			log.info("客户端连接成功。。。。");
			// 服务器端接收每个客户端的数据
			is = socket.getInputStream();
			ois = new ObjectInputStream(is);
			log.info("服务器端开始接收数据");
			Object object = ois.readObject();
			log.info("服务器端接收数据完成");
			if (object instanceof Collection) {
				collection = (Collection<Environment>) object;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return collection;
	}

}
