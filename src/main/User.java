package main;

import data_structures.SinglyLinkedList;
import interfaces.List;

public class User {
/**
 * This class is responsible for stating the elements and variables that will be used in the user list,
 * its methods are based upon getting the data 
 * */
private int userId;
private String fullName;
private List<Book> booksInPossesion = new SinglyLinkedList<>();

public User(int userId , String fullName, List<Book>booksInPossesion){
	this.userId = userId;
	this.fullName = fullName;
	this.booksInPossesion = booksInPossesion;
}

public int getId() {
	/**
	 * This methos is in charge of getting the id of the user
	 * */
		return userId;
	}

	public void setId(int id) {
		/**
		 * This method sets the value of the id of the user
		 * */
		this.userId = id;
	}

	public String getName() {
		/**
		 * This method is in charge of getting the name of the user
		 * */
		return fullName;
	}

	public void setName(String name) {
		/**
		 * This method sets the value of the name of the user
		 * */
		this.fullName = name;
	}

	public List<Book> getCheckedOutList() {
		/**
		 * This method is in charge of getting the list of the books that the users have borrowed
		 * */
		return booksInPossesion;
	}

	public void setCheckedOutList(List<Book> checkedOutList) {
		/**
		 * This method sets the value of the checkedOutList
		 * */
		this.booksInPossesion = checkedOutList;
	}
	
}
