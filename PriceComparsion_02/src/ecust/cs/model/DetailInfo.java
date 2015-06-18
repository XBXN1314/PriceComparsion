package ecust.cs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="detail")
public class DetailInfo {
		//书名
		private String bookName;
		//作者
		private String author;
		//出版社
		private String press;
		//出版日期
		private String publishingTime;
		//版本
		private String edition;
		
		//网站名称
		private String website;
		//书的超链接
		private String URL;
		//评论次数
		private String comment;
		//收藏人气
		private String collections;
		//推荐指数
		private String recommend;
		//价格
		private String price;
		//原价
		private String orignalPrice;
		//页数
		private String pages;
		//字数
		private String words;
		//印刷时间
		private String printTime;
		//开本
		private String opens;
		//纸张
		private String pageAttr;
		//印次
		private String printCount;
		//包装
		private String packages;
		//类别
		private String category;
		//图片URL
		private String picURL;
		
		public String getISBN() {
			return ISBN;
		}
		public void setISBN(String iSBN) {
			ISBN = iSBN;
		}
		//ISBN
		private String ISBN;
		
		public String getBookName() {
			return bookName;
		}
		public void setBookName(String bookName) {
			this.bookName = bookName;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getPress() {
			return press;
		}
		public void setPress(String press) {
			this.press = press;
		}
		public String getPublishingTime() {
			return publishingTime;
		}
		public void setPublishingTime(String publishingTime) {
			this.publishingTime = publishingTime;
		}
		public String getEdition() {
			return edition;
		}
		public void setEdition(String edition) {
			this.edition = edition;
		}
		
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public String getRecommend() {
			return recommend;
		}
		public void setRecommend(String recommend) {
			this.recommend = recommend;
		}
		public String getOrignalPrice() {
			return orignalPrice;
		}
		public void setOrignalPrice(String orignalPrice) {
			this.orignalPrice = orignalPrice;
		}
		public String getPages() {
			return pages;
		}
		public void setPages(String pages) {
			this.pages = pages;
		}
		public String getWords() {
			return words;
		}
		public void setWords(String words) {
			this.words = words;
		}
		public String getPrintTime() {
			return printTime;
		}
		public void setPrintTime(String printTime) {
			this.printTime = printTime;
		}
		public String getOpens() {
			return opens;
		}
		public void setOpens(String opens) {
			this.opens = opens;
		}
		public String getPageAttr() {
			return pageAttr;
		}
		public void setPageAttr(String pageAttr) {
			this.pageAttr = pageAttr;
		}
		public String getPrintCount() {
			return printCount;
		}
		public void setPrintCount(String printCount) {
			this.printCount = printCount;
		}
		public String getPackages() {
			return packages;
		}
		public void setPackages(String packages) {
			this.packages = packages;
		}
		public String getWebsite() {
			return website;
		}
		public void setWebsite(String website) {
			this.website = website;
		}
		
		@Id
		public String getURL() {
			return URL;
		}
		public void setURL(String uRL) {
			URL = uRL;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getCollections() {
			return collections;
		}
		public void setCollections(String collections) {
			this.collections = collections;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getPicURL() {
			return picURL;
		}
		public void setPicURL(String picURL) {
			this.picURL = picURL;
		}
		
		
		public boolean euqals(DetailInfo di){
			if(this.getURL().equals(di.getURL())){
				return true;
			}else{
				return false;
			}
		}
		
}
