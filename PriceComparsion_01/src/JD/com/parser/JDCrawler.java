package JD.com.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class JDCrawler {

	ArrayList<String> allUrlSet=new ArrayList<String>();      
	ArrayList<String> notCrawlurlSet=new ArrayList<String>();      
	HashMap<String, Integer> depth = new HashMap<String, Integer>();//所有网页的url深度  
	int crawDepth  = 3;      //爬虫深度  
	int threadCount = 5;    //线程数量  
	int count = 0;           //表示有多少个线程处于wait状态  
	int webNum=0;            //网页数量
	public static final Object signal = new Object();   //线程间通信变量  

	public synchronized void  addUrl(String url,int d){  
		notCrawlurlSet.add(url);  
		depth.put(url, d);  
	}  

	public synchronized  String getAUrl() {  
		if(notCrawlurlSet.isEmpty())  
			return null;  
		String tmpAUrl= notCrawlurlSet.get(0);
		notCrawlurlSet.remove(0);  
		return tmpAUrl;  
	}  


	private void begin() {  
		for(int i=0;i<threadCount;i++){  
			new Thread(new Runnable(){  
				@Override
				public void run() {  
					//可设置爬取网页数量，true为有网页时一直读取，可通过webNum进行设置

					while (webNum <= 50) {     
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
									System.out.println("当前有"+count+"个线程在等待");  
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

	public void crawler(String visitUrl) throws IOException{
		String mainurl = "http://item.jd.com/";
		//该 url 放入到已访问的 URL 中
		allUrlSet.add(visitUrl);
		//System.out.println(visitUrl);
		//提取出下载网页中的 URL
		if (visitUrl.startsWith(mainurl)){		

			BookParser contentParser = new BookParser();
			String bookInfo=contentParser.extracContent(visitUrl, "gbk");
			System.out.println(bookInfo+"#"+visitUrl);
		

		}

		int d = depth.get(visitUrl);  
		System.out.println("爬网页"+visitUrl+"成功，深度为"+d+" 是由线程"+Thread.currentThread().getName()+"来爬"); 

		if(d<crawDepth){  
			//解析网页内容，从中提取链接  
			Set<String> links=UrlParser.extracLinks(visitUrl);
			//新的未访问的 URL 入队
			for(String link:links)
			{
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
				//    				System.out.println(links.size());
			}
		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final JDCrawler JC = new JDCrawler();  
		JC.addUrl("http://book.jd.com/", 1);
		JC.begin();
	} 



}
