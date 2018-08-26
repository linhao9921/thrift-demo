package com.lh.thrift.api.client;

import com.lh.thrift.Contants;
import com.lh.thrift.HelloService;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * thrift客户端
 * Created by Linhao on 2018/8/26.
 */
public class HelloClientByNonblockingAsync {

    /**
     * 开启客户端的(异步)调用
     */
    private void startClient(){
        TNonblockingTransport tTransport = null;

        try {
            TAsyncClientManager manager = new TAsyncClientManager();

            tTransport = new TNonblockingSocket(Contants.SERVER_IP, Contants.SERVER_PORT, Contants.TIMEOUT);

            //协议采用服务端一样的协议
            TProtocolFactory tProtocolFactory = new TCompactProtocol.Factory();
            HelloService.AsyncClient client = new HelloService.AsyncClient
                    .Factory(manager, tProtocolFactory).getAsyncClient(tTransport);
            System.out.println("Client async start.....");

            //开启thrift调用
            CountDownLatch latch = new CountDownLatch(1);
            MyAsyncMethodCallback callback = new MyAsyncMethodCallback(latch);
            System.out.println("call method helloString start ...");
            client.helloString("hello world!", callback);
            System.out.println("call method helloString end ...");

            boolean wait = latch.await(30, TimeUnit.SECONDS);
            System.out.println("latch.await = " + wait);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(tTransport != null){
                tTransport.close();
            }
        }
        System.out.println("Client async end.");
    }

    private class MyAsyncMethodCallback implements AsyncMethodCallback<String> {
        private final CountDownLatch latch;

        public MyAsyncMethodCallback(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onComplete(String response) {
            System.out.println("onComplete");
            try {
                // Thread.sleep(1000L * 1);
                System.out.println("Async Call result =:" + response);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }

        @Override
        public void onError(Exception exception) {
            System.out.println("onError :" + exception.getMessage());
            latch.countDown();
        }
    }

    public static void main(String[] args) {
        new HelloClientByNonblockingAsync().startClient();
    }
}
