package com.github.candyacao.monitor.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Handler {
    void handler(InputStream ins, OutputStream outs) throws IOException, ClassNotFoundException;
}
