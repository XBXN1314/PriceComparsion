package ecust.cs.crawler;

import java.io.FileOutputStream;
import java.io.IOException;

import ecust.cs.dao.BookInfoDao;
import ecust.cs.dao.WebsiteInfoDao;
import ecust.cs.model.BookInfo;
import ecust.cs.model.WebsiteInfo;
import ecust.cs.util.GenerateBookID;
import ecust.cs.util.ParserString;

public class WriteToDB {
	public boolean writeTxt(String bookinfo,String url) throws IOException  {
		  FileOutputStream o = null;  
		  String[] bookinfostr = bookinfo.split("#");
		  
		  //数据库变量
		  BookInfo bookInfo = new BookInfo();
		  WebsiteInfo websiteInfo = new WebsiteInfo();
		  ParserString ps = new ParserString();
		  GenerateBookID gbd = new GenerateBookID();
		  
		  
		  if(bookinfostr.length<10)
			  return false;
		  
		  
		  websiteInfo.setURL(url);
		  
		  
		  if(bookinfostr[0].indexOf("《")!=-1&&bookinfostr[0].indexOf("》")!=-1){
			  bookInfo.setBookName(bookinfostr[0].substring(bookinfostr[0].indexOf("《"), bookinfostr[0].indexOf("》")+1));
		  }
		  else{
			  bookInfo.setBookName(bookinfostr[0]);
		  }
		  
		  //现价
		  websiteInfo.setPrice(bookinfostr[1]);
		  //原价
		  websiteInfo.setOrignalPrice(bookinfostr[2]);
		  
		  for(int i=3;i<bookinfostr.length-9;i++){
			if(bookinfostr[i].equals("推荐")){
				websiteInfo.setRecommend(bookinfostr[i-1]);
				}
			else if(bookinfostr[i].equals("条")){
				 if(!bookinfostr[i-2].equals("推荐")){
					 websiteInfo.setRecommend(null);
				 }
				 websiteInfo.setComment(bookinfostr[i-1]);
			}
			
			else if(bookinfostr[i].equals("作者")){
				if(bookinfostr[i+1].equals("出版社")){
					bookInfo.setAuthor(null);
				}
				else
					bookInfo.setAuthor(bookinfostr[i+1]);
			}
			
			else if(bookinfostr[i].equals("出版社")){
				if(bookinfostr[i+1].equals("出版时间")){
					bookInfo.setPress(null);
				}
				else
					bookInfo.setPress(bookinfostr[i+1]);
			}
			else if(bookinfostr[i].equals("出版时间")){
				if(bookinfostr[i+1].equals("ＩＳＢＮ")){
					bookInfo.setPublishingTime(null);
				}
				else
					bookInfo.setPublishingTime(bookinfostr[i+1]);
			}
			
			else if(bookinfostr[i].equals("ＩＳＢＮ")){
				if(bookinfostr[i+1].equals("所属分类")){
					bookInfo.setISBN(null);
				}
				else
					bookInfo.setISBN(bookinfostr[i+1]);
			}	
			else if(bookinfostr[i].equals("所属分类")){
				String classify=new String(); 
					  for(int j=i+1;j<bookinfostr.length-10;j++){
						  if(bookinfostr[j].equals("图书"))
							  classify=classify+"#";
						  classify=classify+bookinfostr[j]+"%";
					  }
					  
					  websiteInfo.setCategory(classify);
				}
		  }
		  for(int i=bookinfostr.length-10;i<bookinfostr.length-1;i++){
			  String key = ps.getSubString(bookinfostr[i], 0);
			  String value = ps.getSubString(bookinfostr[i], 1);
			  
			 if("收藏人气".equals(key)){
      			
      			websiteInfo.setCollections(value);
      		}else if("版 次".equals(key)){
      			
      			bookInfo.setEdition(value);
      		}else if("页 数".equals(key)){
      			
      			websiteInfo.setPages(value);
      		}else if("字 数".equals(key)){
      			
      			websiteInfo.setWords(value);
      		}else if("印刷时间".equals(key)){
      			
      			websiteInfo.setPrintTime(value);
      		}else if("开 本".equals(key)){
      			
      			websiteInfo.setOpens(value);
      		}else if("纸 张".equals(key)){
      			
      			websiteInfo.setPageAttr(value);
      		}else if("印 次".equals(key)){
      			
      			websiteInfo.setPrintCount(value);
      		}else if("包 装".equals(key)){
      			
      			websiteInfo.setPackages(value);
      		}
			  
		  }
		  
		  if(websiteInfo.getURL().contains("dangdang")){
				websiteInfo.setWebsite("当当网");
		  }else if(websiteInfo.getURL().contains("jd")){
			websiteInfo.setWebsite("京东网");
		  }
		  
		  websiteInfo.setPicURL(bookinfostr[bookinfostr.length-1]);
		  
		  
		  //此部分存储数据库的操作顺序一定要对
		  if(BookInfoDao.BookInfoIsExist(bookInfo) && WebsiteInfoDao.WebsiteInfoIsExist(websiteInfo)){
			  
			  //设置bookID
			  if(BookInfoDao.getBookInfoIDByBookName(bookInfo.getBookName()) != null){
				  bookInfo.setBookID(BookInfoDao.getBookInfoIDByBookName(bookInfo.getBookName()));
				  
				  BookInfoDao.updateBookInfo(bookInfo);
				  
				  websiteInfo.setBookInfo(bookInfo);
				  
				  WebsiteInfoDao.updateWebsiteInfo(websiteInfo);
			  }
		  }else{
			  
			  //设置bookID
			  bookInfo.setBookID(gbd.generateBookID());
 
			  BookInfoDao.addBookInfo(bookInfo);
			  
			  websiteInfo.setBookInfo(bookInfo);
			  
			  WebsiteInfoDao.addWebsiteInfo(websiteInfo);
			  
			 
		  }
		 
		  return true;  
	}
}
