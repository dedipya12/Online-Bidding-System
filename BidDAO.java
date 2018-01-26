import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

public class BidDAO {

	public void addBid(Bid bean)
	{
    	Session session = SessionHibernate.getSessionFactory().openSession();
    	session.beginTransaction();
        session.save( bean );

	    session.getTransaction().commit();

	    
	    session.close();
	}
	
	public ArrayList<HashMap> getBids(Double postId)
	{
		ArrayList<HashMap> myList = new ArrayList<HashMap>();
    	Session session = SessionHibernate.getSessionFactory().openSession();
    	session.beginTransaction();

		String stringQuery = "FROM Bid WHERE post_id="+postId;
	    Query query = session.createQuery(stringQuery);
	    List<Bid> bids = query.getResultList();
	    for (int i=0;i<bids.size();i++)
	    {
	    	HashMap hash = new HashMap();
	    	hash.put("bidDate", bids.get(i).getBidDate());
	    	hash.put("bidId", bids.get(i).getBidId());
	    	hash.put("username", bids.get(i).getUser().getUsername());
	    	hash.put("bidPrice", bids.get(i).getBidPrice());
	    	hash.put("name", bids.get(i).getUser().getName());
	    	hash.put("postId", bids.get(i).getPost().getPostId());
	    	myList.add(hash);
	    }
	    
	    return myList;
	}
	public ArrayList<HashMap> getMyBids(String userid) {
		ArrayList<HashMap> myList = new ArrayList<HashMap>();
		Session session = SessionHibernate.getSessionFactory().openSession();
		session.beginTransaction();
		String stringQuery = "FROM User WHERE user_id=" + Integer.parseInt(userid);
		Query query = session.createQuery(stringQuery);
		List<User> users = query.getResultList();
		System.out.println("USer id is: " + users.get(0).getUserId());
		stringQuery = "FROM Bid WHERE user_id=" + users.get(0).getUserId();
		query = session.createQuery(stringQuery);
		List<Bid> bids = query.getResultList();
		for (int i = 0; i < bids.size(); i++) {
			HashMap hash = new HashMap();
			hash.put("BidDate", bids.get(i).getBidDate());
			hash.put("mobileName", bids.get(i).getPost().getMobile().getMobileName());
			hash.put("companyName", bids.get(i).getPost().getMobile().getCompanyName());
			hash.put("bidprice", bids.get(i).getBidPrice());
			hash.put("bidID", bids.get(i).getBidId());
			myList.add(hash);
		}
		return myList;
}
	public void removeBids(String bid_id) {
		
		double id = Double.parseDouble(bid_id);
		
		Session session = SessionHibernate.getSessionFactory().openSession();
    	session.beginTransaction();
	   String stringQuery = "delete from Bid where bid_id = :bid_id";
	   
	   Query query = session.createQuery(stringQuery).setParameter("bid_id", id);
	   
		int result=query.executeUpdate();
		System.out.println("result"+result);

		session.close();	
		
	}
	public Bid getBid(long bidId){
		Session session = SessionHibernate.getSessionFactory().openSession();
    	session.beginTransaction();
	    String stringQuery = "FROM Bid WHERE bid_Id=" + bidId;
	    Query query = session.createQuery(stringQuery);
	    List<Bid> bid = query.getResultList();
	    System.out.println("bid:"+bid.get(0).getBidId()+bid.get(0).getBidPrice());
	    return bid.get(0);
		
	}
}
