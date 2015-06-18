package JD.com.util;

import java.util.UUID;

public class GenerateBookID {
	public static String generateBookID(){
     UUID uuid=UUID.randomUUID();
     return uuid.toString();
	}
	
	
	
	
	public static void main(String[] args){
		GenerateBookID gb=new GenerateBookID();
		System.out.println(gb.generateBookID());
		
	}
}
