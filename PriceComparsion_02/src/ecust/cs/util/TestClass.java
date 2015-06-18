package ecust.cs.util;

import java.util.List;

import ecust.cs.dao.BookInfoDao;
import ecust.cs.dao.WebsiteInfoDao;
import ecust.cs.model.BookInfo;
import ecust.cs.model.WebsiteInfo;



public class TestClass {
	
	public static void main(String [] args){
		WebsiteInfoDao wsd = new WebsiteInfoDao();
		
		BookInfoDao bid = new BookInfoDao();
		
		//List<WebsiteInfo> websiteInfoList = wsd.getWebsiteByClassify("本科教材");
		
//		for(WebsiteInfo websiteInfo : websiteInfoList){
//			System.out.println(websiteInfo.getURL());
//		}
//		System.out.println("查询集合大小为：" + websiteInfoList.size());
		
		List<BookInfo> bookList = bid.findByProperty("bookName", "秋山匡");
//		List<WebsiteInfo> websiteInfoList = wsd.findByProperty("category", "少儿");
		
		for(BookInfo bookInfo : bookList){
			System.out.println(bookInfo.getBookName() + bookInfo.getAuthor());
		}
		System.out.println("查询集合大小为：" + bookList.size());
		
//		for(WebsiteInfo websiteInfo : websiteInfoList){
//			System.out.println(websiteInfo.getURL());
//		}
//		System.out.println("查询集合大小为：" + websiteInfoList.size());
	}
}
