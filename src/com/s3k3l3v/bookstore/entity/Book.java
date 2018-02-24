package com.s3k3l3v.bookstore.entity;

import java.sql.Date;

public class Book {
	protected int id;
	protected String title;
	protected String author;
	protected String editon;
	protected Date dateEditon;
	protected int numberCopies;

	public Book() {
	}

	public Book(int id) {
		this.id = id;
	}



	public Book(String title, String author, String editon, Date dateEditon, int numberCopies) {
		this.title = title;
		this.author = author;
		this.editon = editon;
		this.dateEditon = dateEditon;
		this.numberCopies = numberCopies;
	}

	public Book(int id, String title, String author, String editon, Date dateEditon, Integer numberCopies) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.editon = editon;
		this.dateEditon = dateEditon;
		this.numberCopies = numberCopies;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEditon() {
		return editon;
	}

	public void setEditon(String editon) {
		this.editon = editon;
	}

	public Date getDateEditon() {
		return dateEditon;
	}

	public void setDateEditon(Date dateEditon) {
		this.dateEditon = dateEditon;
	}

	public int getNumberCopies() {
		return numberCopies;
	}

	public void setNumberCopies(int numberCopies) {
		this.numberCopies = numberCopies;
	}

	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", title='" + title + '\'' +
				", author='" + author + '\'' +
				", editon='" + editon + '\'' +
				", dateEditon=" + dateEditon +
				", numberCopies=" + numberCopies +
				'}';
	}
}
