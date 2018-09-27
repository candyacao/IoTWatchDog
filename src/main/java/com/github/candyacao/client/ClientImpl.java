package com.github.candyacao.client;

import com.github.candyacao.bean.Environment;
import com.github.candyacao.config.ModuleInit;
import com.github.candyacao.logger.Log;
import com.github.candyacao.logger.LogImpl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Properties;

/**
 * @创建人 candyacao
 * @创建时间 2018/9/26
 * @描述
 */
public class ClientImpl implements Client, ModuleInit {
    private int port;
    private String ip;
    private static final Log log = new LogImpl();
    public ClientImpl() {}
    /**
     * 发送集合到服务器端
     */
    @Override
    public void send(Collection<Environment> collection) {
        Socket socket = null;
        try {
            log.info("客户端正在与服务器进行连接");
            socket = new Socket(ip, port);
            log.info("与服务器成功连接");
            //发送数据
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            log.info("客户端开始发送数据");
            oos.writeObject(collection);
            log.info("客户端发送数据完成");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 加载配置文件
     * @param properties 配置文件
     */
    @Override
    public void init(Properties properties) {
        ip = properties.getProperty("ip");
        port = Integer.parseInt(properties.getProperty("port"));
    }
}
