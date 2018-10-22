package com.zc.rabbitmq.work;

import com.rabbitmq.client.*;
import com.zc.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhangchuang on 2018/10/22.
 * Description:
 */
public class Receive1 {
	private static final String QUEUE_NAME = "test_work_queue";
	public static void  main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();

		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME,false,false,false,null);

		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msg = new String(body,"utf-8");
				System.out.println("receive1:"+msg);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {

				}
			}
		};
		channel.basicConsume(QUEUE_NAME,true,consumer);
	}
}
