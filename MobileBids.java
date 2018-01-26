
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jettison.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;

 
@Path("mobilebiddingservice")
public class MobileBids { 
    
	static String restSecretKey = "abcd";
    @POST
    @Path("/signup")
    public Response addUser(@FormParam("name") String name, @FormParam("address") String address, @FormParam("city") String city, @FormParam("country") String country, @FormParam("state") String state, @FormParam("userName") String userName, @FormParam("password") String password, @FormParam("email") String email, @FormParam("gender") String gender, @FormParam("secretKey") String secretKey){
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	String requestUserExists = MicroConstants.requestUserExistsREQUEST + userName + "/"+email;
    	System.out.println("requestStirng is: "+requestUserExists);
    	Client client = Client.create();
		WebResource webResource = client
		   .resource(requestUserExists);

		ClientResponse jsonResponse = webResource.accept("application/json")
                   .get(ClientResponse.class);

		String str = jsonResponse.getEntity(String.class);
		String adduser="";
		Boolean userexists = Boolean.parseBoolean(str);
		if(userexists==true){
			String requestSignUp = MicroConstants.SIGNUPREQUEST;
	    	System.out.println("requestStirng is: "+requestSignUp);
	    	Client client1 = Client.create();
			WebResource webResource1 = client1
			   .resource(requestSignUp); 
			MultivaluedMap formData = new MultivaluedMapImpl();
			formData.add("userName", userName);
			formData.add("password", password);
			formData.add("name", name);
			formData.add("address", address);
			formData.add("city", city);
			formData.add("state", state);
			formData.add("country", country);
			formData.add("email", email);
			formData.add("gender", gender);
			formData.add("secretKey", restSecretKey);
			ClientResponse jsonResponse1 = webResource1
				    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				    .post(ClientResponse.class, formData);
			adduser = jsonResponse1.getEntity(String.class);
			
		}
		if(adduser.equals("OK")){
			return Response.ok().build();
		}
		else {
			return Response.status(300).build();     
		}
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/updateUser")
    public Response updateUser(@FormParam("name") String name, @FormParam("userid") String userid, @FormParam("address") String address, @FormParam("city") String city, @FormParam("country") String country, @FormParam("state") String state, @FormParam("email") String email, @FormParam("gender") String gender, @FormParam("secretKey") String secretKey) throws UnsupportedEncodingException{
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}        
    	String updateUser = "";
    	String requestUpdate = MicroConstants.UPDATEREQUEST;
    	System.out.println("requestStirng is: "+requestUpdate);
    	Client client = Client.create();
		WebResource webResource = client
		   .resource(requestUpdate); 
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("name", name);
		formData.add("userid", userid);
		formData.add("address", address);
		formData.add("city", city);
		formData.add("state", state);
		formData.add("country", country);
		formData.add("email", email);
		formData.add("gender", gender);
		formData.add("secretKey", restSecretKey);
		ClientResponse jsonResponse = webResource
			    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
			    .post(ClientResponse.class, formData);
		updateUser = jsonResponse.getEntity(String.class);

        if(updateUser.equals("OK")){
        return Response.status(200).build();
        }
        else{
        	return Response.status(300).build();	
        }
    }

    @POST
    @Path("/updateLoginDetails")
    public Response updateLoginDetails(@FormParam("date") String lastLoginDate, @FormParam("time") String lastLoginTime, @FormParam("location") String location, @FormParam("userid") String userid, @FormParam("secretKey") String secretKey)
    {
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	Client client = Client.create();

		String requestString = MicroConstants.UPDATELOGINDETAILSREQUEST;
		WebResource webResource = client.resource(requestString);

		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("userid", userid);
		formData.add("date", lastLoginDate);
		formData.add("time", lastLoginTime);
		formData.add("location", location);
		formData.add("secretKey", secretKey);
		ClientResponse jsonResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(ClientResponse.class, formData);
		String updateLoginDetails = jsonResponse.getEntity(String.class);

        if(updateLoginDetails.equals("OK")){
        return Response.status(200).build();
        }
        else{
        	return Response.status(300).build();	
        }
    }

    @GET
    @Path("/signin/{secretKey}/{userName}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("secretKey") String secretKey, @PathParam("userName") String username, @PathParam("password") String password) {
    	String requestString = MicroConstants.SIGNINREQUEST + username + "/"+password+"/";
    	System.out.println("requestStirng is: "+requestString);
    	Client client = Client.create();
		WebResource webResource = client
		   .resource(requestString);

		ClientResponse jsonResponse = webResource.accept("application/json")
                   .get(ClientResponse.class);
		Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
		String json = jsonResponse.getEntity(String.class);
		User currentUser = gson.fromJson(json, User.class);
		return currentUser;

    }
    
    @GET
    @Path("/getprofile/{secretKey}/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("secretKey") String secretKey, @PathParam("userid") String userid) {
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	
    	String requestString = MicroConstants.GETPROFILEREQUEST + userid + "/";
    	System.out.println("requestStirng is: "+requestString);
    	Client client = Client.create();
		WebResource webResource = client
		   .resource(requestString);

		ClientResponse jsonResponse = webResource.accept("application/json")
                   .get(ClientResponse.class);
		Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
		String json = jsonResponse.getEntity(String.class);
		User currentUser = gson.fromJson(json, User.class);
		return currentUser;
    }

    @POST
    @Path("/addPost")
    public Response addPost(@FormParam("userid") String userid, @FormParam("price") double price, @FormParam("mobileName") String mobileName, @FormParam("companyName") String companyName, @FormParam("secretKey") String secretKey)
    {
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	String requestString = MicroConstants.ADDPOSTREQUEST;
		Client client = Client.create();
		WebResource webResource = client
		   .resource(requestString);
		String pricestr = Double.toString(price);
		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("userid", userid);
		formData.add("mobileName", mobileName);
		formData.add("companyName", companyName);
		formData.add("price", pricestr);
		formData.add("secretKey", secretKey);
		
		ClientResponse jsonResponse = webResource
		    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
		    .post(ClientResponse.class, formData);

		String addpost = jsonResponse.getEntity(String.class);

        if(addpost.equals("OK")){
        return Response.status(200).build();
        }
        else{
        	return Response.status(300).build();	
        }

    }

    @GET
    @Path("/getPost/{secretKey}/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPost(@PathParam("secretKey") String secretKey, @PathParam("postId") Double postId)
    {
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	
    	String requestString = MicroConstants.GETPOSTREQUEST + postId +"/";
    	ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJaxbJsonProvider.class);
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

    	Client client = Client.create(config);
    	WebResource webResource = client.resource(requestString);
    	ClientResponse jsonResponse = webResource.accept("application/json").get(ClientResponse.class);
		
			Gson gson=  new GsonBuilder().create();
			String json = jsonResponse.getEntity(String.class);
			HashMap entries = gson.fromJson(json, HashMap.class);
			return Response.status(200).entity(entries).build();
		
    	
    }
    

    
    @GET
    @Path("/getPosts/{secretKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPosts(@PathParam("secretKey") String secretKey)
    {
    	System.out.println("Inside MobilebiddingService getPosts");
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	    	
        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJaxbJsonProvider.class);
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
    	Client client = Client.create(config);

    	String requestString = MicroConstants.GETHOMEPOSTSREQUEST;
    	WebResource webResource = client
    	   .resource(requestString);

    	ClientResponse jsonResponse = webResource.accept("application/json")
                   .get(ClientResponse.class);

    		Gson gson=  new GsonBuilder().create();
			String json = jsonResponse.getEntity(String.class);
			ArrayList<HashMap> entries = gson.fromJson(json, ArrayList.class);
    		return Response.status(200).entity(entries).build();
    	
    }



    @GET
    @Path("/getMyPosts/{secretKey}/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyPosts(@PathParam("secretKey") String secretKey, @PathParam("userid") String userid)
    {
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	
        String requestString = MicroConstants.GETMYPOSTSREQUEST + userid;
    	ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJaxbJsonProvider.class);
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
    	Client client = Client.create(config);
    	WebResource webResource = client
    	   .resource(requestString);

    	ClientResponse jsonResponse = webResource.accept("application/json")
                   .get(ClientResponse.class);

    	
    		Gson gson=  new GsonBuilder().create();
			String json = jsonResponse.getEntity(String.class);
			ArrayList<HashMap> entries = gson.fromJson(json, ArrayList.class);
        	return Response.status(200).entity(entries).build();
    		
    	
    }


    @POST
    @Path("/addBid")
    public Response addBid(@FormParam("userid") String userid, @FormParam("postID") String postID, @FormParam("bidPrice") String bidPrice, @FormParam("secretKey") String secretKey)
    {
    	System.out.println("Inside MobileBiddingService Addbid");
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	String requestString = MicroConstants.ADDBIDREQUEST;
    	Client client = Client.create();
		WebResource webResource = client
		   .resource(requestString);

		MultivaluedMap formData = new MultivaluedMapImpl();
		formData.add("userid", userid);
		formData.add("postID", postID);
		formData.add("bidPrice", bidPrice);
		formData.add("secretKey", secretKey);
		
		ClientResponse jsonResponse = webResource
		    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
		    .post(ClientResponse.class, formData);

		String addbid = jsonResponse.getEntity(String.class);

        if(addbid.equals("OK")){
        return Response.status(200).build();
        }
        else{
        	return Response.status(300).build();	
        }
    }
    
    @GET
    @Path("/getBids/{secretKey}/{postid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBids(@PathParam("secretKey") String secretKey, @PathParam("postid") Double postId)
    {
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	String requestString = MicroConstants.GETBIDSREQUEST + postId +"/";
    	ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJaxbJsonProvider.class);
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

    	Client client = Client.create(config);
    	WebResource webResource = client.resource(requestString);
    	ClientResponse jsonResponse = webResource.accept("application/json").get(ClientResponse.class);
		
			Gson gson=  new GsonBuilder().create();
			String json = jsonResponse.getEntity(String.class);
			ArrayList<HashMap> entries = gson.fromJson(json, ArrayList.class);
			return Response.status(200).entity(entries).build();

    }
    
    @GET
    @Path("/getMyBids/{secretKey}/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyBids(@PathParam("secretKey") String secretKey, @PathParam("userid") String userid)
    {
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
    	String requestString = MicroConstants.GETMYBIDSREQUEST+ userid;
    	ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJaxbJsonProvider.class);
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

    	Client client = Client.create(config);
    	WebResource webResource = client.resource(requestString);
    	ClientResponse jsonResponse = webResource.accept("application/json").get(ClientResponse.class);
		
			Gson gson=  new GsonBuilder().create();
			String json = jsonResponse.getEntity(String.class);
			ArrayList<HashMap> entries = gson.fromJson(json, ArrayList.class);
			return Response.status(200).entity(entries).build();
    }
    
    @DELETE
    @Path("/deleteBids/{secretKey}/{bid_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBids(@PathParam("secretKey") String secretKey,@PathParam("bid_id") String bidid){
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
		String requestString = MicroConstants.DELETEBIDSREQUEST + bidid;
		ClientConfig config = new DefaultClientConfig();
	    config.getClasses().add(JacksonJaxbJsonProvider.class);
	    config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(config);
		WebResource webResource = client
		   .resource(requestString);

		ClientResponse jsonResponse = webResource.accept("application/json")
				.delete(ClientResponse.class);
		String deletebid = jsonResponse.getEntity(String.class);

        if(deletebid.equals("OK")){
        return Response.status(200).build();
        }
        else{
        	return Response.status(300).build();	
        }
    }
    
    @DELETE
    @Path("/deletePost/{secretKey}/{post_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePost(@PathParam("secretKey") String secretKey,@PathParam("post_id") String postid){
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}
		String requestString = "http://localhost:8080/Jersey/rest/microservice/deletePost/"+ postid;
		ClientConfig config = new DefaultClientConfig();
	    config.getClasses().add(JacksonJaxbJsonProvider.class);
	    config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(config);
		WebResource webResource = client
		   .resource(requestString);

		ClientResponse jsonResponse = webResource.accept("application/json")
				.delete(ClientResponse.class);
		String deletepost = jsonResponse.getEntity(String.class);

        if(deletepost.equals("OK")){
        return Response.status(200).build();
        }
        else{
        	return Response.status(300).build();	
        }
    }
    
    @GET
    @Path("/getPostsByName/{secretKey}/{mobilename}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPostsByName(@PathParam("secretKey") String secretKey, @PathParam("mobilename") String mobileName) throws UnsupportedEncodingException
    {
    	if (!restSecretKey.equals(secretKey)) {
    		return null;
    	}	
    	String requestString = MicroConstants.GETPOSTSBYNAMEREQUEST+ URLEncoder.encode(mobileName, "UTF-8");
    	ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJaxbJsonProvider.class);
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

    	Client client = Client.create(config);
    	WebResource webResource = client.resource(requestString);
    	ClientResponse jsonResponse = webResource.accept("application/json").get(ClientResponse.class);
		
			Gson gson=  new GsonBuilder().create();
			String json = jsonResponse.getEntity(String.class);
			ArrayList<HashMap> entries = gson.fromJson(json, ArrayList.class);
			return Response.status(200).entity(entries).build();
    }
           

}
