package com.github.candyacao.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.github.candyacao.bean.Environment;
import com.github.candyacao.gather.ReadData;
/**
 * 客户端，将数据发送到服务器端
 * @author candyacao
 * @created 2018年9月21日 下午3:27:39
 */
public class Client{
	final static Logger log = Logger.getLogger(Client.class);
	public static void main(String[] args) {
		Socket socket = null;
		ObjectOutputStream oos = null;
		try {
			log.info("客户端开始连接服务器。。。。");
//			System.out.println("客户端开始连接服务器。。。。");
			socket = new Socket("localhost", 10088);
			// 通过socket获取输出流，并将其包装成对象流
			oos = new ObjectOutputStream(socket.getOutputStream());
			// 获取集合对象
			Collection<Environment> collection = new ReadData().gather();
			oos.writeObject(collection);
			log.info("数据发送完成");
//			System.out.println("数据发送完成");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			log.warn(e.getStackTrace());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.warn(e.getStackTrace());
		}finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.warn(e.getStackTrace());
			}
		}
	}
}
