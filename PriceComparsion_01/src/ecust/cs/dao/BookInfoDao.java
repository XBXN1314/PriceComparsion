package ecust.cs.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ecust.cs.util.GenerateBookID;
import ecust.cs.util.HibernateUtil;
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
	
	@SuppressWarnings("unchecked")
	public static String getBookInfoIDByBookName(String bookName){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		GenerateBookID gb = new GenerateBookID();
		
		List<BookInfo> bookInfoList = new ArrayList<BookInfo>();
		
		Query queryBook = session.createQuery("from BookInfo where bookName = ?");
	
		queryBook.setString(0, bookName);
		
		bookInfoList = queryBook.list();
		
		ts.commit();
		session.close();
		
		if(bookInfoList.isEmpty()){
			return gb.generateBookID();
		}else{
			return bookInfoList.get(0).getBookID();
		}
		
	}
	
	public static List<BookInfo> getBookByBookName(String bookName){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		List<BookInfo> bookInfoList = new ArrayList<BookInfo>();
		
		Query queryBook = session.createQuery("from BookInfo where bookName like ?");
	
		queryBook.setString(0, "%" + bookName + "%");
		
		bookInfoList = queryBook.list();
		
		ts.commit();
		session.close();
		
		return bookInfoList;
		
	}
	
	
	public static BookInfo getBookByBookId(String bookId){
		Session session = HibernateUtil.getSession();
		Transaction ts = session.beginTransaction();
		
		BookInfo bookInfo = (BookInfo)session.get(BookInfo.class, bookId);
		
		ts.commit();
		session.close();
		
		return bookInfo;
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
	
	
}
