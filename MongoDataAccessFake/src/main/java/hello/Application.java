
package hello;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.client.RestTemplate;
import java.util.Scanner;
import java.io.File;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
@Component
class MongoCommandLineRunner implements CommandLineRunner {
    @Override
	public void run (String... arg0) throws Exception {
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
	
	
	System.out.println("Sending records to rabbitqueue");
	/*
	
	
	for(Location p: this.mr.findAll())
            {
				
		rabbitTemplate.convertAndSend(queueName, p.getMongoId()+","+p.getStreet()+","+p.getCity()+","+p.getState()+","+p.getZip()+","+p.getUpdateFlag()+","+p.getStatusCode());
	    	System.out.println("Sending "+p.getMongoId());
	    }
	*/
	
    }

@Autowired
    LocationRepository mr;
@Autowired
    RabbitTemplate rabbitTemplate;

}


