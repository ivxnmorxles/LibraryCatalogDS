package main;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Book {
	/**
	 * This class is responsible for getting and setting the parameters
	 * that are found in the book list and its content, there is also
	 * the methods which will permit the program to get some elements and
	 * handle other operations
	 * */
	
private int id;
private String title;
private String author;
private String genres;
private LocalDate lastCheckOut;
private boolean checkedOut;
private float moneyOwed;
private float startingFee;
	
public Book(int id, String title, String author, String genres, LocalDate lastCheckout, boolean checkedOut){
		this.id = id;
		this.title = title;
		this.author = author;
		this.genres = genres;
		this.lastCheckOut = lastCheckout;
		this.checkedOut = checkedOut;
	}
	
public int getId() {
	/**
	 * This method returns the id of the book
	 * */
		return id;
	}
	public void setId(int id) {
		/**
		 * This method sets the value of the id
		 * */
		this.id = id;
	}
	public String getTitle() {
		/**
		 * This method returns the title or name of the book
		 * */
		return title;
	}
	public void setTitle(String title) {
		/**
		 * This method sets the value of title
		 * */
		this.title = title;
	}
	public String getAuthor() {
		/**
		 * This method returns the author of the book
		 * */
		return author;
	}
	public void setAuthor(String author) {
		/**
		 * This method sets the value of the author
		 * */
		this.author = author;
	}
	public String getGenre() {
		/**
		 * This method returns the genre of the book
		 * */
		return genres;
	}
	public void setGenre(String genre) {
		/**
		 * This method sets the value of the genre
		 * */
		this.genres = genre;
	}
	public LocalDate getLastCheckOut() {
		/**
		 * This method returns when the book was last CheckedOut, refers to the date
		 * */
		return lastCheckOut;
	}
	public void setLastCheckOut(LocalDate lastCheckOut) {
		/**
		 * This method sets the value for lastCheckOut
		 * */
		this.lastCheckOut = lastCheckOut;
	}
	public boolean isCheckedOut() {
		/**
		 * This method returns a boolean which says if the book was checkedOut or not
		 * */
		return checkedOut;
	}
	public void setCheckedOut(boolean checkedOut) {
		/**
		 * This method sets the value of the checkedOut element
		 * */
		this.checkedOut = checkedOut;
	}
	
	@Override
	public String toString() {
		/**
		 * This method is in charge of converting the title and author element and making it uppercased when called upon
		 * @return returns the title and the author in an uppercase format
		 * */
		/*
		 * This is supposed to follow the format
		 * 
		 * {TITLE} By {AUTHOR}
		 * 
		 * Both the title and author are in uppercase.
		 */
		return title.toUpperCase() + " BY " + author.toUpperCase();
	}
	public float calculateFees() {
		/**
		 * This method is in charge of calculating the fees that each book is 
		 * accumulating depending how long the user has had the book
		 * and the current date
		 * @return returns the money owed, the accumulated fee
		 * */
		/*
		 * fee (if applicable) = base fee + 1.5 per additional day
		 */
		if(checkedOut == false){
			moneyOwed = 0.0f;
		}
		LocalDate todaysDate = LocalDate.of(2023, 9, 15);
		long daysWithBook = this.getLastCheckOut().until(todaysDate,ChronoUnit.DAYS);
		if(daysWithBook >= 31 ){
			startingFee = 10f;
			float additionalFee = (daysWithBook - 31) * 1.5f;
			moneyOwed = startingFee + additionalFee;
		}
		return moneyOwed;
	}
}
