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
	
	
	public static boolean WebsiteInfoIsExist(WebsiteInfo websiteInfo){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		List<WebsiteInfo> websiteInfoList = new ArrayList<WebsiteInfo>();
		
		Query queryWebsite = session.createQuery("from WebsiteInfo where URL = ?");
		
		queryWebsite.setString(0, websiteInfo.getURL());
		
		websiteInfoList = queryWebsite.list();
		
		ts.commit();
		session.close();
		
		if(websiteInfoList.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
}
