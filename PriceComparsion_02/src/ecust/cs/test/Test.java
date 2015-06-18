package ecust.cs.test;

import java.io.IOException;

import ecust.cs.IKanalyse.WordSegment;


public class Test {
	public static void main(String[] args) {
		String category = "http://item.jd.com/1358116449.html;http://item.jd.com/1125129587.html;http://item.jd.com/1480063662.html;";
		
		String[] buf = category.split(";");
		
		System.out.println(buf[3]);
	}
}
