package com.zc.rabbitmq.tx;

import com.rabbitmq.client.*;
import com.zc.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhangchuang on 2018/10/29.
 * Description:
 */
public class TxRecev {
	private static final String QUEUE_NAME = "test_queue_tx";
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME,false,false,false,null);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				System.out.println("receive "+new String(body,"utf-8"));
			}
		};
		channel.basicConsume(QUEUE_NAME,true,consumer);
	}
}
