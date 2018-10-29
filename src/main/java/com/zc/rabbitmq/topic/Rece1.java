package com.zc.rabbitmq.topic;

import com.rabbitmq.client.*;
import com.zc.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhangchuang on 2018/10/24.
 * Description:
 */
public class Rece1 {
	private static final String QUEUE_NAME = "test_topic_queue1";
	private static final String EXCHANGE_NAME = "test_exchange_topic";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		final Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "goods.add");
		channel.basicQos(1);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msg = new String(body, "utf-8");
				System.out.println("receive1:" + msg);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};

		Boolean ack = false;
		channel.basicConsume(QUEUE_NAME, ack, consumer);

	}

}
