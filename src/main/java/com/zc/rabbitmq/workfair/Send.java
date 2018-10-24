package com.zc.rabbitmq.workfair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zc.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhangchuang on 2018/10/22.
 * Description:
 */
public class Send {
	private static final String QUEUE_NAME = "test_work_queue";
	public static void main(String []args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtils.getConnection();

		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME,false,false,false,null);
		channel.basicQos(1);
		for(int i=0;i<50;i++){
			String msg = "hello work queue"+i;
			System.out.println("[WQ ]send:"+msg);
			channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

			Thread.sleep(i*5);
		}
		channel.close();
		connection.close();
	}
}
