package ecust.cs.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


import ecust.cs.model.BookInfo;
import ecust.cs.model.WebsiteInfo;
import ecust.cs.util.HibernateUtil;

public class WebsiteInfoDao {
	public static void addWebsiteInfo(WebsiteInfo websiteInfo){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		session.save(websiteInfo);
		
		ts.commit();
		session.close();
	}
	
	
	public static void updateWebsiteInfo(WebsiteInfo websiteInfo){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		session.update(websiteInfo);
		session.save(websiteInfo);
		
		ts.commit();
		session.close();
	}
	
	public static void updateOrSaveWebsiteInfo(WebsiteInfo websiteInfo){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		session.saveOrUpdate(websiteInfo);
		
		ts.commit();
		session.close();
	}
	
	//按书的属性获取书信息
	public static List<WebsiteInfo> findByProperty(String propertyName, Object value) {
		Session session = HibernateUtil.getSession();
		try {
			String queryString = "from WebsiteInfo as model where model."
					+ propertyName + " like ?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, "%" + value + "%");
			Transaction ts = session.beginTransaction();
			ts.commit();
			return queryObject.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
//	public static WebsiteInfo getWebsiteByBooKID(String bookID){
//		Session session = HibernateUtil.getSession();
//		Transaction ts = session.beginTransaction();
//		
//		ts.commit();
//		session.close();
//	}
		
		
	
	
	public static List<WebsiteInfo> getwebsiteInfobybook(String bookName){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		List<WebsiteInfo> websiteInfoList = new ArrayList<WebsiteInfo>();
		
		Query queryBook = session.createQuery("from BookInfo where bookName like ?");
		Query queryWebsite = session.createQuery("from WebsiteInfo where bookID = ?");
		
		queryBook.setString(0, "%" + bookName + "%");
		
		List<BookInfo> bookInfoList = queryBook.list();
		
		for(BookInfo bookInfo : bookInfoList){
			queryWebsite.setParameter(0, bookInfo.getBookID());
			
			websiteInfoList.addAll(queryWebsite.list());
			
		}
		ts.commit();
		session.close();
		
		return websiteInfoList;
		
	}
	
	public static List<WebsiteInfo> getWebsiteByClassify(String classify){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		List<WebsiteInfo> websiteInfoList = new ArrayList<WebsiteInfo>();
		
		Query queryWebsite = session.createQuery("from WebsiteInfo where category like ?");
		
		queryWebsite.setString(0, "%" + classify + "%");
		
		websiteInfoList = queryWebsite.list();
		
		ts.commit();
		session.close();
		
		return websiteInfoList;
	}
	
	
	public static WebsiteInfo WebsiteInfoIsExist(WebsiteInfo websiteInfo){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		WebsiteInfo wi = (WebsiteInfo) session.get(WebsiteInfo.class, websiteInfo.getURL());
		
		ts.commit();
		session.close();
		
		return wi;
	}
	
	public static void deleteWebsiteInfo(WebsiteInfo wi){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		session.delete(wi);
		
		ts.commit();
		session.close();
	}
	
	public static List<WebsiteInfo> getAll(){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		List<WebsiteInfo> websiteInfoList = new ArrayList<WebsiteInfo>();
		
		Query queryBook = session.createQuery("from WebsiteInfo");
		
		websiteInfoList = queryBook.list();
		
		ts.commit();
		session.close();
		
		return websiteInfoList;
	}
	
}
