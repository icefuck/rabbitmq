package com.zc.rabbitmq.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zc.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhangchuang on 2018/10/24.
 * Description:
 */
public class Send {
	private static final String EXCHANGE_NAME = "test_exchange_topic";


	public static void main(String[] args) throws IOException, TimeoutException {

		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
		String msg = "hello topic";
		String routingKey = "goods.delete";
		channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
		System.out.println(msg);

		channel.close();
		connection.close();
	}
}
