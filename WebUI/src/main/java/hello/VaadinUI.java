package hello;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Future;
import org.springframework.util.StringUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import com.vaadin.ui.Button.ClickEvent;
import java.net.URL;
import java.net.*;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import hello.RestUtility;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.json.*;

@EnableAsync
@SpringUI
@Theme("valo")
@Configuration
@ComponentScan("hello")
public class VaadinUI extends UI {

	//	private final Grid grid;

	private final Table dataTable;

	private final Label dataCnt;
	private final Label instanceCnt;
	private final Label ipInstance;

	private final Button addNewBtn;
	private final Button killBtn;
	private String puturl="";
	private String loadurl="";
	private int pageNum=0;
	public VaadinUI() {
		//	this.grid = new Grid();

		this.dataCnt = new Label("Total Processed");
		this.dataTable = new Table();
		this.addNewBtn = new Button("Next Page", FontAwesome.PLUS);
		this.killBtn = new Button("Kill");
		this.instanceCnt = new Label("Instance:");
		this.ipInstance = new Label("Instance IP");
	}

	public void clearData(Table dataTable)
	{
		dataTable.removeAllItems();
	}
	public void loadData(Table dataTable,String id,String street,String city, String zip, String flag, String status)
	{
		dataTable.addItem(new Object[]{id,street,city,zip,flag,status }, dataTable.size());
	}
	@Override
	protected void init(VaadinRequest request) {
		// build layout

		Map<String, String> env = System.getenv();
		
		String instance=env.get("CF_INSTANCE_INDEX");
		String ipAddr = env.get("CF_INSTANCE_ADDR");

		/*if (env.containsKey("REST_PUT_ENDPOINT")) {
			puturl=env.get("REST_PUT_ENDPOINT");
			System.out.println("REST_PUT_ENDPOINT:"+puturl);

		}
		else 
			System.out.println("ERROR: Need to define 'REST_PUT_ENDPOINT'");

		 */
		if (env.containsKey("LOAD_ENDPOINT")) {
			loadurl=env.get("LOAD_ENDPOINT");
			System.out.println("LOAD_ENDPOINT:"+loadurl);

		}
		else 
			System.out.println("ERROR: Need to define 'LOAD_ENDPOINT'");

		dataTable.addContainerProperty("ID", String.class, null);
		dataTable.addContainerProperty("Street", String.class, null);
		dataTable.addContainerProperty("City", String.class, null);
		dataTable.addContainerProperty("Zip", String.class, null);
		dataTable.addContainerProperty("Flag", String.class, null);
		dataTable.addContainerProperty("Status", String.class, null);

		instanceCnt.setValue("Instance Id: "+instance);
		ipInstance.setValue("Instsance IP: "+ipAddr);

		HorizontalLayout actions = new HorizontalLayout(dataTable);
		HorizontalLayout actions2 = new HorizontalLayout(killBtn);
		VerticalLayout mainLayout = new VerticalLayout(ipInstance,instanceCnt,dataCnt,addNewBtn,actions2,actions);
		setContent(mainLayout);

		// Configure layouts and components
		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		//ataCnt.setInputPrompt("Total Records");
		final String  putEndpoint=puturl;
		final String loadEndpoint=loadurl;
		//loadData(dataTable);

		killBtn.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				System.out.println("Kill the system. Goodbye!");
				try{
					java.lang.Runtime.getRuntime().exec("killall java ");
					//java.lang.Runtime.getRuntime().exec("killall tomcat ");
				} catch(Exception e)
				{
					e.printStackTrace();
				}
				//	System.exit(1);
			}});

		addNewBtn.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {

				try{
					String dataCntStr=dataCnt.getValue();
					pageNum++;
					System.out.println("Loading data from :"+loadEndpoint+"?page="+pageNum);
					Future<String> page1 = RestUtility.callRestGet(loadEndpoint+"?page="+pageNum);
					System.out.println(page1.get());

					JSONObject obj = new JSONObject(page1.get());
					int totalElements = obj.getJSONObject("page").getInt("totalElements");
					System.out.println("Total elements: "+totalElements);
					dataCnt.setValue("Total Processed: "+totalElements);

					/*
						JSONArray arr = obj.getJSONArray("location");
						for (int i = 0; i < arr.length(); i++)
						{
						    String zip = arr.getJSONObject(i).getString("zip");
						   System.out.println("zip: "+zip);
						}
					 */
					clearData(dataTable);
					JSONArray arr = obj.getJSONObject("_embedded").getJSONArray("location");
					for (int i = 0; i < arr.length(); i++)
					{
						String id = "";
						// String id = arr.getJSONObject(i).getString("id");
						String street = arr.getJSONObject(i).getString("street");
						String city = arr.getJSONObject(i).getString("city");
						String zip = arr.getJSONObject(i).getString("zip");
						String flag = arr.getJSONObject(i).getString("updateFlag");
						String status = arr.getJSONObject(i).getString("statusCode");


						loadData( dataTable, id, street, city,  zip,  flag,  status);

					}



				} catch(Exception e) {
					System.out.println(e);
				}

			}
		});


	}

}
