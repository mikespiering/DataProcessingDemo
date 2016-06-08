package hello;

import org.springframework.data.annotation.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

//10000999556515111113,000,0,15360 T STREET NW,City,NV,89101
public class Location {

    private String id;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String updateFlag;
    private String statusCode;

    public Location(String id, String street,String city, String state, String zip, String updateFlag, String statusCode)
    {
	this.id=id;
	this.street=street;
	this.city=city;
	this.state=state;
	this.zip=zip;
	this.updateFlag=updateFlag;
	this.statusCode=statusCode;
    }
    public Location()
    {}
   
    public String getStreet() {
	return street;
    }
    
    public void setStreet(String street) {
		this.street = street;
    }
    
    public String getCity() {
	return city;
    }

    public void setCity(String city ) {
	this.city = city;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
	return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
    
    public String getStatusCode() {
	return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode ;
    }

    public String getUpdateFlag() {
	return updateFlag;
    }

    public void setUpdateFlag(String updateFlag ) {
        this.updateFlag = updateFlag;
    }

    public String getId()
    {
	return id;
    }
    public void setId(String id)
    {
	this.id=id;
    }
}
