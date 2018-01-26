import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;

import java.sql.Date;

import org.hibernate.Session;

public class UserDAO {
    
    public void addUser(User bean){
    	Session session = SessionHibernate.getSessionFactory().openSession();
    	session.beginTransaction();

        Integer id = (Integer)session.save( bean );
        MemCacheClass.addInCache(bean.getUsername(), bean);
	    session.getTransaction().commit();
	    session.close();
        
    }
        
    public void updateUser(String name, String userid, String address, String city, String state, String country, String email, String gender) {
    	
    	Session session = SessionHibernate.getSessionFactory().openSession();
    	session.beginTransaction();

	    String stringQuery = "UPDATE User SET name = :name, address = :address, city = :city, state = :state, country = :country, email = :email, gender= :gender WHERE user_id="+Integer.parseInt(userid);
	    Query query = session.createQuery(stringQuery);
	    query.setParameter("address", address);
	    query.setParameter("city", city);
	    query.setParameter("state", state);
	    query.setParameter("country", country);
	    query.setParameter("name", name);
	    query.setParameter("email", email);
	    query.setParameter("gender", gender);
	    query.executeUpdate();
	    
	    session.getTransaction().commit();

	    session.close();

    }
    
    public void updateLoginDetails(Date lastLoginDate, String lastLoginTime, String location, String userid)
    {
    	Session session = SessionHibernate.getSessionFactory().openSession();
    	session.beginTransaction();

	    String stringQuery = "UPDATE User SET last_login_date= :lastLoginDate, last_login_time= :lastLoginTime, location= :location WHERE user_id="+userid;
	    Query query = session.createQuery(stringQuery);
	    query.setParameter("lastLoginDate", lastLoginDate);
	    query.setParameter("lastLoginTime", lastLoginTime);
	    query.setParameter("location", location);
	    query.executeUpdate();
	    
	    session.getTransaction().commit();


	    session.close();
    }

    public User getUser(String username, String password)
    {
    	User user = (User)MemCacheClass.getFromCache(username);    	 
    	if ( user != null) {
    		return user;
    	}
    	Session session = SessionHibernate.getSessionFactory().openSession();
    	session.beginTransaction();
	    String stringQuery = "FROM User WHERE username='"+username+"'and password='"+password+"'";
	    Query query = session.createQuery(stringQuery);
	    List<User> usr = query.getResultList();
	    session.close();
	    MemCacheClass.addInCache(usr.get(0).getUsername(), usr.get(0));
	    return usr.get(0);
    }
    
    public User getUserById(String userId)
    {
    	Session session = SessionHibernate.getSessionFactory().openSession();
    	session.beginTransaction();

	    String stringQuery = "FROM User WHERE user_id=" + userId;
	    Query query = session.createQuery(stringQuery);
	    List<User> usr = query.getResultList();
	    session.close();
	    return usr.get(0);
    }
    public boolean checkUserExists(String username, String email)
    {
    	Session session = SessionHibernate.getSessionFactory().openSession();
    	session.beginTransaction();
        boolean check=false;
	   String stringQuery = "FROM User WHERE username='" + username + "' and email= '"+email+"'";
	   List query = session.createQuery(stringQuery).list();
	   if(query.isEmpty())
	   {
	   	check=true;
	   }
    	session.close();
    	return check;
    }
}
