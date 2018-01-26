import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Mobile implements Serializable{

    private int mobileId;
   
    private String mobileName;
    private String companyName;


    public Mobile(){
    	
    }
    public int getMobileId() {
    	return this.mobileId;
    }
    public void setMobileId(int id) {
    	this.mobileId = id;
    }

    public String getMobileName() {
        return this.mobileName;
    }
    public void setMobileName(String mobileName) {
        this.mobileName = mobileName;
    }
    
    public String getCompanyName() {
        return this.companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}
