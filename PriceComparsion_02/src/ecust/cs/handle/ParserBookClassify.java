package ecust.cs.handle;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ParserBookClassify {
	
	
	//#图书%教材%研究生/本科/专科教材%医学%#图书%医学%医学/药学教材%本科教材%#图书%医学%药学%中药%
	public static void ParserClassify(String classify) throws UnsupportedEncodingException{
		char classifyBuf[] = classify.toCharArray();
		
		List<String> classifyList = new ArrayList<String>();
		String eachclassify = null;
		int [] pos = new int[3];
		
		int length = 0, start=0, end=0, i=0;
		while(length < classifyBuf.length){
System.out.println(classify);
			if(classifyBuf[length] == '#'){
System.out.println(length + ":" + classifyBuf[length]);
				pos[i] = length;
				i ++;
				length ++; 
			}
		}
		
		for(int j=0; j < pos.length; j++){
System.out.println(j);
			eachclassify = (String) classify.subSequence(pos[j], pos[j+1]);
			classifyList.add(eachclassify);
		}
		
		for(String classfiy : classifyList){
			System.out.println(classfiy);
		}
		
	}
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String classify = "#图书%教材%研究生/本科/专科教材%医学%#图书%医学%医学/药学教材%本科教材%#图书%医学%药学%中药%";
		ParserBookClassify pbc = new ParserBookClassify();
		pbc.ParserClassify(classify);
		
	}

}
