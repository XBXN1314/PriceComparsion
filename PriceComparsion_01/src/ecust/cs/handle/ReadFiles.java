package ecust.cs.handle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class ReadFiles {
	
	 public static boolean readfile(String filepath) throws FileNotFoundException, IOException {
         try {
        	 	 
        	 	 FileToDB ftdb = new FileToDB();
                 File file = new File(filepath);
                 
                 if (!file.isDirectory()) {
                         System.out.println("文件");
                         System.out.println("path=" + file.getPath());
                         System.out.println("name=" + file.getName());

                 } else if (file.isDirectory()) {
                         System.out.println("文件夹");
                         String[] filelist = file.list();
                         for (int i = 0; i < filelist.length; i++) {
                        	 	System.out.println("文件" + i);
                                 File readfile = new File(filepath + "\\" + filelist[i]);
                                 if (!readfile.isDirectory()) {
                                	 ftdb.resolveFile(readfile.getPath());
                                	 
                                     System.out.println("name=" + readfile.getName());

                                 } else if (readfile.isDirectory()) {
                                	 readfile(filepath + "\\" + filelist[i]);
                                 }
                         }

                 }

         } catch (FileNotFoundException e) {
                 System.out.println("readfile()   Exception:" + e.getMessage());
         } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return true;
	 }
	 
	 
	 public static void main(String[] args) {
		String filesPath = null;
		filesPath = "D:\\Genuitec\\bookInfoFile\\book";
		ReadFiles rf = new ReadFiles();
		try {
			rf.readfile(filesPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
