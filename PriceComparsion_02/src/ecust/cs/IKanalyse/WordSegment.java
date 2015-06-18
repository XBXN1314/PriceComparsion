package ecust.cs.IKanalyse;

import java.io.IOException;  
import java.io.StringReader;  
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;  
import org.apache.lucene.analysis.TokenStream;  
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;  
import org.wltea.analyzer.lucene.IKAnalyzer;  
  
public class WordSegment {  
	public static List<String> segment(String sentence) throws IOException{
		List<String> wordList = new ArrayList<String>();
        //创建分词对象  
        Analyzer anal=new IKAnalyzer(true);       
        StringReader reader=new StringReader(sentence);  
        //分词  
        TokenStream ts=anal.tokenStream("", reader);  
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);  
        //遍历分词数据  
        while(ts.incrementToken()){  
           wordList.add(term.toString());
        }  
        reader.close();  	
		return wordList;
	}
	
	
	
    public static void main(String[] args) throws IOException {  
        String text="书名平凡的世界";  
        System.out.println(segment(text).toString());  
    }  
  
}  