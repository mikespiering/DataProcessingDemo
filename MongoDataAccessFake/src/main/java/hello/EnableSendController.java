package hello;


import java.io.File;
import java.util.Scanner;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;


@Controller
@RequestMapping("/startReading")
public class EnableSendController {

	@Autowired
	LocationRepository mr;
	@Autowired
	RabbitTemplate rabbitTemplate;

	
	@RequestMapping(method=RequestMethod.GET)
    public @ResponseBody String startReceiving()  {
		
		final String queueName = "SampleQueueName";
		int cnt=0;
		for(Location p: this.mr.findAll())
		    {
			cnt++;
		    }
		System.out.println("Found "+cnt+" records");
		int dataCnt=0;
		if(cnt<999999)
		    {
			System.out.println("Attemping to read resources");
			ClassLoader classLoader = getClass().getClassLoader();
			File f= new File(classLoader.getResource("datalist.txt").getFile());
			
			try (Scanner scanner = new Scanner(f)) {
				
				while (scanner.hasNextLine()) {
				    String line = scanner.nextLine();
				    if(dataCnt>1000000)
					continue;
				    //10000066022250111051,000,0,4383 DOUBLETREE LANE,City,CA,90210
				    String splitLine[]=line.split(",");
				    Location loc = new Location(splitLine[0],splitLine[3],splitLine[4],splitLine[5],splitLine[6],splitLine[2],splitLine[1]);
				    System.out.println(dataCnt);
				    // mr.save(loc);
				    rabbitTemplate.convertAndSend(queueName, loc.getMongoId()+","+loc.getStreet()+","+loc.getCity()+","+loc.getState()+","+loc.getZip()+","+loc.getUpdateFlag()+","+loc.getStatusCode());
				    dataCnt++;
				    
					
				}
				scanner.close();
			    } catch (Exception e) {
			    e.printStackTrace();
			}
		    }
		
		
		return "Started Writing";
    }
}
