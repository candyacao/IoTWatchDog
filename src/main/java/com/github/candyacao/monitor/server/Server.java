package com.github.candyacao.monitor.server;

import com.github.candyacao.monitor.bean.Environment;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;

/**
 * @创建人 candyacao
 * @创建时间 2018/9/25
 * @描述
 */
public interface Server {

    void listenAndServe() ;
    void serve(ServerSocket socket) throws IOException, ClassNotFoundException;

}
