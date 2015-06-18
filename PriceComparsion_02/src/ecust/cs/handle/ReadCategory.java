package ecust.cs.handle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadCategory {
	//中国文学（中国古诗词，中国现当代诗，中国古代随笔，中国当代随；中国文学）
	public static Map<String, List<String>> getCategoryMapping(){
		Map<String, List<String>> categoryMap = new HashMap<String, List<String>>();
		
		
		try {
            // read file content from file
            StringBuffer sb= new StringBuffer("");

            FileInputStream reader = new FileInputStream("D://wpc//category.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(reader, "UTF-8"));
            String str = null;

            while((str = br.readLine()) != null) {
            	List<String> jdddCategory = new ArrayList<String>();
            	int a = str.indexOf('（');
            	int b = str.indexOf('；');
            	int c = str.indexOf('）');
            	
            	if(a != -1 && b != -1 && c != -1){
            		if(a+1 == b){
        			jdddCategory.add(str.substring(0, a));
        			jdddCategory.add(str.substring(b+1, c));
        			
        			categoryMap.put(str.substring(0, a), jdddCategory);
        			
//            		System.out.println("本地分类：" + str.substring(0, a) 
//                         		  + "\t\t当当分类：" + str.substring(0, a)
//                         		  + "\t\t京东分类：" + str.substring(b+1 , c));
            		}
            		if(b+1 == c){
        			jdddCategory.add(str.substring(a+1, b));
        			jdddCategory.add(str.substring(0, a));
        			
        			categoryMap.put(str.substring(0, a), jdddCategory);
            			
//        			System.out.println("本地分类：" + str.substring(0, a) 
//                      		  + "\t\t当当分类：" + str.substring(a+1, b)
//                      		  + "\t\t京东分类：" + str.substring(0, a));
            		}else{
        			jdddCategory.add(str.substring(a+1, b));
        			jdddCategory.add(str.substring(b+1, c));
        			
        			categoryMap.put(str.substring(0, a), jdddCategory);
        			
//        			System.out.println("本地分类：" + str.substring(0, a) 
//                     		  + "\t\t当当分类：" + str.substring(a+1, b)
//                     		  + "\t\t京东分类：" + str.substring(b+1 , c));	
            		}
            		
            	}
                 

            }

            br.close();

            reader.close();

           

		}catch(FileNotFoundException e) {
                e.printStackTrace();
        }catch(IOException e) {
                e.printStackTrace();
        }
		
		return categoryMap;
	}
	
	public static void main(String[] args) {
		Map<String, List<String>> map = ReadCategory.getCategoryMapping();
		
		for(Map.Entry<String, List<String>> entry : map.entrySet()){
			System.out.println("本地分类：" + entry.getKey() 
					+ "\t当当分类：" + entry.getValue().get(0)
					+ "\t京东分类：" + entry.getValue().get(1));
			
			System.out.println();
		}
	}
	
}
