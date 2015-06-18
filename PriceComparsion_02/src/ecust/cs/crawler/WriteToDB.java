package ecust.cs.crawler;

import java.io.FileOutputStream;
import java.io.IOException;

import ecust.cs.dao.BookInfoDao;
import ecust.cs.dao.DetailInfoDao;
import ecust.cs.dao.WebsiteInfoDao;
import ecust.cs.model.BookInfo;
import ecust.cs.model.DetailInfo;
import ecust.cs.model.WebsiteInfo;
import ecust.cs.util.GenerateBookID;
import ecust.cs.util.ParserString;

public class WriteToDB {
	public int writeTxt(String bookinfo,String url) throws IOException  {
		  FileOutputStream o = null;  
		  String[] bookinfostr = bookinfo.split("#");
		  
		  //数据库变量
		  ParserString ps = new ParserString();
		  
		  DetailInfo detailInfo = new DetailInfo();
		  
		  if(bookinfostr.length<10)
			  return 0;
		  
		  detailInfo.setURL(url);
		  
		  
		  if(bookinfostr[0].indexOf("《")!=-1&&bookinfostr[0].indexOf("》")!=-1){
			  detailInfo.setBookName(bookinfostr[0].substring(bookinfostr[0].indexOf("《"), bookinfostr[0].indexOf("》")+1));
		  }
		  else{
			  detailInfo.setBookName(bookinfostr[0]);
		  }
		  
		  //现价
		  detailInfo.setPrice(bookinfostr[1]);
		  //原价
		  detailInfo.setOrignalPrice(bookinfostr[2]);
		  
		  for(int i=3;i<bookinfostr.length-9;i++){
			if(bookinfostr[i].equals("推荐")){
				detailInfo.setRecommend(bookinfostr[i-1]);
				}
			else if(bookinfostr[i].equals("条")){
				 if(!bookinfostr[i-2].equals("推荐")){
					 detailInfo.setRecommend(null);
				 }
				 detailInfo.setComment(bookinfostr[i-1]);
			}
			
			else if(bookinfostr[i].equals("作者")){
				if(bookinfostr[i+1].equals("出版社")){
					detailInfo.setAuthor(null);
				}
				else
					detailInfo.setAuthor(bookinfostr[i+1]);
			}
			
			else if(bookinfostr[i].equals("出版社")){
				if(bookinfostr[i+1].equals("出版时间")){
					detailInfo.setPress(null);
				}
				else
					detailInfo.setPress(bookinfostr[i+1]);
			}
			else if(bookinfostr[i].equals("出版时间")){
				if(bookinfostr[i+1].equals("ＩＳＢＮ")){
					detailInfo.setPublishingTime(null);
				}
				else
					detailInfo.setPublishingTime(bookinfostr[i+1]);
			}
			
			else if(bookinfostr[i].equals("ＩＳＢＮ")){
				if(bookinfostr[i+1].equals("所属分类")){
					detailInfo.setISBN(null);
				}
				else
					detailInfo.setISBN(bookinfostr[i+1]);
			}	
			else if(bookinfostr[i].equals("所属分类")){
				String classify=new String(); 
					  for(int j=i+1;j<bookinfostr.length-10;j++){
						  if(bookinfostr[j].equals("图书"))
							  classify=classify+"#";
						  classify=classify+bookinfostr[j]+"%";
					  }
					  
					  detailInfo.setCategory(classify);
				}
		  }
		  for(int i=bookinfostr.length-10;i<bookinfostr.length-1;i++){
			  String key = ps.getSubString(bookinfostr[i], 0);
			  String value = ps.getSubString(bookinfostr[i], 1);
			  
			 if("收藏人气".equals(key)){
      			
				 detailInfo.setCollections(value);
      		}else if("版 次".equals(key)){
      			
      			detailInfo.setEdition(value);
      		}else if("页 数".equals(key)){
      			
      			detailInfo.setPages(value);
      		}else if("字 数".equals(key)){
      			
      			detailInfo.setWords(value);
      		}else if("印刷时间".equals(key)){
      			
      			detailInfo.setPrintTime(value);
      		}else if("开 本".equals(key)){
      			
      			detailInfo.setOpens(value);
      		}else if("纸 张".equals(key)){
      			
      			detailInfo.setPageAttr(value);
      		}else if("印 次".equals(key)){
      			
      			detailInfo.setPrintCount(value);
      		}else if("包 装".equals(key)){
      			
      			detailInfo.setPackages(value);
      		}
			  
		  }
		  
		  if(detailInfo.getURL().contains("dangdang")){
			  detailInfo.setWebsite("当当网");
		  }else if(detailInfo.getURL().contains("jd")){
			  detailInfo.setWebsite("京东网");
		  }
		  
		  detailInfo.setPicURL(bookinfostr[bookinfostr.length-1]);
		  
		  
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
