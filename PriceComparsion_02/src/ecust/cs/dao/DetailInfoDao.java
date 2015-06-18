package ecust.cs.dao;

import java.io.*;
import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import ecust.cs.IKanalyse.WordSegment;
import ecust.cs.handle.ReadCategory;
import ecust.cs.model.DetailInfo;
import ecust.cs.util.HibernateUtil;


public class DetailInfoDao {
	//存入数据库数据
		public static void addDetailInfo(DetailInfo detailInfo){
			Session session = HibernateUtil.getSession();
			Transaction ts = session.beginTransaction();
			
			session.save(detailInfo);
			
			ts.commit();
			session.close();
		}
		
		//升级数据库数据
		public static void updateDetailInfo(DetailInfo detailInfo){
			Session session = HibernateUtil.getSession();
			Transaction ts = session.beginTransaction();
			
			session.update(detailInfo);
			session.save(detailInfo);
			
			ts.commit();
			session.close();
		}
		
		public static void updateOrSaveDetailInfo(DetailInfo detailInfo){
			Session session = HibernateUtil.getSession();
			Transaction ts = session.beginTransaction();
			
			session.saveOrUpdate(detailInfo);
			
			ts.commit();
			session.close();
		}
		
		
		public static List<DetailInfo> getNumDetailInfo(int num){
			Session session = HibernateUtil.getSession();
			Transaction ts = session.beginTransaction();
			
			List<DetailInfo> detailInfoList = new ArrayList<DetailInfo>();
			
			for(int i=0; i<num; i++){
				detailInfoList.addAll(DetailInfoDao.getOneDetailInfo());
			}
			
			ts.commit();
			session.close();
			
			return detailInfoList;
		}
		
		
		public static List<DetailInfo> getOneDetailInfo(){
			Session session = HibernateUtil.getSession();
			Transaction ts = session.beginTransaction();
			
			List<DetailInfo> detailInfoList = new ArrayList<DetailInfo>();
			
			Query queryBook = session.createQuery("from DetailInfo");
			
			int count = (int) ((Math.random())*30000);
			
			queryBook.setFirstResult(count);
			queryBook.setMaxResults(1);
			
			detailInfoList = queryBook.list();
			
			ts.commit();
			session.close();
			
			return detailInfoList;
		}
		
		//按书的属性获取书信息
		public static List<DetailInfo> findByProperty(String propertyName, Object value) {
			Session session = HibernateUtil.getSession();
			try {
				String queryString = "from DetailInfo as model where model."
						+ propertyName + " like ?";
				Query queryObject = session.createQuery(queryString);
				queryObject.setParameter(0, "%" + value + "%");
				Transaction ts = session.beginTransaction();
				ts.commit();
				return queryObject.list();
			} catch (RuntimeException re) {
				throw re;
			}
		}
		
		public static DetailInfo getDetailInfo(DetailInfo di){
			Session session = HibernateUtil.getSession();
			Transaction ts = session.beginTransaction();
			
			DetailInfo detail = (DetailInfo) session.get(DetailInfo.class, di.getURL());
			
			ts.commit();
			session.close();
			
			return detail;
		}
		
		
		public static void deleteDetailInfo(DetailInfo di){
			Session session = HibernateUtil.getSession();
			Transaction ts = session.beginTransaction();
			
			session.delete(di);
			
			ts.commit();
			session.close();
		}
		
		
		public static List<DetailInfo> getAll(){
			Session session = HibernateUtil.getSession();
			Transaction ts = session.beginTransaction();
			
			List<DetailInfo> detailInfoList = new ArrayList<DetailInfo>();
			
			Query queryBook = session.createQuery("from DetailInfo");
			
			detailInfoList = queryBook.list();
			
			ts.commit();
			session.close();
			
			return detailInfoList;
		}
		
		
		public static List<DetailInfo> getDetailInfoBySearch(String search){
			List<DetailInfo> detailInfoList = DetailInfoDao.findByProperty("bookName", search);
			
			return detailInfoList;
		}
		
		
		//此方法为未输入空格求交集，仅作者
		public static List<DetailInfo> findCrossResult(List<String> keyWordsList){
System.out.println(keyWordsList.toString());
			List<DetailInfo> firstList = DetailInfoDao.findByProperty("bookName", keyWordsList.get(0));
			List<DetailInfo> finalList = new ArrayList<DetailInfo>();
			
			for(int i=1 ; i<keyWordsList.size(); i++){
				for(DetailInfo di : firstList){
	if(di.getBookName().toLowerCase().contains(keyWordsList.get(i).toLowerCase())){
						finalList.add(di);
					}
				}
			}
			
			return finalList;
		}
		
		//此种方法为输入空格去查询书名和作者
		public static List<DetailInfo> findCrossResultWithSpace(String search){
			int firstpos = search.indexOf(' ');
			String name = search.substring(0, firstpos);
			String author = search.substring(firstpos + 1, search.length());
System.out.println(name);
System.out.println(author);
			List<DetailInfo> nameBufList = DetailInfoDao.findByProperty("bookName", name);
			List<DetailInfo> finalList = new ArrayList<DetailInfo>();
			
			for(DetailInfo di : nameBufList){
				if(di.getAuthor() != null){
					if(di.getAuthor().contains(author)){
						finalList.add(di);
					}
				}else{
					finalList.add(di);
				}
				
				
			}
			
			return finalList;
		}
		
		
		public static List<DetailInfo> processKey(String search){
			if(search.indexOf(' ') != -1){//用户输入了空格
				return findCrossResultWithSpace(search);
				
			}else if(search.indexOf(' ') == -1){//用户没有输入空格
				List<String> keyWordsList = new ArrayList<String>();
				
				try {
					keyWordsList = WordSegment.segment(search);
					keyWordsList.add(search);
System.out.println(keyWordsList.toString());				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return findCrossResult(keyWordsList);
				
			}
			
			return null;
		}
		
		public static List<DetailInfo> getDetailInfoBySearchAfterSegment(String search){
//			List<DetailInfo> bufSectionList = new ArrayList<DetailInfo>();
//			List<String> keyWordsList = new ArrayList<String>();
//			keyWordsList.add(search);
//			try {
//				keyWordsList = WordSegment.segment(search);
//System.out.println(keyWordsList.toString());				
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//			
//			//此方法为求并集
//			for(String keywords : keyWordsList){
//				bufSectionList = DetailInfoDao.findByProperty("bookName", keywords);
//				
//				bufSectionList.addAll(DetailInfoDao.findByProperty("author", keywords));
//				
//				bufSectionList.addAll(DetailInfoDao.findByProperty("category", keywords));
//			}
//			
//			//此部分为去除重复数据
//			List<DetailInfo> bufSectionSet = new ArrayList<DetailInfo>(); 
//			Set<String> bookURL = new HashSet();
//			for(DetailInfo detail : bufSectionList){
//				if(!bookURL.contains(detail.getURL())){
//					bookURL.add(detail.getURL());
//					bufSectionSet.add(detail);
//				}
//			}
			
			List<DetailInfo> bufSectionSet = DetailInfoDao.processKey(search);
			
			//此部分为聚合相同书籍
			List<DetailInfo> finalList = new ArrayList<DetailInfo>();
			Map<String, List<DetailInfo>> tempMap = clustering(bufSectionSet);
			
			for(String tempKey : tempMap.keySet()){
				finalList.add(tempMap.get(tempKey).get(0));
			}
			
			//将此map存入文件中
			File file = new File("D:/wpc/buf.txt");
	        FileWriter fw = null;
	        BufferedWriter writer = null;
	        int flag = 1;
	        
	        try {
	            fw = new FileWriter(file);
	            writer = new BufferedWriter(fw);
	            
	            for(String tempKey : tempMap.keySet()){
	            	if(flag%2 == 1){//奇数行
	            		writer.write(tempKey);
		                writer.newLine();
		                flag ++;
	            	}
	            	if(flag%2 == 0){//偶数行
	            		List<DetailInfo> bufList = tempMap.get(tempKey);
	            		for(DetailInfo di : bufList){
	            			writer.write(di.getURL() + ";");
	            		}
	            		writer.newLine();
	            		flag ++;
	            	}
				}
	            writer.flush();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }catch (IOException e) {
	            e.printStackTrace();
	        }finally{
	            try {
	                writer.close();
	                fw.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
			
			return finalList;
		}
		
		// 聚合，key是书名，value是该种书下的所有书
		public static Map<String, List<DetailInfo>> clustering(List<DetailInfo> books){
			
			Map<String, List<DetailInfo>> maps = new HashMap();
			double bookNameSimThreshold = 0.85;
			double bookAuthorSimThreshold = 0.8;
			int[] flag = new int[books.size()];
			for(int i=0 ; i<books.size(); i++){
				
				if(flag[i] == 0){
					flag[i] = 1;
					String bookName = books.get(i).getBookName();
					List<DetailInfo> abooks = new ArrayList();
					abooks.add( books.get(i));
					for (int j = i+1; j < books.size(); j++){
						if (flag[j] == 0 && (simOfStrings( books.get(i).getBookName(), books.get(j).getBookName()) > bookNameSimThreshold && simOfStrings( books.get(i).getAuthor(),  books.get(j).getAuthor()) > bookAuthorSimThreshold || books.get(i).getISBN().equals(books.get(j).getISBN()))){
System.out.println( books.get(i).getBookName() +  "和" + books.get(j).getBookName() + "的书名相似度:" + simOfStrings( books.get(i).getBookName(), books.get(j).getBookName())
					+ "\n作者相似度" + simOfStrings( books.get(i).getAuthor(),  books.get(j).getAuthor()));
							
							abooks.add( books.get(j));
							if( books.get(j).getBookName().length() < bookName.length()){
								bookName =  books.get(j).getBookName();
							}
							flag[j] = 1;
						}
					}
					Collections.sort(abooks, new ComparatorOfDetailInfo());
					//maps.put(bookName, abooks);
					maps.put(abooks.get(0).getBookName(), abooks);
				}
			}
			
			return maps;
		}
		
		// 书之间的相似度计算
		public static double simOfStrings(String a, String b){
			if("".equals(a) || a == null){
				return 0;
			}if("".equals(b) || b == null){
				return 0;
			}
			
			int union = a.length();
			int intersection = 0;
			int lengthOfB = b.length();
			for(int i = 0; i < lengthOfB; i++){
				if (a.indexOf(b.charAt(i)) != -1 ){
					intersection ++;
				}else
					union ++;
			}
			
			if(a.length() < b.length()){
				return 1.0*intersection/a.length();
			}else{
				return 1.0*intersection/b.length();
			}
		}
		
		//XIUGAI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		public static List<DetailInfo> getBookDetailInfoByURLBasedOnSearch(String picUrl){
			List<DetailInfo> finalList = new ArrayList<DetailInfo>();
			List<DetailInfo> detailInfoList = new ArrayList<DetailInfo>(); 
			detailInfoList = DetailInfoDao.findByProperty("picURL", picUrl);
			
			DetailInfo keyDI = detailInfoList.get(0);
			
			List<DetailInfo> bufList = new ArrayList<DetailInfo>();
			String urlList = null;
			
			//从文件中读取获得已存入的书
			try {
	            // read file content from file
	            FileReader reader = new FileReader("D:/wpc/buf.txt");
	            BufferedReader br = new BufferedReader(reader);

	            String str = null;

	            while((str = br.readLine()) != null) {
	            	if(str.equals(keyDI.getBookName())){
	            		urlList = br.readLine();
	            		break;
	            	}
	            }
	            br.close();
	            reader.close();
			}catch(FileNotFoundException e) {
                e.printStackTrace();
			}
			catch(IOException e) {
                e.printStackTrace();
			}
			
			String[] bufURL = urlList.split(";");
			for(int i=0 ; i<bufURL.length; i++){
				bufList.add(DetailInfoDao.findByProperty("URL", bufURL[i]).get(0));
			}
			
			for(DetailInfo di : detailInfoList){
				String ISBN = di.getISBN().trim();
				bufList.addAll(DetailInfoDao.findByProperty("ISBN", ISBN));
			}
			Set<String> bookURL = new HashSet<String>();
			for(DetailInfo di : bufList){
				if(!bookURL.contains(di.getURL())){
					bookURL.add(di.getURL());
					finalList.add(di);
				}
			}
			
			if(finalList.isEmpty()){
				return null;
			}else{
				return finalList;
			}
		}
		
		
		//此部分最终应扩展为搜索所有相关
		public static List<DetailInfo> getBookDetailInfoByURL(String picUrl){
			List<DetailInfo> detailInfoList = new ArrayList<DetailInfo>(); 
			List<String> bookURL = new ArrayList<String>();		
			detailInfoList = DetailInfoDao.findByProperty("picURL", picUrl);
			
			List<DetailInfo> bufList = new ArrayList<DetailInfo>();
			
			//此处需加代码找数据库中相关书籍
			for(DetailInfo di : detailInfoList){
				String ISBN = di.getISBN().trim();
				
				bookURL.add(di.getURL());
				
				bufList.addAll(DetailInfoDao.findByProperty("ISBN", ISBN));
			}
			
			for(DetailInfo di : bufList){
				if(!bookURL.contains(di.getURL())){
					detailInfoList.add(di);
				}
			}
			
			if(detailInfoList.isEmpty()){
				return null;
			}else{
				return detailInfoList;
			}
		}
		
		public static boolean isExistInList(List<DetailInfo> list, DetailInfo di){
			for(DetailInfo detail : list){
				if(di.getURL().equals(detail.getURL())){
					return true;
				}
			}
			return false;
		}
		
		public static List<DetailInfo> getBooksByCategory(String category){
			int firstpos = category.indexOf('#', 0);
			int secondpos = category.indexOf('#', firstpos + 1);
			String topCategory = null, secondCategory = null, thirdCategory = null;
			
			if(firstpos != -1 && secondpos != -1){
				topCategory = category.substring(0, firstpos);
				secondCategory = category.substring(firstpos + 1, secondpos);
				thirdCategory = category.substring(secondpos + 1, category.length());
			}else if(firstpos != -1 && secondpos == -1){
				topCategory = category.substring(0, firstpos);
				secondCategory = category.substring(firstpos + 1, category.length());
				thirdCategory = null;
			}else if(firstpos == -1 && secondpos == -1){
				topCategory = category;
				secondCategory = null;
				thirdCategory = null;
			}
			
			List<DetailInfo> tempList = DetailInfoDao.getDetailInfoByCategory(topCategory);
			List<DetailInfo> secTempList = new ArrayList<DetailInfo>();
			List<DetailInfo> thirdTempList = new ArrayList<DetailInfo>();
			
			//从文件中获取分类具体信息
			Map<String, List<String>> localCategory = ReadCategory.getCategoryMapping();
			List<String> ddjdSecondCategory = localCategory.get(secondCategory);
			List<String> ddjdThirdCategory = localCategory.get(secondCategory);
			
			if(ddjdSecondCategory!= null && !ddjdSecondCategory.isEmpty()){
				ddjdSecondCategory.add(secondCategory);
System.out.println(ddjdSecondCategory.toString());
				for(DetailInfo di : tempList){
					for(String ddjdSecondCate : ddjdSecondCategory){
						if(di.getCategory().contains(ddjdSecondCate)){
							secTempList.add(di);
						}
					}
				}
				
				if(ddjdThirdCategory != null && !ddjdThirdCategory.isEmpty()){
					ddjdThirdCategory.add(thirdCategory);
					for(DetailInfo di : secTempList){
						for(String ddjdThirdCate : ddjdThirdCategory){
System.out.println(ddjdThirdCate);
							if(di.getCategory().contains(ddjdThirdCate)){
								thirdTempList.add(di);
							}
						}
					}
					return thirdTempList;
				}else if(thirdCategory == null){
					return secTempList;
				}
			}
			
			if(tempList.size() >= 30){
				tempList = tempList.subList(0, 30);
			}
			
			return tempList;
		}
		
		public static List<DetailInfo> getDetailInfoByCategory(String category){
			List<DetailInfo> tempList = new ArrayList<DetailInfo>();
			List<DetailInfo> allTempList = new ArrayList<DetailInfo>();
			List<DetailInfo> finalList = new ArrayList<DetailInfo>();
			
			Map<String, List<String>> localCategory = ReadCategory.getCategoryMapping();
			List<String> ddjdCategory = localCategory.get(category);
			
			if(ddjdCategory != null){
				ddjdCategory.add(category);
				for(String cate : ddjdCategory){
					tempList = DetailInfoDao.findByProperty("category", cate);
					allTempList.addAll(tempList);
				}
			}
			
			//去重 
			List<String> urlTemp = new ArrayList<String>();
			for(DetailInfo di : allTempList){
				if(!urlTemp.contains(di.getURL())){
					urlTemp.add(di.getURL());
				}
			}
			for(String url : urlTemp){
				for(DetailInfo di : allTempList){
					if(url.equals(di.getURL())){
						finalList.add(di);
						break;
					}
				}
			}
System.out.println("最终集合大小" + finalList.size());				
			return finalList;
		}
		
		
		public static List<DetailInfo> getSpecialCategoryBook(List<DetailInfo> detailInfoList, String category){
			List<DetailInfo> specialCategoryBookList = new ArrayList<DetailInfo>();
			
			Map<String, List<String>> localCategory = ReadCategory.getCategoryMapping();
			
			List<String> ddjdCategory = localCategory.get(category);
			

			if(ddjdCategory != null){
			ddjdCategory.add(category);
				for(DetailInfo di : detailInfoList){
					for(String cate : ddjdCategory){

						if(di.getCategory().contains(cate) && !DetailInfoDao.isExistInList(specialCategoryBookList, di)){
							specialCategoryBookList.add(di);
						}
					}
				}
				
				return specialCategoryBookList;
			}
			else{
				return detailInfoList;
			}
		}
}

	
	
//对书按照价格排序
class ComparatorOfDetailInfo implements Comparator{
	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		double prices1 = 100,prices2 = 100;
		
		DetailInfo book1 = (DetailInfo)o1;
		DetailInfo book2 = (DetailInfo)o2;
		
		try{
			prices1 = Double.valueOf(book1.getPrice());
		}catch(Exception e){
			prices1 = 100;
		}
		
		try{
			prices2 = Double.valueOf(book2.getPrice());
		}catch(Exception e){
			prices2 = 100;
		}
		
		if(prices1 < prices2){
			return -1;
		}else if(prices1 == prices2){
			return 0;
		}else{
			return 1;
		}
	}
	
}
