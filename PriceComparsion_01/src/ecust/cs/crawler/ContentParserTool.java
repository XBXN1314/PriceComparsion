package ecust.cs.crawler;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.beans.StringBean;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class ContentParserTool {

	public static String extracContent (String url,String encoding){
		String bookInfo=new String();
		String imgSrcPath=new String();
		 try {
			 HttpClient httpCLient = new DefaultHttpClient();  
			 HttpGet httpget = new HttpGet(url);
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
	            NodeFilter[]  filters = new NodeFilter[9];
	            filters[0]=new HasAttributeFilter("class", "key clearfix");          //商品详情
	            filters[1]=new HasAttributeFilter("class", "book_messbox");    //出版物信息 所属分类
	            filters[2]=new HasAttributeFilter("class", "renqi_span");         //收藏人气
	      //      filters[3]=new HasAttributeFilter("class", "breadcrumb");       //分类   书名limit_time
	            filters[3]=new TagNameFilter("title");      //分类   书名
	            filters[4]=new HasAttributeFilter("id", "salePriceTag");         //当当价
	            filters[5]=new HasAttributeFilter("id", "originalPriceTag");    //定价
	            filters[6]=new HasAttributeFilter("id", "comm_num_up");     //评论+推荐
	            filters[7]=new HasAttributeFilter("id", "promo_price");        //抢购价
	            filters[8]=new HasAttributeFilter("id","largePic");    //图片网址
	            OrFilter orFilter = new OrFilter(filters);
	            //过滤页面中的链接标签
	            NodeList list = parser.extractAllNodesThatMatch(orFilter);
	            for(int i=0; i<list.size();i++){
	                Node node = (Node)list.elementAt(i);
	  			  if(node instanceof ImageTag){
					  ImageTag imageTag = (ImageTag)node;
	                   imgSrcPath = imageTag.getAttribute("wsrc");
				  }
	                //System.out.print(node.toHtml());
	                bookInfo= bookInfo+extractText(node.toHtml());
	            }
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		 return bookInfo+imgSrcPath;
	}	
	 public static String extractText(String inputHtml) throws ParserException{
		 String bookInfoT=new String();
		 
		 final String patternString = "<a\\s*?href=([^>]*?)>(.*?)";
		 Pattern pat=Pattern.compile(patternString);
		 Matcher mat=pat.matcher(inputHtml);
		 inputHtml=mat.replaceAll("").replace("</a>", "");
		// System.out.print(inputHtml);
		 Parser parser = Parser.createParser(inputHtml, "gb2312");
		 NodeList list = parser.extractAllNodesThatMatch(new NodeFilter() {
			@Override
			public boolean accept(Node node) {
				// TODO Auto-generated method stub
				return ((node instanceof TextNode));
			}			 
		 }
				 );
		 
		  for(int i=0; i<list.size();i++){		 

			  TextNode   textnode   =   (TextNode) list.elementAt(i);
			  String   line   =   textnode.toPlainTextString().trim().replaceAll("&[A-Za-z0-9]+;", "").replaceAll("&nbsp", "");  
              if   (line.equals(""))  
                      continue; 
              bookInfoT=bookInfoT+line+"#";              
          }

		  return bookInfoT;

	 }
	 
		
		public static void main(String[] args) throws IOException {
			// TODO Auto-generated method stub
			String url = "http://product.dangdang.com/23659805.html";
			String book=extracContent(url, "gb2312");

			WriteToDB wtdb = new WriteToDB();
			wtdb.writeTxt(book, url);
			
			
		    System.out.print(book);
		}

}
