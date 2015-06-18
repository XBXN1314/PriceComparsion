package ecust.cs.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyCrawler {
	public Document getDocument (String url){
		try {
			return Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO: handle exception
		}
		return null;
	}
	 
	public String getContent(String url){
		String str = null;
		
		Document doc = this.getDocument(url);
		
		//Elements e1 = doc.select("[class=home_nav_l]");
		
		
		
		str = doc.toString();
		return str;
	}
	
	public static void main(String[] args) {
		MyCrawler mc = new MyCrawler();
		System.out.println(mc.getContent("http://www.dangdang.com/?_ddclickunion=P-293858-3654|ad_type=10|sys_id=1#dd_refer=http%3A%2F%2Fs.76mi.com%2Findex.asp%3Fs%3Dsh%26qgid%3D1"));
	}
	
}
