import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.persistence.Query;

import org.hibernate.Session;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SellJob implements Job
{
	public void execute(JobExecutionContext context)
	throws JobExecutionException {

//		System.out.println("Hello Quartz!");
		
		Session session = SessionHibernate.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Post");
		List<Post> posts = query.getResultList();
		ArrayList<HashMap> myList = new ArrayList<HashMap>();
		String email = "";
		String status="";
		for (int i = 0; i < posts.size(); i++) {
//			HashMap hash = new HashMap();
//			hash.put("postid", posts.get(i).getPostId());
//			hash.put("postdate", posts.get(i).getPostDate());
//			hash.put("price", posts.get(i).getPrice());
//			
//			hash.put("bookid", posts.get(i).getBook().getBookId());
//			hash.put("maxbid", posts.get(i).getMaxBid());
//			hash.put("status", posts.get(i).getStatus());
//			myList.add(hash);
			email = posts.get(i).getUser().getEmail();
			status = posts.get(i).getStatus();
			if(!status.equals("Sold")){
				System.out.println("User Email Address is :" + posts.get(i).getUser().getEmail());
			Query updatequery = session.createQuery("update Post set status = :Status");
			updatequery.setParameter("Status", "Sold");
			int result = updatequery.executeUpdate();
			System.out.println("Mail Sent to"+email);
			try {
				SendConfirmationMail.generateAndSendEmail(email);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		
		 
//		while(i<result.size()){
//			System.out.println(result.get(0));
//			i++;
//		}
		
		session.getTransaction().commit();
		

	}

}