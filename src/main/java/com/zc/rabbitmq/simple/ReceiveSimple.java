package com.zc.rabbitmq.simple;

import com.rabbitmq.client.*;
import com.zc.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2018/10/21.
 */
public class ReceiveSimple {
    public static final String QUEUE_NAME = "test_simple_quene";

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws IOException,
            TimeoutException, ShutdownSignalException,
            ConsumerCancelledException, InterruptedException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("receive:" + msg);
            }
        };
        //监听频道
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);

    }

    public void old() throws IOException,
            TimeoutException, ShutdownSignalException,
            ConsumerCancelledException, InterruptedException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();
        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msgString = new String(delivery.getBody());
            System.out.println("[recv] msg:" + msgString);
        }
    }
}
