package com.github.candyacao.client;


import com.github.candyacao.bean.Environment;

import java.util.Collection;

public interface Client {
    void send(Collection<Environment> collection);
}
