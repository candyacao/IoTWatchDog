package com.github.candyacao.server;

import com.github.candyacao.bean.Environment;

import java.util.Collection;

/**
 * @创建人 candyacao
 * @创建时间 2018/9/25
 * @描述
 */
public interface Server {
    Collection<Environment> receiver();
}
