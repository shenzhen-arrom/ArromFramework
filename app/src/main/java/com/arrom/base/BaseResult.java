package com.arrom.base;

import java.io.Serializable;

/**
 * @author Arrom
 * @Project ArromFramework
 * @Package com.arrom.base
 * @Description: ${TODO}(返回数据的架构)
 * @Date 16/9/5 下午9:15
 * @Version V2.0
 */
public class BaseResult<T> implements Serializable {
    public int code;
    public String message;
    public T data;
}
