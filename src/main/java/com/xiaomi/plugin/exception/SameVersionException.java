package com.xiaomi.plugin.exception;

/**
 * 同种名称软件 版本号唯一
 * Created by lijie on 2015-08-11.
 */
public class SameVersionException extends Exception{

    public SameVersionException() {
        super();
    }

    public SameVersionException(String message) {
        super(message);
    }
}
