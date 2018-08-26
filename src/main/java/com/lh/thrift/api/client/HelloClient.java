package com.lh.thrift.api.client;

import com.lh.thrift.Contants;
import com.lh.thrift.HelloService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * thrift客户端
 * Created by Linhao on 2018/8/26.
 */
public class HelloClient {

    /**
     * 开启客户端的调用
     */
    private void startClient(){
        TTransport tTransport = null;

        try {
            tTransport = new TSocket(Contants.SERVER_IP, Contants.SERVER_PORT, Contants.TIMEOUT);

            //协议采用服务端一样的协议
            TProtocol protocol = new TBinaryProtocol(tTransport);
            // TProtocol protocol = new TCompactProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);

            HelloService.Client client = new HelloService.Client.Factory().getClient(protocol);
            tTransport.open();

            //开启thrift调用
            String result = client.helloString("hello world!");
            System.out.println("Thrift client result = " + result);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if(tTransport != null){
                tTransport.close();
            }
        }
    }

    public static void main(String[] args) {
        new HelloClient().startClient();
    }
}
