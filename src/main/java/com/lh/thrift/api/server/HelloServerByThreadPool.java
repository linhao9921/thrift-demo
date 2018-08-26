package com.lh.thrift.api.server;

import com.lh.thrift.Contants;
import com.lh.thrift.HelloService;
import com.lh.thrift.impl.HelloServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

/**
 * thrift服务端
 * Created by Linhao on 2018/8/26.
 */
public class HelloServerByThreadPool {

    private void startService(){
        try {
            System.out.println("Start to thrift server...");

            TProcessor tProtocol = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());

            //开启服务监听
            TServerSocket tServerSocket = new TServerSocket(Contants.SERVER_PORT);


            TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(tServerSocket);
            tArgs.processor(tProtocol);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());

            //【采用线程池服务方式，采用标准阻塞式IO】
            TServer tServer = new TThreadPoolServer(tArgs);
            tServer.serve();

            System.out.println("Started thrift server on port:" + Contants.SERVER_PORT);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HelloServerByThreadPool().startService();
    }
}
