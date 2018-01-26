import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

@Path("microservice")
public class Microservices {
	static String restSecretKey = "abcd";
	
	@GET
    @Path("/checkuserexists/{userName}/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean checkUserExists(@PathParam("userName") String username, @PathParam("email") String email){
	UserDAO usr = new UserDAO();
	boolean result = usr.checkUserExists(username, email);
	
	return result;
	}
	
	@POST
    @Path("/signup")
	@Produces(MediaType.APPLICATION_JSON)
	public String createUser(@FormParam("name") String name, @FormParam("address") String address, @FormParam("city") String city, @FormParam("country") String country, @FormParam("state") String state, @FormParam("userName") String userName, @FormParam("password") String password, @FormParam("email") String email, @FormParam("gender") String gender, @FormParam("secretKey") String secretKey){
		if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
		
		User usr = new User();
        usr.setName(name);
        usr.setAddress(address);
        usr.setCity(city);
        usr.setState(state);
        usr.setCountry(country);
        usr.setUsername(userName);
        usr.setPassword(password);
        usr.setEmail(email);
        
        System.out.println("Initial constraints are: "+usr.getName() + usr.getUsername() + usr.getEmail());

        usr.setCreatedDate(new java.sql.Date(new Date().getTime()));
        usr.setLastLoginDate(new java.sql.Date(new Date().getTime()));
        usr.setLocation("Location not set");
        usr.setGender(gender);
        
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Calendar calobj = Calendar.getInstance();
        usr.setLastLoginTime(df.format(calobj.getTime()));
                
        UserDAO dao = new UserDAO();
        dao.addUser(usr);
        
        return "OK";
	}
	
	@POST
    @Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateUser(@FormParam("name") String name, @FormParam("address") String address, @FormParam("city") String city, @FormParam("state") String state, @FormParam("country") String country, @FormParam("userid") String userid, @FormParam("email") String email, @FormParam("gender") String gender, @FormParam("secretKey") String secretKey) throws UnsupportedEncodingException{
		if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
		UserDAO dao = new UserDAO();
        dao.updateUser(URLDecoder.decode(name, "UTF-8"), URLDecoder.decode(userid, "UTF-8"),URLDecoder.decode(address, "UTF-8"), URLDecoder.decode(city, "UTF-8"), URLDecoder.decode(state, "UTF-8"), URLDecoder.decode(country, "UTF-8"), URLDecoder.decode(email, "UTF-8"), URLDecoder.decode(gender, "UTF-8"));
		return "OK";		
	}
	@GET
    @Path("/getprofile/{secretKey}/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getProfile(@PathParam("userid") String userid, @PathParam("secretKey") String secretKey) {
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	UserDAO dao = new UserDAO();
    	User currentUser = dao.getUserById(userid);
    	return currentUser;
    }
	@GET
	@Path("/signin/{secretKey}/{userName}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("secretKey") String secretKey, @PathParam("userName") String userName, @PathParam("password") String password) {
//    	if (!restSecretKey.equals(secretKey)) {
//    		return null;
//    	}
    	UserDAO dao = new UserDAO();
    	User currentUser = dao.getUser(userName, password);
    	return currentUser;
    }
	
	
	@GET
    @Path("/getMyBids/{secretKey}/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<HashMap> getMyBids(@PathParam("secretKey") String secretKey, @PathParam("userid") String userid)
    {
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	BidDAO dao = new BidDAO();
    	ArrayList<HashMap> entries = dao.getMyBids(userid);
    	
    	GenericEntity<ArrayList<HashMap>> entity = new GenericEntity<ArrayList<HashMap>>(entries) {};
    	return entries;
    }
    
    @DELETE
    @Path("/deleteBids/{secretKey}/{bid_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteBids(@PathParam("secretKey") String secretKey,@PathParam("bid_id") String bidid){
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	System.out.println("in service delete");
    	BidDAO dao = new BidDAO();
    	dao.removeBids(bidid);
    	
    	return "OK";
    }
    
    @DELETE
    @Path("/deletePost/{post_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deletePost(@PathParam("post_id") String postid){
//    	if (!restSecretKey.equals(secretKey)) {
//    		return null;
//    	}
    	System.out.println("in service delete");
    	PostDAO dao = new PostDAO();
    	dao.removePost(postid);
    	
    	return "OK";
    }
	
	
    @GET
    @Path("/getPost/{secretKey}/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap getPost(@PathParam("secretKey") String secretKey, @PathParam("postId") Double postId)
    {
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	//System.out.println("Over here");
    	PostDAO dao = new PostDAO();
    	HashMap entries =  dao.getPost(postId);
    	return entries;
    	    	
    }
    
    @GET
    @Path("/getPosts/{secretKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<HashMap> getPosts(@PathParam("secretKey") String secretKey)
    {
    	System.out.println("Inside Mobilebids getPosts");
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	//System.out.println("Over here");
    	PostDAO dao = new PostDAO();
    	ArrayList<HashMap> entries = dao.getPosts();
    	
    	GenericEntity<ArrayList<HashMap>> entity = new GenericEntity<ArrayList<HashMap>>(entries) {};
    	System.out.println("Exiting Mobilebids getPosts");
    	return entries;
    	
    	
    }
    @GET
    @Path("/getMyPosts/{secretKey}/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<HashMap> getMyPosts(@PathParam("secretKey") String secretKey, @PathParam("userid") String userid)
    {
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	PostDAO dao = new PostDAO();
    	ArrayList<HashMap> entries = dao.getMyPosts(userid);
    	
    	GenericEntity<ArrayList<HashMap>> entity = new GenericEntity<ArrayList<HashMap>>(entries) {};
    	return entries;
    	
    	
    }
    
    @GET
    @Path("/getBids/{secretKey}/{postid}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<HashMap> getBids(@PathParam("secretKey") String secretKey, @PathParam("postid") Double postId)
    {
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	BidDAO dao = new BidDAO();
    	ArrayList<HashMap> entries = dao.getBids(postId);
    	GenericEntity<ArrayList<HashMap>> entity = new GenericEntity<ArrayList<HashMap>>(entries) {};
    	return entries;
    }
    
    @GET
    @Path("/getPostsByName/{secretKey}/{mobilename}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<HashMap> getPostsByName(@PathParam("secretKey") String secretKey, @PathParam("mobilename") String mobileName) throws UnsupportedEncodingException
    {
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	System.out.println("Mobile name is: "+mobileName);
    	System.out.println("Decoded name is: "+URLDecoder.decode(mobileName, "UTF-8"));
    	PostDAO dao = new PostDAO();
    	ArrayList<HashMap> entries = dao.getPostsByName(URLDecoder.decode(mobileName, "UTF-8"));
    	
    	GenericEntity<ArrayList<HashMap>> entity = new GenericEntity<ArrayList<HashMap>>(entries) {};
    	return entries;
    }
    
    @POST
    @Path("/addBid")
    public String addBid(@FormParam("userid") String userid, @FormParam("postID") String strpostid, @FormParam("bidPrice") String bidPrice, @FormParam("secretKey") String secretKey)
    {
    	System.out.println("Inside Mobilebids Addbid");
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	Double postID = Double.parseDouble(strpostid);
    	UserDAO dao = new UserDAO();
    	User currentUser = dao.getUserById(userid);

    	PostDAO postDAO = new PostDAO();
    	Post post = postDAO.getPostForBid(postID,bidPrice);
    	Bid bid = new Bid();

    	bid.setUser(currentUser);
    	bid.setBidDate(new Date());
    	bid.setBidPrice(Double.parseDouble(bidPrice));
    	bid.setPost(post);

    	BidDAO bidDAO = new BidDAO();
    	bidDAO.addBid(bid);
    	System.out.println("Exiting Mobilebids Addbid");
    	return "OK";
    }
	
	@POST
    @Path("/addPost")
	@Produces(MediaType.TEXT_PLAIN)
	public String addPost(@FormParam("userid") String userid, @FormParam("price") String price, @FormParam("mobileName") String mobileName, @FormParam("companyName") String companyName, @FormParam("secretKey") String secretKey)
    {
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	System.out.println(userid + price + mobileName + companyName);
    	UserDAO dao = new UserDAO();
    	User currentUser = dao.getUserById(userid);
    	
    	MobileDAO mobileDAO = new MobileDAO();
    	Mobile mobile = mobileDAO.getMobile(mobileName, companyName);
    	if (mobile == null) {
    		mobile = new Mobile();
    		mobile.setMobileName(mobileName);
    		mobile.setCompanyName(companyName);
    		mobileDAO.addMobile(mobile);
    	}
    	double pricedouble = Double.parseDouble(price);
    	Post post = new Post();

    	post.setUser(currentUser);
    	post.setPostDate(new Date());
    	post.setPrice(pricedouble);
    	post.setMobile(mobile);
    	post.setStatus("shelf");
    	post.setMaxBid(0.0);
    	PostDAO postDAO = new PostDAO();
    	postDAO.addPost(post);

    	return "OK";
    }
	
//	public Book getMobile(@PathParam("secretKey") String secretKey, @PathParam("name") String name, @PathParam("isbnNo") String isbnNo){
//    	if (!restSecretKey.equals(secretKey)) {
//    		return null;
//    	}
//    	MobileDAO mobileDAO = new MobileDAO();
//    	Book book = mobileDAO.getMobile(name, isbnNo);
//    	return book;
//    }
	
	@POST
    @Path("/updateLoginDetails")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateLoginDetails(@FormParam("date") String lastLoginDate, @FormParam("time") String lastLoginTime, @FormParam("location") String location, @FormParam("userid") String userid, @FormParam("secretKey") String secretKey)
    {
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	try {
    		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    		Date parsedDate = format.parse(lastLoginDate);
    		System.out.println("Parsed date is: "+parsedDate);
    		java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
    		UserDAO dao = new UserDAO();
    		dao.updateLoginDetails(sqlDate, lastLoginTime, location, userid);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return "OK";
    }


}
