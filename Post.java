import java.io.Serializable;
import java.util.Date;

/*import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;*/

public class Post implements Serializable {
 
    private int postId;
    private double maxbid;
    private String status;
    private User user;
    private Date postDate;
    private Mobile mobile;
    private Double price;
    public Post(){
    	
    }
    public int getPostId() {
    	return this.postId;
    }
    
    public void setPostId(int postId) {
    	this.postId = postId;
    }

    public User getUser()
    {
    	return user;
    }
    public void setUser(User user)
    {
    	this.user = user;
    }
    public Double getMaxBid()
    {
    	return this.maxbid;
    }
    public void setMaxBid(Double maxbid)
    {
    	this.maxbid = maxbid;
    }
    public String getStatus()
    {
    	return this.status;
    }
    public void setStatus(String status)
    {
    	this.status = status;
    }

    public Date getPostDate() {
        return postDate;
    }
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
    
    public Mobile getMobile()
    {
    	return this.mobile;
    }
    public void setMobile(Mobile mobile)
    {
    	this.mobile = mobile;
    }
    
    public Double getPrice()
    {
    	return this.price;
    }
    public void setPrice(Double price)
    {
    	this.price = price;
    }
}
