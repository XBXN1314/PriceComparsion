package ecust.cs.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bookInfo")
public class BookInfo {
	//书ID
	private String bookID;
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

	
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	//ISBN
	private String ISBN;
	
	@Id
	public String getBookID() {
		return bookID;
	}
	public void setBookID(String bookID) {
		this.bookID = bookID;
	}
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
	
	
}
