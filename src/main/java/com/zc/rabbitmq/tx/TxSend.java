package com.zc.rabbitmq.tx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zc.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhangchuang on 2018/10/29.
 * Description:
 */
public class TxSend {
	private static final String QUEUE_NAME = "test_queue_tx";
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME,false,false,false,null);
		String msg = "send tx";
		try {
			channel.txSelect();
			int x = 1/0;
			channel.basicPublish("",QUEUE_NAME, null, msg.getBytes());
			channel.txCommit();
		}catch (Exception e){
			channel.txRollback();
			System.out.println("回滚了");
		}finally {
			channel.close();
			connection.close();
		}
	}
}
