package ecust.cs.handle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import ecust.cs.dao.BookInfoDao;
import ecust.cs.dao.WebsiteInfoDao;
import ecust.cs.model.WebsiteInfo;
import ecust.cs.model.BookInfo;
import ecust.cs.util.GenerateBookID;
import ecust.cs.util.ParserString;

public class FileToDB {
	public static void resolveFile(String fileName) throws UnsupportedEncodingException, FileNotFoundException, ParseException{
		File file = new File(fileName);
		
		InputStreamReader read = new InputStreamReader (new FileInputStream(file),"GBK");
		
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(read);
            String tempString = null;
            int bookCount = 1;
          
            ParserString parserStr = new ParserString();
            
            BookInfo bookInfo = new BookInfo();
        	WebsiteInfo websiteInfo = new WebsiteInfo();
        	
        	String key = null;
    		String value = null;
    		
    		//生成书的唯一标示ID
    		GenerateBookID gbd = new GenerateBookID();
    		
    		//将txt中文件读取至model中去实现数据库存储
            while ((tempString = reader.readLine()) != null) {
            	//判断一本书的信息有没有结束
            	if(tempString.length() == 0){
            		bookInfo.setBookID(gbd.generateBookID());
            		
            		if(websiteInfo.getURL().contains("dangdang")){
            			websiteInfo.setWebsite("当当网");
            		}else if(websiteInfo.getURL().contains("jd")){
            			websiteInfo.setWebsite("京东网");
            		}
            		
            		websiteInfo.setBookInfo(bookInfo);
            		
            		
            		
            		BookInfoDao.addBookInfo(bookInfo);
            		WebsiteInfoDao.addWebsiteInfo(websiteInfo);
            		
            		bookCount ++;
            		
            	}else{
            		//将所读取的行数据解析为key和value
                	key = parserStr.getSubString(tempString, 0);
                	value = parserStr.getSubString(tempString, 1);
            		
            		if("url".equals(key)){
            			
            			if(value.isEmpty() || value == ""){
            				websiteInfo.setURL("URL is miss...");
            			}else{
            				websiteInfo.setURL(value);
            			}
            			
            			System.out.println(value);
            			
            		}else if("bookName".equals(key)){
            			
            			if(value.isEmpty() || value == ""){
            				bookInfo.setBookName("此书名信息丢失");
            			}else{
            				bookInfo.setBookName(value);
            			}
            			
            		}else if("salePrice".equals(key)){
            			
            			websiteInfo.setPrice(value);
            		}else if("originalPrice".equals(key)){
            			
            			websiteInfo.setOrignalPrice(value);
            		}else if("recommend".equals(key)){
            			
            			websiteInfo.setRecommend(value);
            		}else if("comment".equals(key)){
            			
            			websiteInfo.setComment(value);
            		}else if("author".equals(key)){
            			
            			bookInfo.setAuthor(value);
            		}else if("press".equals(key)){
            			
            			bookInfo.setPress(value);
            		}else if("time".equals(key)){
            			
            			bookInfo.setPublishingTime(value);
            		}else if("ISBN".equals(key)){
            			
            			bookInfo.setISBN(value);
            		}else if("classify".equals(key)){
            			
            			websiteInfo.setCategory(value);
            		}else if("收藏人气".equals(key)){
            			
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
            }
            //BookInfoDao.addBookInfo(bookInfo);
    		//WebsiteInfoDao.addWebsiteInfo(websiteInfo);
    		
        	System.out.println("书总数为：" + bookCount);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
	}
	
	
	
	public static void main(String[] args) {
		try {
			resolveFile("D:\\Genuitec\\bookInfoFile\\b1.txt");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
