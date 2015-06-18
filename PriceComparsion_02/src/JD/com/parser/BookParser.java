package JD.com.parser;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import JD.com.Bean.BookInfoBuf;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class BookParser {

	public static String extracContent (String url,String encoding){
	
		String bookInfo=new String();
		String imgSrcPath=new String();
		try {
			HttpClient httpCLient = new DefaultHttpClient();  
			HttpGet httpget = new HttpGet  (url);
			HttpResponse response = (httpCLient).execute(httpget);  
			int status=response.getStatusLine().getStatusCode(); 
			if(status<200||status>300){
				return null; 	
			}
			Parser parser = new Parser();
			parser.setURL(url);
			if(null==encoding){
				parser.setEncoding(parser.getEncoding());
			}else{
				parser.setEncoding(encoding);
			}      
			NodeFilter[]  filters = new NodeFilter[5];
			filters[0]=new HasAttributeFilter("class", "p-parameter-list");  //商品详情
			filters[1]=new TagNameFilter("title");      //分类   书名
			filters[2]=new HasAttributeFilter("data-img","1");    //图片网址
			filters[3]=new HasAttributeFilter("class", "breadcrumb");
			filters[4]=new HasAttributeFilter("class","p-author");
		
			OrFilter orFilter = new OrFilter(filters);
			//过滤页面中的链接标签
			NodeList list = parser.extractAllNodesThatMatch(orFilter);
			
			for(int i=0; i<list.size();i++){
				Node node = (Node)list.elementAt(i);
				if(node instanceof ImageTag){
					ImageTag imageTag = (ImageTag)node;
					imgSrcPath = imageTag.getAttribute("src").replace("n4", "n1");
				}
				bookInfo= bookInfo+extractText(node.toHtml());
			}

			//价格的爬取
			String Aurl=url.substring(url.lastIndexOf("/")+1, url.lastIndexOf("."));
			String Turl="http://p.3.cn/prices/mgets?skuids=J_"+Aurl;
//			System.out.println(Turl);
			AJXParser ap=new AJXParser();
			Document dc=Jsoup.parse(ap.readPageSourceAjax(Turl,"gbk"));
			String pageSource=dc.text();
			List<BookInfoBuf> Blist= new ArrayList<BookInfoBuf>();
			try{

				Type listType = new TypeToken<List<BookInfoBuf>>(){}.getType();
				Gson gson = new Gson();
				Blist = gson.fromJson(pageSource, listType);
				bookInfo=bookInfo+"京东价："+Blist.get(0).getP()+"#";
				bookInfo =bookInfo+"原价："+Blist.get(0).getM()+"#";
            
			}catch(Exception e){
				Blist=null;
			}
			
			
         //累计评价的爬取
		String Purl=url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."));
		String Jurl="http://club.jd.com/clubservice/productcomment-"+Purl+"-5-0.html";
	    Document docpj=Jsoup.parse(ap.readPageSourceAjax(Jurl, "gbk"));
	    String pagesource=docpj.text();
	    
	    
		String regex="(?<=\\().*(?=\\))";
    	Pattern p=Pattern.compile(regex);
    	Matcher m=p.matcher(pagesource);
    	while(m.find()){
    	String pageso =m.group();
//    		System.out.println(pageso);
    		
	    try{
	    	JSONObject object = JSONObject.fromObject(pageso);
	    	 String cos = object.getString("CommentSummary");
	    	 JSONObject object1 = JSONObject.fromObject(cos);
	    	 String cos1= object1.getString("CommentCount");
	    	 
	    	 
	    	bookInfo=bookInfo+"累计评价:"+cos1+"#";
	    	
	    }catch(Exception e){
	    	Blist=null;
	    	
	    }	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookInfo+imgSrcPath;
	}


	public static String extractText(String inputHtml) throws ParserException{
		
		String bookInfoT=new String();


		Parser parser = Parser.createParser(inputHtml, "gbk");
		NodeList list = parser.extractAllNodesThatMatch(new NodeFilter() {
			@Override
			public boolean accept(Node node) {
				// TODO Auto-generated method stub
				return ((node instanceof TextNode));
			}			
		}
				);

		for(int i=0; i<list.size();i++){		 
			TextNode   textnode = (TextNode) list.elementAt(i);
			String   line   =   textnode.toPlainTextString().trim().replaceAll("&[A-Za-z0-9]+;", "").replaceAll("<a[^>]+>([^<]+)</a>", "$1");  
			if   (line.equals(""))  
				continue; 
			bookInfoT=bookInfoT+line+"#";  
		
		
		}
		
		return bookInfoT;

	}
	
    
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String url = "http://item.jd.com/11647793.html";
		String book=extracContent(url, "gbk");

		//			WriteToDB wtdb = new WriteToDB();
		//			wtdb.writeTxt(book, url);


		System.out.println(book);
		System.out.println(url);
	}
}
