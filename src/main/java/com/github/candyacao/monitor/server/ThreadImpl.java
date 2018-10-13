package com.github.candyacao.monitor.server;

import com.github.candyacao.monitor.logger.Log;
import com.github.candyacao.monitor.logger.LogImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @创建人 candyacao
 * @创建时间 2018/10/13
 * @描述   多线程来实现处理客户端的连接
 */
public class ThreadImpl implements Runnable{

    private Socket s;
    private Handler DefaultHandler;
    private static Log log = new LogImpl();

    public ThreadImpl(Socket s, Handler defaultHandler) {
        this.s = s;
        DefaultHandler = defaultHandler;
    }

    @Override
    public void run() {
        InputStream ins = null;
        OutputStream outs = null;
        try {
            // 服务器端接收每个客户端的数据
            ins = s.getInputStream();
            outs= s.getOutputStream();
                DefaultHandler.handler(ins, outs);
            s.close();
            log.info("client leaved.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
