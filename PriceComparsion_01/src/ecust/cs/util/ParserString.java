package ecust.cs.util;

public class ParserString {
	public static String getSubString(String str, int flag){
		char [] strBuf = str.toCharArray();
		char x = ':';
		char y = '：';
		int position = 0;
		
		for(;position < str.length(); position++){
			if(x == strBuf [position] || y == strBuf[position]){
				break;
			}
		}
		if(0 == flag){
			return str.substring(0, position);
		}else{
			if(position+1 < str.length()){
				return str.substring(position + 1, str.length());
			}else{
				return "";
			}
			
		}
	}
	
	public static void main(String[] args) {
		ParserString ps = new ParserString();
		if("开 本".equals(ps.getSubString("开 本:1", 0))){
			System.out.println(ps.getSubString("bookName：《工业4.0——即将来袭的第四次工业革命》", 1));
		}
		
	}
	
	
}
