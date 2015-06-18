package ecust.cs.handle;

import java.util.ArrayList;
import java.util.List;

import ecust.cs.dao.BookInfoDao;
import ecust.cs.dao.WebsiteInfoDao;
import ecust.cs.model.BookInfo;
import ecust.cs.model.WebsiteInfo;

public class ClearDB {
	public static void clearDB(){
		 List<BookInfo> bookInfoList = BookInfoDao.getAll();
		 List<WebsiteInfo> websiteInfoList = WebsiteInfoDao.getAll();
		 
		 List<String> idList = new  ArrayList<String>();
		 
		 for(BookInfo bi : bookInfoList){
			 idList.add(bi.getBookID());
		 }
		 
		 for(WebsiteInfo wi : websiteInfoList){
			 if(idList.contains(wi.getBookInfo().getBookID())){
				 
			 }
		 }
		 
	}
}
