package com.zc.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zc.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2018/10/21.
 */
public class SendSimple {
    public static final String QUEUE_NAME = "test_simple_quene";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "hello simple quene";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println("****sendMsg:" + msg);
        channel.close();
        connection.close();

    }
}
