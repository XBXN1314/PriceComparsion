package ecust.cs.util;

import java.util.List;

import ecust.cs.dao.BookInfoDao;
import ecust.cs.dao.WebsiteInfoDao;
import ecust.cs.model.WebsiteInfo;



public class TestClass {
	
	public static void main(String [] args){
		WebsiteInfoDao wsd = new WebsiteInfoDao();
		//根据分类获取网页信息
		//List<WebsiteInfo> websiteInfoList = wsd.getWebsiteByClassify("本科教材");
		
		//根据书名获取网页信息
		List<WebsiteInfo> websiteInfoList = wsd.getwebsiteInfobybook("考研 英语");
		
		//根据书名获取书信息
		
		for(WebsiteInfo websiteInfo : websiteInfoList){
			System.out.println();
			System.out.println("书名：" + websiteInfo.getBookInfo().getBookName()
					+ "\turl：" + websiteInfo.getURL());
//System.out.println();
//System.out.println(websiteInfo.getCategory());
		}
		System.out.println("查询集合大小为：" + websiteInfoList.size());
	}
}
