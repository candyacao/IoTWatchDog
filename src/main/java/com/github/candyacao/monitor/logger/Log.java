package com.github.candyacao.monitor.logger;

/**
 * @创建人 candyacao
 * @创建时间 2018/9/25
 * @描述
 */
public interface Log {
    void debug(String msg);
    void info(String msg);
    void warn(String msg);
    void error(String msg);
}
