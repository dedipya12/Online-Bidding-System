import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaQuery;

public class PostDAO {

	public void addPost(Post bean) {
		Session session = SessionHibernate.getSessionFactory().openSession();
		session.beginTransaction();
		Integer id = (Integer)session.save(bean);
		MemCacheClass.addInCache(id.toString(), bean);
		session.getTransaction().commit();

		session.close();
	}

	public void removePost(String post_id) {
		
		double id = Double.parseDouble(post_id);
		
		Session session = SessionHibernate.getSessionFactory().openSession();
    	session.beginTransaction();
	    String stringQuery = "delete Post where post_id = :post_id";
	    
	    Query query = session.createQuery(stringQuery).setParameter("post_id", id);
	    
		int result=query.executeUpdate();
		System.out.println("result"+result);

		session.close();	
		
	}
	public ArrayList<HashMap> getPosts() {
		ArrayList<HashMap> myList = new ArrayList<HashMap>();
		Session session = SessionHibernate.getSessionFactory().openSession();
		session.beginTransaction();
		String stringQuery = "FROM Post ORDER BY postdate asc";
		Query query = session.createQuery(stringQuery);
		query.setMaxResults(10);
		List<Post> posts = query.getResultList();

		
		for (int i = 0; i < posts.size(); i++) {
			HashMap hash = new HashMap();
			hash.put("postDate", posts.get(i).getPostDate());
			hash.put("mobileName", posts.get(i).getMobile().getMobileName());
			hash.put("companyName", posts.get(i).getMobile().getCompanyName());
			hash.put("price", posts.get(i).getPrice());
			hash.put("postID", posts.get(i).getPostId());
			hash.put("maxBid", posts.get(i).getMaxBid());
			hash.put("status", posts.get(i).getStatus());
			myList.add(hash);
		}
		return myList;
	
	}

	public ArrayList<HashMap> getMyPosts(String userid) {
		ArrayList<HashMap> myList = new ArrayList<HashMap>();
		Session session = SessionHibernate.getSessionFactory().openSession();
		session.beginTransaction();
		String stringQuery = "FROM User WHERE user_id=" + Integer.parseInt(userid);
		Query query = session.createQuery(stringQuery);
		List<User> users = query.getResultList();
		
		stringQuery = "FROM Post WHERE user_id=" + users.get(0).getUserId();
		query = session.createQuery(stringQuery);
		List<Post> posts = query.getResultList();
		for (int i = 0; i < posts.size(); i++) {
			HashMap hash = new HashMap();
			hash.put("postDate", posts.get(i).getPostDate());
			hash.put("mobileName", posts.get(i).getMobile().getMobileName());
			hash.put("companyName", posts.get(i).getMobile().getCompanyName());
			hash.put("price", posts.get(i).getPrice());
			hash.put("postID", posts.get(i).getPostId());
			hash.put("maxBid", posts.get(i).getMaxBid());
			hash.put("status", posts.get(i).getStatus());
			myList.add(hash);
		}
		return myList;
	}

	public HashMap getPost(Double postID) {
		HashMap hash = new HashMap();
		if (MemCacheClass.getFromCache(postID.toString()) == null) {
		Session session = SessionHibernate.getSessionFactory().openSession();
		session.beginTransaction();
		String stringQuery = "FROM Post WHERE post_id=" + postID;
		Query query = session.createQuery(stringQuery);
		List<Post> post = query.getResultList();
		System.out.println("Let us c" + post.get(0).getPostDate());
		hash.put("mobileName", post.get(0).getMobile().getMobileName());
		hash.put("companyName", post.get(0).getMobile().getCompanyName());
		hash.put("price", post.get(0).getPrice());
		hash.put("date", post.get(0).getPostDate());
		hash.put("postowner", post.get(0).getUser().getUserId());
		hash.put("maxBid", post.get(0).getMaxBid());
		hash.put("status", post.get(0).getStatus());
		return hash;
		}
		
		Post singlePost = (Post)MemCacheClass.getFromCache(postID.toString());
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd"); 
		hash.put("mobilename", singlePost.getMobile().getMobileName());
		hash.put("authorname", singlePost.getMobile().getCompanyName());
		hash.put("price", singlePost.getPrice());
		System.out.println("Post date cache: "+df.format(singlePost.getPostDate()));
		hash.put("date", df.format(singlePost.getPostDate()));
		hash.put("postowner", singlePost.getUser().getUserId());
		// session.close();
		// return Response.ok(usr.get(0)).build();
		return hash;
	}

	public Post getPostForBid(Double postID, String bidPrice) {
		System.out.println("Inside PostDao");
		Session session = SessionHibernate.getSessionFactory().openSession();
		session.beginTransaction();
		String stringQuery = "FROM Post WHERE post_id=" + postID;
		Query query = session.createQuery(stringQuery);
		System.out.println(bidPrice);
		String stringMaxBidquery = "select max(bidPrice) from Bid where post_id=" + postID;
		List maxbidquery = session.createQuery(stringMaxBidquery).list();
		if (maxbidquery.get(0)!=null){
			double maxbid = ( (Double)maxbidquery.get(0) ).doubleValue();
		System.out.println("Max Value is:"+maxbid);
		Double currentbid = Double.parseDouble(bidPrice);
		System.out.println("Current Bid" + currentbid);
		
		String updatebid;
		if (currentbid>maxbid){
			System.out.println(currentbid+"\t"+postID);
			Query query1 = session.createSQLQuery("update post set maxBid = :bidPrice"+" where post_id= :postID");
			query1.setParameter("bidPrice", currentbid);
			query1.setParameter("postID", postID);
			int result = query1.executeUpdate();
			session.getTransaction().commit();
		}
		}
		else{
			
			Query query1 = session.createSQLQuery("update post set maxBid = :bidPrice"+" where post_id= :postID");
			query1.setParameter("bidPrice", bidPrice);
			query1.setParameter("postID", postID);
			int result = query1.executeUpdate();
			session.getTransaction().commit();
		}
		
		List<Post> post = query.getResultList();
		System.out.println("Let us c" + post.get(0).getPostDate());
		// session.close();
		System.out.println("Exiting PostDao");
		return post.get(0);
	}

	public ArrayList<HashMap> getPostsByName(String mobilename) {
		ArrayList<HashMap> myList = new ArrayList<HashMap>();
		Session session = SessionHibernate.getSessionFactory().openSession();
		session.beginTransaction();

		String stringQuery = "FROM Mobile WHERE mobilename LIKE'" + mobilename + "'";
		Query query = session.createQuery(stringQuery);
		List<Mobile> mobile = query.getResultList();


		if (mobile.size() > 0) {
			stringQuery = "FROM Post WHERE mobile_id=" + mobile.get(0).getMobileId();
			query = session.createQuery(stringQuery);
			List<Post> posts = query.getResultList();

			for (int i = 0; i < posts.size(); i++) {
				HashMap hash = new HashMap();
				hash.put("postDate", posts.get(i).getPostDate());
				hash.put("mobileName", posts.get(i).getMobile().getMobileName());
				hash.put("companyName", posts.get(i).getMobile().getCompanyName());
				hash.put("price", posts.get(i).getPrice());
				hash.put("postID", posts.get(i).getPostId());
				myList.add(hash);
			}
		}
	
		return myList;
	}
	
	public Post getPostForBid(int postId)
	{
    	Session session = SessionHibernate.getSessionFactory().openSession();
    	session.beginTransaction();
	    String stringQuery = "FROM Post WHERE post_id=" + postId;
	    Query query = session.createQuery(stringQuery);
	    List<Post> post = query.getResultList();
	    System.out.println("Let us c" + post.get(0).getPostDate());
	    
	    return post.get(0);	
	}

}
