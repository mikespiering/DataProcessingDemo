package hello;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import com.vaadin.ui.Button.ClickEvent;
import java.net.URL;
import java.net.*;
import java.util.concurrent.Future;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import java.io.OutputStreamWriter;
public class RestUtility {


@Async
    public static Future<String> callRestGet(String urlString)
    {
	String outputString="";
	try {

	    URL url = new URL(urlString);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Accept", "application/json");
	    
	    if (conn.getResponseCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : "
					   + conn.getResponseCode());
	    }
	    
	    BufferedReader br = new BufferedReader(new InputStreamReader(
								 (conn.getInputStream())));
	    
	    String output;
	    System.out.println("Output from Server .... \n");
	    while ((output = br.readLine()) != null) {
		System.out.println(output);
		outputString+=output+" ";
	    }
	    
	    conn.disconnect();

	} catch (MalformedURLException e) {
	    
	    e.printStackTrace();
	    
	} catch (IOException e) {

	    e.printStackTrace();
	    
	}
	return new AsyncResult<String>(outputString);
	//	return outputString;
    }
    @Async
    public static Future<String> callRestPost(String urlString,String payload)
    {
	String outputString="";
	try {

	    URL url = new URL(urlString);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/json");
	    conn.setDoOutput(true);
	    OutputStreamWriter osw=new OutputStreamWriter(conn.getOutputStream());
	    osw.write(payload);
	    osw.flush();
	    osw.close();
	    
	    if (conn.getResponseCode() != 201 && conn.getResponseCode() !=200) {
		throw new RuntimeException("Failed : HTTP error code : "
					   + conn.getResponseCode());
	    }
	    
	    
	
	    
	    conn.disconnect();

	} catch (MalformedURLException e) {
	    
	    e.printStackTrace();
	    
	} catch (IOException e) {

	    e.printStackTrace();
	    
	}
	return new AsyncResult<String>(outputString);
	//	return outputString;
    }
}
