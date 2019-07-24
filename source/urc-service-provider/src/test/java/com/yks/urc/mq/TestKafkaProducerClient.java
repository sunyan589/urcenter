package com.yks.urc.mq;

import com.yks.mq.client.MQProducerClient;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.ArrayList;
import java.util.List;

public class TestKafkaProducerClient
{
	public static void main(String[] args)
	{
		long start = System.currentTimeMillis();

		MQProducerClient mqProducerClient = new MQProducerClient();


		List<String> messages = new ArrayList<String>();

		messages.add("hello,this is  kafka");

		mqProducerClient.pubish("TEST_KAFKA", "我发了一个消息", new MQProducerClient.MessageCallBack()
		{
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception)
			{
				if(exception == null)
				{
					System.out.println("publish msg succeed!");
				}
				else
				{
					exception.printStackTrace();
				}
			}
		});

		long end = System.currentTimeMillis();

		System.out.println("Cost: [" + String.valueOf(end - start) + "]");
	}
}
