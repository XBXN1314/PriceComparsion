package ecust.cs.util;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ClearDB {
	public static void clearDB(){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();

		session.delete("select * from WebsiteInfo");
		session.delete("select * from bookInfo");
		
		ts.commit();
		session.close();
	}
	
	
	public static void main(String[] args) {
		clearDB();
	}
}
