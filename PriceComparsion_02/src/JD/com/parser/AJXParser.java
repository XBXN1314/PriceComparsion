package JD.com.parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AJXParser {
public String readPageSourceAjax(String urlString, String encode) {
		
		URL url;
		int responsecode;
		HttpURLConnection urlConnection;
		BufferedReader reader;
		String line;
		String str = "";
		try{
			//生成一个URL对象，要获取源代码的网页地址为：http://www.sina.com.cn
			url = new URL(urlString);
			//打开URL
			urlConnection = (HttpURLConnection)url.openConnection();

			urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.01; Windows NT 5.0)");
			urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			urlConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			urlConnection.setConnectTimeout(15000);
			urlConnection.setReadTimeout(50000);
			urlConnection.setUseCaches(false);
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setAllowUserInteraction(false);


			//获取服务器响应代码
			responsecode=urlConnection.getResponseCode();
			if(responsecode==200){
				//得到输入流，即获得了网页的内容 
				reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), encode));
				while((line=reader.readLine())!=null){
					str = str + line;
				}
			}
			else{
				System.out.println("Ajax获取不到网页的源码，服务器响应代码为："+responsecode);
				return str = "再来一次";
			}
		}
		catch(Exception e){
			System.out.println("Ajax获取不到网页的源码,出现异常："+e );
			return str = "再来一次";
		}
		
		urlConnection.disconnect();
		
		return str;
		
	}
public static void main(String[] args){
	AJXParser ap =new AJXParser();
	System.out.println(ap.readPageSourceAjax("http://p.3.cn/prices/mgets?skuids=J_11650440","gbk"));
}




}
