package com.github.candyacao.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器端  用来接收客户端发送来的数据并完成入库操作
 * @author candyacao
 * @created 2018年9月21日 下午3:45:14
 */
public class Server {
	public static void main(String[] args) {
		// 创建Server服务器端的连接,并指定监听端口号
		ServerSocket server = null;
		int num = 0;	
		try {
			server = new ServerSocket(10088);
			System.out.println("服务端即将连接，等待客户端");
			while(true) {
				System.out.println("开始等待连接。。。");
				// 建立accept（）连接
				Socket socket = server.accept();
				System.out.println("有客户端连接进来了");
				new Thread(new ServerThread(socket, "客户端"+num++)).start();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}
}
