package ecust.cs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ecust.cs.util.GenerateBookID;
import ecust.cs.util.HibernateUtil;
import ecust.cs.model.AllInfo;
import ecust.cs.model.BookInfo;
import ecust.cs.model.WebsiteInfo;

public class BookInfoDao {
	//存入数据库数据
	public static void addBookInfo(BookInfo bookInfo){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		session.save(bookInfo);
		
		ts.commit();
		session.close();
	}
	
	//升级数据库数据
	public static void updateBookInfo(BookInfo bookInfo){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		session.update(bookInfo);
		session.save(bookInfo);
		
		ts.commit();
		session.close();
	}
	
	public static void updateOrSaveWebsiteInfo(BookInfo bookInfo){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		session.saveOrUpdate(bookInfo);
		
		ts.commit();
		session.close();
	}
	
	public static String getBookInfoIDByBookName(String bookName){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		List<BookInfo> bookInfoList = new ArrayList<BookInfo>();
		
		Query queryBook = session.createQuery("from BookInfo where bookName = ?");
	
		queryBook.setString(0, bookName);
		
		bookInfoList = queryBook.list();
		
		ts.commit();
		session.close();
		
		if(bookInfoList.isEmpty()){
			return GenerateBookID.generateBookID();
		}else{
			return bookInfoList.get(0).getBookID();
		}
		
	}
	
	public static List<BookInfo> getNumBookInfo(int num){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		List<BookInfo> bookInfoList = new ArrayList<BookInfo>();
		
		Query queryBook = session.createQuery("from BookInfo");
		
		int count = (int) ((Math.random())*1000);
System.out.println(count);		
		queryBook.setFirstResult(count);
		queryBook.setMaxResults(num);
		
		bookInfoList = queryBook.list();
		
		ts.commit();
		session.close();
		
		return bookInfoList;
	}
	
	
	//按书的属性获取书信息
	public static List<BookInfo> findByProperty(String propertyName, Object value) {
		Session session = HibernateUtil.getSession();
		try {
			String queryString = "from BookInfo as model where model."
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
	
	
	
	public static List<AllInfo> getIndexBookRandom(int num){
		List<BookInfo> bookList = BookInfoDao.getNumBookInfo(num);
		
		List<AllInfo> allList = new ArrayList<AllInfo>();
		
		for(BookInfo bookInfo : bookList){
			if(bookInfo != null){
			
				AllInfo allInfo = new AllInfo();
				WebsiteInfo websiteInfo = (WebsiteInfo) WebsiteInfoDao.findByProperty("bookInfo.bookID", bookInfo.getBookID()).get(0);
				
				allInfo.setBookInfo(bookInfo);
				allInfo.setWebsiteInfo(websiteInfo);
				
				allList.add(allInfo);
			}
		
		}
		return allList;
		
	}
	
	//根据书名获取相关信息
	public static List<AllInfo> getBookInfoBySearch(String search){
		List<BookInfo> bookList = BookInfoDao.findByProperty("bookName", search);
		
		List<AllInfo> allList = new ArrayList<AllInfo>();
		
		for(BookInfo bookInfo : bookList){
			if(bookInfo != null){
			
				WebsiteInfo websiteInfo =  new WebsiteInfo();
				
				AllInfo allInfo = new AllInfo();
System.out.println(bookInfo.getBookID());
				if(!WebsiteInfoDao.findByProperty("bookInfo.bookID", bookInfo.getBookID()).isEmpty()){
					websiteInfo = (WebsiteInfo) WebsiteInfoDao.findByProperty("bookInfo.bookID", bookInfo.getBookID()).get(0);
				}
			
				
				allInfo.setBookInfo(bookInfo);
				allInfo.setWebsiteInfo(websiteInfo);
				
				allList.add(allInfo);
			}
		}
		return allList;
	}
	
	//判断数据库中是否有该条数据
	public static boolean BookInfoIsExist(BookInfo bookInfo){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		List<BookInfo> bookInfoList = new ArrayList<BookInfo>();
		
		Query queryBook = session.createQuery("from BookInfo where bookName = ?");
		
		queryBook.setString(0, bookInfo.getBookName());
		
		bookInfoList = queryBook.list();
		
		ts.commit();
		session.close();
		
		if(bookInfoList.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	
	public static void deleteInfoByURL(WebsiteInfo websiteInfo){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		String bookInfoID = websiteInfo.getBookInfo().getBookID();
		
		WebsiteInfo websiteInfoBuf = (WebsiteInfo) session.get(WebsiteInfo.class, websiteInfo.getURL());
		
		BookInfo bookInfoBuf = (BookInfo) session.get(BookInfo.class, bookInfoID);
		
		session.delete(websiteInfoBuf);
		
		session.delete(bookInfoBuf);
		
		ts.commit();
		session.close();
		
	}
	
	public static void deleteBookInfo(BookInfo bi){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		session.delete(bi);
		
		ts.commit();
		session.close();
	}
	
	
	public static List<BookInfo> getAll(){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		List<BookInfo> bookInfoList = new ArrayList<BookInfo>();
		
		Query queryBook = session.createQuery("from BookInfo");
		
		bookInfoList = queryBook.list();
		
		ts.commit();
		session.close();
		
		return bookInfoList;
	}
	
}

