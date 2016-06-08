package com.mikespiering;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import org.apache.http.*;


public class MessageReceiver {

	@Autowired
	RabbitTemplate rabbitTemplate;


	//10000000894039111012,18023 PRIVET DRIVE,City,NV,89128,0,000
	public void receiverMessage(String message)
	{
		System.out.println("RECEIVED MESSAGE: "+message);

		RestTemplate restTemplate = new RestTemplate(); 
		String[] splitString=message.split(",");
		Location p = new Location(splitString[0],splitString[1],splitString[2],splitString[3],splitString[4],splitString[5],splitString[6]);
		String locationUrl="";
		Map<String, String> env = System.getenv();
		if (env.containsKey("LOCATION_ENDPOINT")) {
			locationUrl=env.get("LOCATION_ENDPOINT");
			//System.out.println("LOCATION_ENDPOINT:"+loadurl);

		}
		else 
		{
			System.out.println("ERROR: Need to define 'LOCATION_ENDPOINT'");
			locationUrl="http://sqlrestaccess.apps.edite.kim/location";
		}
		restTemplate.postForObject(locationUrl,p,Location.class);

	}
}
