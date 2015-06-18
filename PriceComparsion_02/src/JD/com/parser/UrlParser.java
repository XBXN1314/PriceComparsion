package JD.com.parser;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


public class UrlParser {

	public static Set<String> extracLinks(String url) {
		Set<String> links = new HashSet<String>();	
		String url1 = "http://book.jd.com/";
		String url2="http://list.jd.com/";
		String url3="http://item.jd.com/";
		String url4="http://channel.jd.com/";

		try {
			Parser parser = new Parser(url);
			parser.setEncoding("gbk");
			final String patternString = "<a\\s*?href=([^>]*?)>(.*?)</a>";
			NodeFilter frameFilter = new NodeFilter() {
				public boolean accept(Node node) {
					//					System.out.println(node);
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
				if(linkUrl.startsWith(url1)||linkUrl.startsWith(url2)||linkUrl.startsWith(url3)||linkUrl.startsWith(url4))
				{
					links.add(linkUrl);
				}      
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return links;
	}

	public static void main(String[]args) throws IOException
	{
		Set<String> links = UrlParser.extracLinks(
				"http://book.jd.com/");

		for(String link : links)
			System.out.println(link);
		System.out.println(links.size());

	}
}
