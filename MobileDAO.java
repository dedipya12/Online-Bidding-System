import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

public class MobileDAO {

	public Mobile getMobile(String mobileName, String companyName)
	{
    	Session session = SessionHibernate.getSessionFactory().openSession();
    	session.beginTransaction();

	    String stringQuery = "FROM Mobile WHERE mobilename='" + mobileName + "' and company_name= '"+companyName+"'";
	    Query query = session.createQuery(stringQuery);
	    List<Mobile> mobile = query.getResultList();
	    session.close();
	    if (mobile.size() != 0)
	    {
	    	return mobile.get(0);
	    } else {
	    	return null;
	    }
	}
	
	public void addMobile(Mobile mobile)
	{
    	Session session = SessionHibernate.getSessionFactory().openSession();
    	session.beginTransaction();

        session.save( mobile );

	    session.getTransaction().commit();

	    
	    session.close();
	}
}
