package com.mikespiering;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.apache.http.*;


public class MessageReceiver {

	@Autowired
	RabbitTemplate rabbitTemplate;


    //10000000894039111012,18023 PRIVET DRIVE,City,NV,89128,0,000
	public void receiverMessage(String message)
	{
	    System.out.println("RECEIVED MESSAGE: "+message);
	    
	    //HttpClient httpClient = HttpClients.createDefault();

	    	    RestTemplate restTemplate = new RestTemplate(); 
	    // restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient)); 
		    String[] splitString=message.split(",");
		    Location p = new Location(splitString[0],splitString[1],splitString[2],splitString[3],splitString[4],splitString[5],splitString[6]);
		     // public Location(String id, String street,String city, String state, String zip, String updateFlag, String statusCode)
		     //p.setLastName("bbbbb"+message);
		   
		    restTemplate.postForObject("http://sqlrestaccess.apps.edite.kim/location",p,Location.class);
		   
		    //            System.out.println(message.getId());


	}
}
