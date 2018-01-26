import java.io.Serializable;
import java.sql.Time;
import java.sql.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable{
    private int userId;
     
    private String name;
    private String country;
    private String city;
    private String state;
    private String address;
    private Date createdDate;
    private String email;
    private String username;
    private String password;
    private String gender;
    private Date lastLoginDate;
    private String location;
    private String lastLoginTime;
    
    public User(){
    	
    }

    public int getUserId()
    {
    	return this.userId;
    }
    public void setUserId(int userId) {
    	this.userId = userId;
    }
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String name) {
        this.address = name;
    }
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String name) {
        this.city = name;
    }
    public String getState() {
        return this.state;
    }
    
    public void setState(String name) {
        this.state = name;
    }
    public String getCountry() {
        return this.country;
    }
    
    public void setCountry(String name) {
        this.country = name;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getGender() {
    	return gender;
    }
    
    public void setGender(String gender)
    {
    	this.gender = gender;
    }
    
    public Date getLastLoginDate() {
        return this.lastLoginDate;
    }
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
    
    public String getLastLoginTime() {
        return this.lastLoginTime;
    }
    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
