package JD.com.util;

import java.io.FileOutputStream;
import java.io.IOException;

import ecust.cs.dao.BookInfoDao;
import ecust.cs.dao.DetailInfoDao;
import ecust.cs.dao.WebsiteInfoDao;
import ecust.cs.model.BookInfo;
import ecust.cs.model.DetailInfo;
import ecust.cs.model.WebsiteInfo;
import ecust.cs.util.GenerateBookID;

public class Wtdb {
	public static int writeToDB(String bookinfo)  {
		
		DetailInfo detailInfo = new DetailInfo();
		
		String[] bookinfostr = bookinfo.split("#");
		//数据库变量
		BookInfo bookInfo = new BookInfo();
		WebsiteInfo websiteInfo = new WebsiteInfo();
		ParserString ps = new ParserString();

		if(bookinfostr[0].indexOf("《")!=-1&&bookinfostr[0].indexOf("》")!=-1){
			detailInfo.setBookName(bookinfostr[0].substring(bookinfostr[0].indexOf("《"), bookinfostr[0].indexOf("》")+1));
		}
		else{
			detailInfo.setBookName(bookinfostr[0]);
		}

		if(bookinfostr[1].equals("图书")){
			String classify=new String(); 
			for(int j=1;j<5;j++){
				classify=classify+"#";
				classify=classify+bookinfostr[j]+"%";}
			detailInfo.setCategory(classify);
			
			for(int i=5;i<=bookinfostr.length-1;i++){
				if(bookinfostr[i].equals("出版社：")){
					 String author = new String();
				   for(int j=5;j<i;j++){
					    author=author+bookinfostr[j]+"#";  
					   
				   }	
				String authors = author.replaceAll("#", "");
				detailInfo.setAuthor(authors);
				}
			}
			for(int i=5;i<=bookinfostr.length-1;i++){
				if(bookinfostr[i].equals("出版社：")){
					detailInfo.setPress(bookinfostr[i+1]);
				}
				 
			}

			for(int i=6;i<bookinfostr.length-1;i++){
				String key = ps.getSubString(bookinfostr[i], 0);
				String value = ps.getSubString(bookinfostr[i], 1);

				if("ISBN".equals(key)){
					detailInfo.setISBN(value);
				}else if("用纸".equals(key)){
					 detailInfo.setPageAttr(value);
				}
				 else if("页数".equals(key)){
					 detailInfo.setPages(value);
					}
				 else if("字数".equals(key)){
					detailInfo.setWords(value);
					}
				else if("版次".equals(key)){

					detailInfo.setEdition(value);
				}else if("印刷时间".equals(key)){

					detailInfo.setPrintTime(value);
				}else if("出版时间".equals(key)){

					detailInfo.setPublishingTime(value);
				}
				else if("开本".equals(key)){

					detailInfo.setOpens(value);
				}
				 
				else if("印次".equals(key)){
					detailInfo.setPrintCount(value);
				}
				else if("包装".equals(key)){

					detailInfo.setPackages(value);
				}
				else if("累计评价".equals(key)){

					detailInfo.setComment(value);
				}else if("原价".equals(key)){

					detailInfo.setOrignalPrice(value);
				}else if("京东价".equals(key)){

					detailInfo.setPrice(value);
				}

			}
		}
		
		//图片URL
		detailInfo.setPicURL(bookinfostr[bookinfostr.length-2]);
		
		//书URL
		detailInfo.setURL(bookinfostr[bookinfostr.length-1]);
		
		 
		 if(detailInfo.getURL().contains("dangdang")){
			 detailInfo.setWebsite("当当网");
			}else if(detailInfo.getURL().contains("jd")){
				detailInfo.setWebsite("京东网");
			}
		
		if("".equals(detailInfo.getOrignalPrice())  || "".equals(detailInfo.getPrice())
				|| null == detailInfo.getOrignalPrice()){
			return 0;
		}
		
		//此部分存储数据库的操作顺序一定要对
		DetailInfo di = DetailInfoDao.getDetailInfo(detailInfo);
		  
		  if(di != null){
			  DetailInfoDao.updateDetailInfo(detailInfo);
			  return 1;
		  }else{
			  DetailInfoDao.addDetailInfo(detailInfo);
			  return 2;
		  }
		
		
		
	}

}
