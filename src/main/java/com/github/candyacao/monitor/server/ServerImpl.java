package com.github.candyacao.monitor.server;

import com.github.candyacao.monitor.bean.Environment;
import com.github.candyacao.monitor.config.ConfigurationImpl;
import com.github.candyacao.monitor.config.ModuleInit;
import com.github.candyacao.monitor.logger.Log;
import com.github.candyacao.monitor.logger.LogImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;


public class ServerImpl implements Server, ModuleInit {
	private int port;
	private static Log log = new LogImpl();

	private static Handler DefaultHandler;

	public void setHandler(Handler h){
		DefaultHandler = h;
	}

	@Override
	public void init(Properties properties) {
		port = Integer.parseInt(properties.getProperty("port"));
	}

	@Override
	public void serve(ServerSocket socket) throws IOException, ClassNotFoundException {
		InputStream ins = null;
		OutputStream outs = null;
		Collection<Environment> collection = null;
		Socket s = socket.accept();
		log.info("客户端连接成功。。。。");
		// 服务器端接收每个客户端的数据
		ins = s.getInputStream();
		outs= s.getOutputStream();
		DefaultHandler.handler(ins, outs);
		s.close();
		log.info("client leaved.");
	}

	public void listenAndServe(){
		ServerSocket ss = null;
		log.info("开始创建服务器端 addr:"+port);
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		// 监听客户端是否连接
		log.info("服务器开始等待客户端连接。。。。");
		while(true) {
			try {
				serve(ss);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args){

		class H implements Handler{
			public void handler(InputStream ins, OutputStream outs) throws IOException, ClassNotFoundException{
				ObjectInputStream ois = null;
				ois = new ObjectInputStream(ins);
				log.info("服务器端开始接收数据");
				Object object = ois.readObject();
				log.info("服务器端接收数据完成");
				if (object instanceof Collection) {
					DBStore dbStore = new ConfigurationImpl().getDBStore();
					dbStore.saveToDB((Collection<Environment>) object);
				}
			}
		}

		Server server = new ConfigurationImpl().getServer();
		((ServerImpl) server).setHandler(new H());
		server.listenAndServe();
	}
}
