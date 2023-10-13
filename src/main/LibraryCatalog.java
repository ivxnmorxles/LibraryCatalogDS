package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;

import data_structures.ArrayList;
import data_structures.DoublyLinkedList;
import data_structures.SinglyLinkedList;
import interfaces.FilterFunction;
import interfaces.List;

public class LibraryCatalog {
/**
 *  In this class is where most of the operations for the report would be done,
 *  the purpose of this class is to get the lists bookCatalog and userClients to
 *  be able to utilize with the other methods and generate a report, since we extract
 *  the info from a .csv file a BufferedReader was used.
 *  @author Ivan Morales
 *  
 *  @param bookReader is in charge of allowing the program to read the data from catalog.csv
 *  @param userReader is in charge of allowing the program to read the data from user.csv
 *  @param bookCatalog list where we will store the values of the Books
 *  @param userClients list where we will store the values of the users
 * 
 * */
private BufferedReader bookReader = new BufferedReader(new FileReader("data/catalog.csv"));
private BufferedReader userReader = new BufferedReader(new FileReader("data/user.csv"));
private List<Book> bookCatalog;
private List<User> userClients;

    public LibraryCatalog() throws IOException {
    	this.bookCatalog = getBooksFromFiles();
    	this.userClients = getUsersFromFiles();
    	

	}
	private List<Book> getBooksFromFiles() throws IOException {
		/**
		 * This method is where we declare and start filling up our lists with the data required
		 * @bookCatalog is a single linked list where we will store the values
		 * a Single Linked List was chosen due to its efficiency in saving space and as later on
		 * there would be methods were we need to add or remove books, choosing a Single linked list
		 * was a safe option for this.
		 * @param line - is in charge of skipping the first line in the catalog.csv file
		 * @param id - is in charge of passing the value of the list, in position "0", is the ID of the book
		 * @param title - is in charge of passing the value to the list, in position "1" and is the name of the book
		 * @param author - is in charge of passing the value to the list, in position "2" and is the name of the author
		 * @param genres - is in charge of passing the value to the list,  in position "3" and says the genre of the book
		 * @param lastCheckOut - is in charge of passing values to the list, in position "4" and checks the date it was checkedOut
		 * @param checkedOut - is in charge of passing values to the list, in position "5" and checks if the book was returned
		 * @param bookCatalog.add - adds all of those elements to the linkedlist
		 * @return returns the bookcatalog List with all the lements added to the list
		 * */
	List<Book> bookCatalog = new SinglyLinkedList<Book>();
	String line = this.bookReader.readLine();
	line = this.bookReader.readLine();
	while(line!=null) {
	String[] data = line.split(",");
	int id = Integer.parseInt(data[0]);
	String title = data[1];
	String author = data[2];
	String genres = data[3];
	LocalDate lastCheckOut = LocalDate.parse(data[4]);
	Boolean checkedOut = Boolean.parseBoolean(data[5]);
	bookCatalog.add(new Book(id, title, author, genres, lastCheckOut, checkedOut));
	line = bookReader.readLine();
	}
		return bookCatalog;
	}
	
	private List<User> getUsersFromFiles() throws IOException {
	/**
	 * This method is where we declare and find the data of the Clients active with the library
	 * For this a Singly Linked List was also chosen, due to efficiency with space, also since we wouldn't
	 * need to call a single element directly, we have to traverse through the list.
	 * For this part two Single linked List were used to facilitate finding their respective parameters
	 *  
	 * the condition if(line_data.length == 3) is there because of the parameters of the users in user.csv were some of them had
	 * books borrowed, while others had null spaces, so in the code we say that after a line of data we take the info of the book IDs that is enclosed
	 * after the name of the user, it later takes those IDs retrieved from that list and checks what book corresponds to it thanks to the bookCatalog
	 * @param getUsersFromFile - is the list were the info about the users would be added to
	 * @param bookIdWithUser - list created to solely traverse the book id's that would later be added to the getUser list
	 * @return returns the getUserFromFile linked list with all its parameters
	 * 
	 * */
	List<User>getUsersFromFile=new SinglyLinkedList<User>();
		
	String line=this.userReader.readLine();
	line=this.userReader.readLine();
	while(line!=null) {
		List<Book>bookIdWithUser = new SinglyLinkedList<Book>();
		String[]line_data = line.split(",");
		if(line_data.length ==3) {
			String booksBorrowed=line_data [2].substring(1,line_data [2].length()-1);
			String[] booksBorrowedpt2=booksBorrowed.split(" ");
			for(int i=0; i<booksBorrowedpt2.length; i++) {
				bookIdWithUser.add(this.bookCatalog.get(Integer.parseInt(booksBorrowedpt2[i])-1));
			}
			}
			 getUsersFromFile.add(new User(Integer.parseInt(line_data [0]), line_data [1], bookIdWithUser ));
			 line=this.userReader.readLine();
		}
		return getUsersFromFile;
	}
		
	public List<Book> getBookCatalog() {
		/**
		 * @return returns the bookCatalog
		 * */
		return bookCatalog;
	}
	public List<User> getUsers() {
		/**
		 * @return returns the userClients
		 * */
		return userClients;
	}
	public void addBook(String title, String author, String genre) {
		/**
		 * This method creates a new book, it expands the bookCatalog size to make the new book fit and it sets its parameters of
		 * check in date and if it has been checkedOut
		 * @param title the name of the book
		 * @param author the name of the author
		 * @param genre the genre of the book
		 * @param newBookNumber provides the id for the book based on where it is added
		 * @param newBook the new book that is being created, with already determining its check in date and if it has been checked out
		 * */
	int newBookNumber = bookCatalog.size() +1;
	Book newBook = new Book(newBookNumber,title,author,genre, LocalDate.of(2023, 9, 15), false);
	bookCatalog.add(newBook);
	}
	public void removeBook(int id) {
		/**
		 * This methods removes a book depending of the id 
		 * @param id the id Number which specifies which book it is
		 * */
	this.bookCatalog.remove(id - 1);
	}	
	
	public boolean checkOutBook(int id) {
		/**
		 * This methods is responsible for checking if the book has been taken, or if someone has checked it out
		 * it searches for the book and if the id appears it was checked out and it should give the date it was checked out
		 * @param bookInStock variable created to check if the book is there
		 * @return returns true if it was checkedOut, if not returns false
		 * */
	Book bookInStock = null;
	for(Book book:bookCatalog) {
		if(book.getId()==id) {
		bookInStock =book;
		break;
		}
	}
	if(bookInStock==null) {
	return false;
	}
	if(bookInStock.isCheckedOut()) {
	return false;
	}
	bookInStock.setCheckedOut(true);
	bookInStock.setLastCheckOut(LocalDate.of(2023,9,15));
		return true;
	}
	
	public boolean returnBook(int id) {
		/**
		 * this method checks if the book was returned, if it was returned it makes isCheckedOut false
		 * @return returns true if the book was returned, if not returns false
		 * */
	Book isBookReturned = null;
	for(Book book:bookCatalog) {
	  if(book.getId()==id) {
		  isBookReturned=book;
		  break;
	  }
	}
	if(isBookReturned==null) {
		return false;
	}
	if(!isBookReturned.isCheckedOut()) {
		return false;
	}
	isBookReturned.setCheckedOut(false);
		return true;
	}
	
	public boolean getBookAvailability(int id) {
		/**
		 * This method checks if the book is available in the catalog, it checks if it was CheckedOut, if it wasn't
		 * it is available
		 * @param isCheckedOut checks if the book was taken or not
		 * @return returns true if the book is not checkedOut, if it is checkedOut  it returns false
		 * */
	if(!this.bookCatalog.get(id).isCheckedOut()){
		return true;
	}
	return false;
	}
	
	public int bookCount(String title) {
		/**
		 * This method creates a counter which checks how many books are in the list
		 * @return returns the total amount of books in the list
		 * */
	int counter = 0;
	for(Book book:bookCatalog) {
	  if(book.getTitle().equals(title)) {
		  counter++;
	  }
	}
		return counter;
	}
	
	public void generateReport() throws IOException {
		/**
		 * This method is responsible for printing out the variables assigned to it 
		 * to create a report about the library and utilizes all the previous methods 
		 * to do so
		 * @param output stores the values that will be printed in the report
		 * @return returns a report in the console
		 * */
		
		String output = "\t\t\t\tREPORT\n\n";
		output += "\t\tSUMMARY OF BOOKS\n";
		output += "GENRE\t\t\t\t\t\tAMOUNT\n";
		/*
		 * In this section you will print the amount of books per category.
		 * 
		 * Place in each parenthesis the specified count. 
		 * 
		 * Note this is NOT a fixed number, you have to calculate it because depending on the 
		 * input data we use the numbers will differ.
		 * 
		 * How you do the count is up to you. You can make a method, use the searchForBooks()
		 * function or just do the count right here.
		 */
		List<String>genres = new SinglyLinkedList<String>();
		genres.add("Adventure");
		genres.add("Fiction");
		genres.add("Classics");
		genres.add("Mystery");
		genres.add("Science Fiction");
		int totalBooksInGenre = 0;
		for(String genre:genres) {
			int countByGenre = booksByGenreCounter(genre);
			output+= genre+ "\t\t\t\t\t" + countByGenre + "\n";
			totalBooksInGenre+= countByGenre;
		}
		output+="====================================================\n";
		output+="\t\t\tTOTAL AMOUNT OF BOOKS\t" + totalBooksInGenre + "\n\n";
		
	
		//output += "Adventure\t\t\t\t\t" + (/*Place here the amount of adventure books*/) + "\n";
		//output += "Fiction\t\t\t\t\t\t" + (/*Place here the amount of fiction books*/) + "\n";
		//output += "Classics\t\t\t\t\t" + (/*Place here the amount of classics books*/) + "\n";
		//output += "Mystery\t\t\t\t\t\t" + (/*Place here the amount of mystery books*/) + "\n";
		//output += "Science Fiction\t\t\t\t\t" + (/*Place here the amount of science fiction books*/) + "\n";
		//output += "====================================================\n";
		//output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (/*Place here the total number of books*/) + "\n\n";
		
		/*
		 * This part prints the books that are currently checked out
		 */
		output+= "\t\t\tBOOKS CURRENTLY CHECKED OUT\n\n";
		List<Book> currentlyCheckedOut = searchForBook(book -> book.isCheckedOut());
		for(Book book:currentlyCheckedOut) {
			output+=book.toString()+ "\n";
		}
		//output += "\t\t\tBOOKS CURRENTLY CHECKED OUT\n\n";
		/*
		 * Here you will print each individual book that is checked out.
		 * 
		 * Remember that the book has a toString() method. 
		 * Notice if it was implemented correctly it should print the books in the 
		 * expected format.
		 * 
		 * PLACE CODE HERE
		 */
		
		output+= "====================================================\n";
		output+= "\t\t\tTOTAL AMOUNT OF BOOKS\t" + currentlyCheckedOut.size()+ "\n\n";
		
		//output += "====================================================\n";
		//output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" (/*Place here the total number of books that are checked out*/) + "\n\n";
		
		
		/*
		 * Here we will print the users the owe money.
		 */
		output+= "\n\n\t\tUSERS THAT OWE BOOK FEES\n\n";
		DecimalFormat formatDeci = new DecimalFormat("0.00");
		float libraryDebt = 0;
		for(User user:userClients) {
			float totalBookFees = 0;
			List<Book>userWithBooks = user.getCheckedOutList();
			for(Book book:userWithBooks) {
				totalBookFees+=book.calculateFees();
			}
			if(totalBookFees>0) {
				output+= user.getName() + "\t\t\t\t\t$" + formatDeci.format(totalBookFees) + "\n";
				libraryDebt+= totalBookFees;
			}
			
	
		}
		//output += "\n\n\t\tUSERS THAT OWE BOOK FEES\n\n";
		/*
		 * Here you will print all the users that owe money.
		 * The amount will be calculating taking into account 
		 * all the books that have late fees.
		 * 
		 * For example if user Jane Doe has 3 books and 2 of them have late fees.
		 * Say book 1 has $10 in fees and book 2 has $78 in fees.
		 * 
		 * You would print: Jane Doe\t\t\t\t\t$88.00
		 * 
		 * Notice that we place 5 tabs between the name and fee and 
		 * the fee should have 2 decimal places.
		 * 
		 * PLACE CODE HERE!
		 */

		output+= "====================================================\n";
		output+= "\t\t\t\tTOTAL DUE\t$" + formatDeci.format(libraryDebt)/*calcTotalLibraryDebt()*/ + "\n\n\n";
		//output += "====================================================\n";
		//output += "\t\t\t\tTOTAL DUE\t$" + (/*Place here the total amount of money owed to the library.*/) + "\n\n\n";
		output += "\n\n";
		System.out.println(output);// You can use this for testing to see if the report is as expected.
		
		/*
		 * Here we will write to the file.
		 * 
		 * The variable output has all the content we need to write to the report file.
		 * 
		 * PLACE CODE HERE!!
		 */
		String fileName = "report/report.txt";
		try(BufferedWriter reportWriter = new BufferedWriter(new FileWriter(fileName))){
			reportWriter.write(output);
			System.out.println(fileName);
		}
	}
	
	/*
	 * BONUS Methods
	 * 
	 * You are not required to implement these, but they can be useful for
	 * other parts of the project.
	 */
	public List<Book> searchForBook(FilterFunction<Book> func) {
		/**
		 * This method utilizes a lambda method to filter through the books and help find a specific book
		 * @return returns the book that was filtered 
		 * */
		List<Book>booksFiltered = new SinglyLinkedList();
		for(Book book: bookCatalog) {
			if(func.filter(book)) {
				booksFiltered.add(book);
			}
		}
		return booksFiltered;
	}
	/*
	public float calcTotalLibraryDebt() {
		float owedLibraryDebt = 0.0f;
		for(User user:userClients) {
			List<Book>userWithBooks=user.getCheckedOutList();
			for(Book book:userWithBooks) {
				owedLibraryDebt += book.calculateFees();
			}
		}
		return owedLibraryDebt;
		- function originally made to help with fees, after encountering errors with total$, used a different approach
	}
	*/
	
	public int booksByGenreCounter(String genre) {
		/**
		 * this method is responsible for having a counter which counts how many books are in a certain genre
		 * @return returns how many books are in a genre
		 * */
		int counter= 0;
		for(Book book: bookCatalog) {
			if(book.getGenre().equals(genre)) {
			  counter++;
			}
		}
		return counter;
		
	}
	public List<User> searchForUsers(FilterFunction<User> func) {
		/**
		 * This method utilizes a lambda function to filter through the users in the userClients list
		 * @return returns the user that was filtered and found
		 * */
		List<User>usersFiltered = new SinglyLinkedList();
		for(User user : userClients) {
			if(func.filter(user)) {
				usersFiltered.add(user);
			}
		}
		return usersFiltered;
	}
	
}
