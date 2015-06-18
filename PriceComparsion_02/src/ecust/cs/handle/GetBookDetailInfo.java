package ecust.cs.handle;

import java.util.ArrayList;
import java.util.List;

import ecust.cs.dao.BookInfoDao;
import ecust.cs.dao.WebsiteInfoDao;
import ecust.cs.model.AllInfo;
import ecust.cs.model.BookInfo;
import ecust.cs.model.WebsiteInfo;

public class GetBookDetailInfo {
	public static List<AllInfo> getBookDetailInfo(String picUrl){
		List<AllInfo> allInfoList = new ArrayList<AllInfo>();
		
		List<WebsiteInfo> wsiList = WebsiteInfoDao.findByProperty("picURL", picUrl);
		
		//原始书籍网站信息
		WebsiteInfo wiOri = wsiList.get(0);
		
		//便于得到此书籍对应的书本信息
		String bookId = wiOri.getBookInfo().getBookID();
		
		//便于得到其他网站所对应的书本信息
		String bookName = wiOri.getBookInfo().getBookName();
		
		List<BookInfo> biList = BookInfoDao.findByProperty("bookID", bookId);
		
		//原始书籍书籍基本信息
		BookInfo biOri = biList.get(0);
		
		AllInfo ai = new AllInfo();
		
		ai.setBookInfo(biOri);
		ai.setWebsiteInfo(wiOri);
		
		allInfoList.add(ai);
		
		return allInfoList;
	}
}
