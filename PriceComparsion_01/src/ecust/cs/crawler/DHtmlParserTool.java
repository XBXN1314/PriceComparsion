package ecust.cs.crawler;

import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.DefaultParserFeedback;


public class DHtmlParserTool {
	 public static Parser createParser(String inputHTML) {
	        Lexer mLexer = new Lexer(new Page(inputHTML));
	        return new Parser(mLexer,
	                          new DefaultParserFeedback(DefaultParserFeedback.QUIET));
	    }
	
	
	
	public static Set<String> extracLinks(String url) {
		Set<String> links = new HashSet<String>();
		String url4 = "http://product.dangdang.com/";
		String url1="http://book.dangdang.com/";
		String url2="http://category.dangdang.com";
		String url3="http://bang.dangdang.com/books/";
		
		try {
			//原写法
			Parser parser = new Parser(url);
			
			//现写法
			//Parser parser = createParser(url);
			
			
			parser.setEncoding("GB2312");
			final String patternString = "< a\\s*?href=([^>]*?)>(.*?)< /a>";
			NodeFilter frameFilter = new NodeFilter() {
				public boolean accept(Node node) {
					if (node.getText().startsWith(patternString)) {
						return true;
					} else {
						return false;
					}
				}
			};
			OrFilter linkFilter = new OrFilter(new NodeClassFilter(
					LinkTag.class), frameFilter);
			NodeList list = parser.extractAllNodesThatMatch(linkFilter);
			for (int i = 0; i < list.size(); i++) {
				 Node node = (Node)list.elementAt(i);
				   LinkTag link = (LinkTag) node;
				   String linkUrl = link.getLink();
				   if(linkUrl.startsWith(url1)||linkUrl.startsWith(url2)||linkUrl.startsWith(url3)||linkUrl.startsWith(url4)){
				   links.add(linkUrl);
				   }      
			}
		} catch (ParserException e) {
			//e.printStackTrace();
		}
		return links;
	}
	
	public static void main(String[]args)
	{
Set<String> links = DHtmlParserTool.extracLinks(
"http://book.dangdang.com/");
		for(String link : links)
			System.out.println(link);
	}
}
