package com.github.candyacao.config;

import java.util.Properties;

/**
 * @创建人 candyacao
 * @创建时间 2018/9/25
 * @描述 初始化其余各个模块的基本参数
 */
public interface ModuleInit {
    void init(Properties properties);
}
