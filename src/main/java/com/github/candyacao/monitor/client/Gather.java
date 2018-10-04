package com.github.candyacao.monitor.client;


import com.github.candyacao.monitor.bean.Environment;

import java.util.Collection;

/**
 * @创建人 candyacao
 * @创建时间 2018/9/25
 * @描述
 */
public interface Gather {
    Collection<Environment> gather();
}
