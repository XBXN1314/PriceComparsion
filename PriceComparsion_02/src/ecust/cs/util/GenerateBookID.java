package ecust.cs.util;

import java.util.UUID;

public class GenerateBookID {
	public static String generateBookID(){
		UUID uuid = UUID.randomUUID();   
		return uuid.toString();
	}
	
}
