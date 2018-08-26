package com.lh.thrift.api.server;

import com.lh.thrift.Contants;
import com.lh.thrift.HelloService;
import com.lh.thrift.impl.HelloServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 * thrift服务端
 * Created by Linhao on 2018/8/26.
 */
public class HelloServerByNonblocking {

    private void startService(){
        try {
            System.out.println("Start to thrift server...");

            TProcessor tProtocol = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());

            //开启服务监听(NIO)
            TNonblockingServerSocket tServerSocket = new TNonblockingServerSocket(Contants.SERVER_PORT);

            TNonblockingServer.Args tArgs = new TNonblockingServer.Args(tServerSocket);
            tArgs.processor(tProtocol);
            tArgs.transportFactory(new TFramedTransport.Factory());
            tArgs.protocolFactory(new TCompactProtocol.Factory());

            //【采用标准非阻塞式IO方式，服务端和客户端需要指定TFramedTransport数据传输的方式】
            TServer tServer = new TNonblockingServer(tArgs);
            tServer.serve();

            System.out.println("Started thrift server on port:" + Contants.SERVER_PORT);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HelloServerByNonblocking().startService();
    }
}
