package ecust.cs.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
	private static SessionFactory sf;
	public HibernateUtil(){
		
	}
	static{
		Configuration cf = new AnnotationConfiguration();
		cf.configure("/hibernate.cfg.xml").configure("/hibernate.map.xml");
		sf = cf.buildSessionFactory();
	}
	
	public static Session getSession(){
		Session s = sf.openSession();
		return s;
	}
	
	public static void closeSession(){
		sf.openSession().close();
	}
	
}
