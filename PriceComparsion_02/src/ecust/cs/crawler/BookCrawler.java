package ecust.cs.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class BookCrawler {

	ArrayList<String> allUrlSet=new ArrayList<String>();      
	ArrayList<String> notCrawlurlSet=new ArrayList<String>();      
	HashMap<String, Integer> depth = new HashMap<String, Integer>();//所有网页的url深度  
	int crawDepth  = 6;        //爬虫深度  
	int threadCount = 10;    //线程数量  
	int count = 0;                 //表示有多少个线程处于wait状态  
	int webNum=0, updateWebNum=0;           //网页数量
	public static final Object signal = new Object();   //线程间通信变量  
	
    public synchronized void  addUrl(String url,int d){  
    	notCrawlurlSet.add(url);  
    	//allUrlSet.add(url);  
        depth.put(url, d);  
        }  
    
    public synchronized  String getAUrl() {  
        if(notCrawlurlSet.isEmpty())  
            return null;  
        String tmpAUrl= notCrawlurlSet.get(0);
         notCrawlurlSet.remove(0);  
        return tmpAUrl;  
    }  
    
    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final BookCrawler bc = new BookCrawler();  
		//bc.addUrl("http://book.dangdang.com", 1);
		bc.addUrl("http://product.dangdang.com/23681626.html", 1);
		
		//多线程爬取
		bc.begin();
		//单线程爬取
		//bc.oneThreadCrawler();
	}
	
	//多线程爬取
	private void begin() {  
        for(int i=0;i<threadCount;i++){  
            new Thread(new Runnable(){  
                @Override
				public void run() {  
                	//可设置爬取网页数量，true为有网页时一直读取，可通过webNum进行设置
                	
                    while (true) {   
                   // 	  while (true) {   
                        String tmp = getAUrl();  
                        if(tmp!=null){  
                              //爬网页
                        	try {
								crawler(tmp);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        }else{  
                            synchronized(signal) {  //------------------（2）  
                                try {  
                                    count++;  
//System.out.println("当前有"+count+"个线程在等待");  
                                    signal.wait();  
                                } catch (InterruptedException e) {  
                                    // TODO Auto-generated catch block  
                                    e.printStackTrace();  
                                }  
                            }                             
                        }  
                    }  
                }  
            },"thread-"+i).start();  
        }  
    }  
	
	//单线程爬取
	public void oneThreadCrawler(){
		while (true) {   
            // 	  while (true) {   
                 String tmp = getAUrl();  
                 if(tmp!=null){  
                       //爬网页
                 	try {
							crawler(tmp);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                 }
		}
	}
	
	public void crawler(String visitUrl) throws IOException{
			String mainurl = "http://product.dangdang.com";
			//该 url 放入到已访问的 URL 中
			allUrlSet.add(visitUrl);
			//提取出下载网页中的 URL
			if (visitUrl.startsWith(mainurl)){		
				
				ContentParserTool contentParser = new ContentParserTool();
				String bookInfo=contentParser.extracContent(visitUrl, "gb2312");
					WriteToDB writeT=new WriteToDB();
					
					//调数据存储
					
					int flag=writeT.writeTxt(bookInfo,visitUrl);
if(flag == 1){
	updateWebNum ++;
						
//打印出书信息						
System.out.println("更新第" + updateWebNum + "书");
System.out.println("所爬取网址：" + visitUrl);
System.out.println("书信息：" + bookInfo);

}else if(flag == 2){
	
webNum=webNum+1;
						
//打印出书信息						
System.out.println("存储第" + webNum + "书");
System.out.println("所爬取网址：" + visitUrl);
System.out.println("书信息：" + bookInfo);
}
			}
			 int d = depth.get(visitUrl);  
//System.out.println("爬网页"+visitUrl+"成功，深度为"+d+" 是由线程"+Thread.currentThread().getName()+"来爬");  
             
             //控制网页深度
//             if(d<crawDepth){  
                 //解析网页内容，从中提取链接  
     			Set<String> links=DHtmlParserTool.extracLinks(visitUrl);
    			//新的未访问的 URL 入队
    			for(String link:links)
    			{
    				if(link.startsWith(mainurl)){
    					link=link.substring(0, link.indexOf(".html")+5);
    				}
    				if(!allUrlSet.contains(link)){
    					//System.out.println(link);
    					addUrl(link,depth.get(visitUrl)+1);
    					if(count>0){ //如果有等待的线程，则唤醒  
                            synchronized(signal) {  //---------------------（2）  
                                count--;  
                                signal.notify();  
                            }  
                        }  
    				}
    			}
//	}
	}
}
