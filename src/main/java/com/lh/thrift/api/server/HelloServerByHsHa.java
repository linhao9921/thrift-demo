package com.lh.thrift.api.server;

import com.lh.thrift.Contants;
import com.lh.thrift.HelloService;
import com.lh.thrift.impl.HelloServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 * thrift服务端
 * Created by Linhao on 2018/8/26.
 */
public class HelloServerByHsHa {

    private void startService(){
        try {
            System.out.println("Start to thrift server...");

            TProcessor tProtocol = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());

            //开启服务监听(NIO)
            TNonblockingServerSocket tServerSocket = new TNonblockingServerSocket(Contants.SERVER_PORT);

            THsHaServer.Args tArgs = new THsHaServer.Args(tServerSocket);
            tArgs.processor(tProtocol);
            tArgs.transportFactory(new TFramedTransport.Factory());
            tArgs.protocolFactory(new TBinaryProtocol.Factory());

            //【半同步半异步的服务端模型，服务端和客户端需要指定TFramedTransport数据传输的方式】
            TServer tServer = new THsHaServer(tArgs);
            tServer.serve();

            System.out.println("Started thrift server on port:" + Contants.SERVER_PORT);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HelloServerByHsHa().startService();
    }
}
