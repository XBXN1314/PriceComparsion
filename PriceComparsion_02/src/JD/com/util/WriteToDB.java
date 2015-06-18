package JD.com.util;

public class WriteToDB {
	public static void writeToDB(String bookStr){
		System.out.println(bookStr.substring(bookStr.indexOf('《') +  1, bookStr.indexOf('》')));
		System.out.println(bookStr.substring(bookStr.indexOf('(') +  1, bookStr.indexOf(')')));
		
		
	}
	
	
	
	
	public static void main(String[] args) {
		writeToDB("《向前一步·全新升级珍藏版》([美]谢丽尔·桑德伯格)【摘要 书评 试读】- 京东图书#图书#励志与成功#女性励志#向前一步·全新升级珍藏版#出版社：#中信出版社#ISBN：9787508646244#版次：2#商品编码：11548193#包装：平装#外文名称：#LEAN IN FOR GRADUATES#开本：16开#出版时间：2014-10-01#印刷时间：" +
				"2014-09-01#用纸：胶版纸#页数：324#印次：1#京东价：31.00#原价：49.00#累计评价:666" +
				"#http://img13.360buyimg.com/n4/jfs/t301/121/1556106126/93002/e87bd0b5/543ceac8N1e0cd7ab.jpghttp://item.jd.com/11548193.html");
	}
}
