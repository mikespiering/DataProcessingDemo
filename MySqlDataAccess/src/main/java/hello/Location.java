package hello;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String mongoId;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String updateFlag;
    private String statusCode;

    public Location(String mongoId, String street,String city, String state, String zip, String updateFlag, String statusCode)
    {
	this.mongoId=mongoId;
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


}
