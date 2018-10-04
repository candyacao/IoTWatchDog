package com.github.candyacao.monitor.client;


import com.github.candyacao.monitor.bean.Environment;

import java.util.Collection;

public interface Client {
    void send(Collection<Environment> collection);
}
