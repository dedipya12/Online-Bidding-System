class MicroConstants {

	public static final String secretKey = "abcd";
	public static final String requestUserExistsREQUEST = "http://localhost:8080/Jersey/rest/microservice/checkuserexists/";
	public static final String SIGNINREQUEST = "http://localhost:8080/Jersey/rest/microservice/signin/" +secretKey + "/";
	public static final String SIGNUPREQUEST = "http://localhost:8080/Jersey/rest/microservice/signup";
	public static final String UPDATEREQUEST = "http://localhost:8080/Jersey/rest/microservice/update";
	public static final String GETMYBIDSREQUEST = "http://localhost:8080/Jersey/rest/microservice/getMyBids/"+secretKey+"/";
	public static final String DELETEBIDSREQUEST = "http://localhost:8080/Jersey/rest/microservice/deleteBids/"+secretKey+"/";
	public static final String DELETEPOSTREQUEST = "http://localhost:8080/Jersey/rest/microservice/deletePost/"+secretKey+"/";
	public static final String UPDATELOGINDETAILSREQUEST = "http://localhost:8080/Jersey/rest/microservice/updateLoginDetails";
	public static final String ADDPOSTREQUEST = "http://localhost:8080/Jersey/rest/microservice/addPost";
	public static final String GETMYPOSTSREQUEST = "http://localhost:8080/Jersey/rest/microservice/getMyPosts/" +secretKey + "/";
	public static final String GETPOSTREQUEST = "http://localhost:8080/Jersey/rest/microservice/getPost/" + secretKey + "/";
	public static final String ADDBIDREQUEST = "http://localhost:8080/Jersey/rest/microservice/addBid";
	public static final String GETBIDSREQUEST = "http://localhost:8080/Jersey/rest/microservice/getBids/" + secretKey + "/";
	public static final String GETPOSTSBYNAMEREQUEST = "http://localhost:8080/Jersey/rest/microservice/getPostsByName/" + secretKey + "/";
	public static final String GETPROFILEREQUEST = "http://localhost:8080/Jersey/rest/microservice/getprofile/" + secretKey + "/";
	public static final String GETHOMEPOSTSREQUEST = "http://localhost:8080/Jersey/rest/microservice/getPosts/" + secretKey + "/";
	
	
}
