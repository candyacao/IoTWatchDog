package com.github.candyacao.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Collection;

import com.github.candyacao.bean.Environment;

public class ServerThread implements Runnable {
	private Socket socket;
	/** 客户端名称 */
	private String clientName;
	
	public ServerThread(Socket socket,String clientName) {
		this.socket = socket;
		this.clientName = clientName;
	}
	@Override
	public void run() {
		ObjectInputStream ois = null;
		Object object = null;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			object = ois.readObject();
			if (object instanceof Collection) {
				Collection<Environment> collection = (Collection) object;
				System.out.println(clientName + "接收的数据大小为："+collection.size());
				//入库操作
				System.out.println("开始入库");
				new DBStore().saveToDB(collection);
				System.out.println("入库完成");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
