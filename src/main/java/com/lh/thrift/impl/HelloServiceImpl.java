package com.lh.thrift.impl;

import com.lh.thrift.HelloService;
import org.apache.thrift.TException;

/**
 * 测试
 * Created by Linhao on 2018/8/26.
 */
public class HelloServiceImpl implements HelloService.Iface {
    @Override
    public String helloString(String para) throws TException {
        return "hi，I'am thrift service." + para;
    }
}
