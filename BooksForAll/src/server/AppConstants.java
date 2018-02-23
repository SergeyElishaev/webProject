package server;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.reflect.TypeToken;

import server.model.User;

/**
 * A simple place to hold global application constants
 */
public interface AppConstants {

	//app constants
	public final Type USER_COLLECTION = new TypeToken<Collection<User>>() {}.getType();
	public final String NAME = "name";
	public final String USERNAME = "username";
	public final String BOOKS = "books";
	public final String MYBOOKS = "myBooks";
	public final String CLOSEBOOK = "closeBook";
	public final String LIKEBOOK = "likeBook";
	public final String UNLIKEBOOK = "unlikeBook";
	public final String WRITE_REVIEW = "writeReview";
	
	public final String ALLUSERS = "allUsers";
	public final String USERS_FILE = "users.json";
	public final String BOOKS_FILE = "books.json";
	public final String REVIEWS_FILE = "reviews.json";
	public final String BOOKS_OF_USER_FILE = "booksOfUser.json";
	public final String BOOKS_LIKED_BY_USER_FILE = "booksLikedByUser.json";
	
	//Statuses
	public final String PENDING = "pending";
	public final String APPROVED = "approved";
	public final String REJECTED = "rejected";
	
	//Validations
	public final String WARNING_EMPTY_USERNAME = "Please enter a username";
	public final String WARNING_USERNAME_EXIST = "Username already exists";
	public final String WARNING_WRONG_USERNAME = "Username can be up to 10 characters";
	public final String WARNING_EMPTY_PASSWORD = "Please enter a password";
	public final String WARNING_EMPTY_EMAIL = "Please insert an email";
	public final String WARNING_EMPTY_PHONE = "Please insert a phone number";
	public final String WARNING_LONG_PASSWORD = "Password can be up to 8 characters";
	public final String WARNING_WRONG_EMAIL = "Please insert a valid email";
	public final String WARNING_WRONG_PHONE = "Please insert a valid phone number";
	public final String WARNING_EMPTY_NICKNAME = "Please choose a username";
	public final String WARNING_LONG_DESCRIPTION = "Max description length is 50";
	
	public final String WARNING_EMPTY_STREET = "Street is mandatory";
	public final String WARNING_WRONG_STREET = "Please insert a valid street";
	public final String WARNING_WRONG_NUMBER = "Please insert a valid House Number";
	public final String WARNING_EMPTY_CITY = "City is mandatory";
	public final String WARNING_WRONG_CITY = "Please insert a valid City";
	public final String WARNING_EMPTY_ZIP = "ZIP code is mandatory";
	public final String WARNING_WRONG_ZIP= "Please insert a valid ZIP code";
	
	public final String REGISTRATION_FAIL = "Failed to register a new user";
	public final String REGISTRATION_SUCCESS= "User registered successfully";
	
	public final String WARNING_WRONG_LOGIN = "Wrong Username or Password";
	public final String LOGIN_WELCOME = "Welcome!";
	
	public final String NO_USERS_IN_DB = "The are no user registered to the site.";
	public final String NO_BOOKS_IN_DB = "There are no available books right now, try again later.";
	public final String NO_BOOKS_FOR_USER = "You don't have books. But you can buy in the store.";
	
	//derby constants
	public final String DB_NAME = "DB_NAME";
	public final String DB_DATASOURCE = "DB_DATASOURCE";
	public final String PROTOCOL = "jdbc:derby:"; 
	public final String OPEN = "Open";
	public final String SHUTDOWN = "Shutdown";

	//Table names
	public final String USERS_TABLE = "USERS";
	public final String BOOKS_TABLE = "BOOKS";
	public final String REVIEWS_TABLE = "REVIEWS";
	public final String BOOKS_OF_USER_TABLE = "BOOKSOFUSER";
	public final String BOOKS_LIKED_BY_USER_TABLE = "BOOKSLIKEDBYUSER";
	
	//sql SELECT statements
	public final String SELECT_ALL_USERS = "SELECT * FROM USERS";
	public final String SELECT_USERS_BY_USERNAME = "SELECT * FROM USERS WHERE username=?";
	public final String SELECT_USER_BY_LOGIN_STMT = "SELECT * FROM USERS WHERE username=? AND password=?";
	public final String SELECT_ALL_BOOKS = "SELECT * FROM BOOKS";
	public final String SELECT_BOOKSOFUSER_BY_USERID = "SELECT * FROM BOOKSOFUSER WHERE userId=?";
	public final String SELECT_BOOKS_BY_BOOKID = "SELECT * FROM BOOKS WHERE id=?";
	public final String SELECT_REVIEWS_BY_BOOKID = "SELECT * FROM REVIEWS WHERE bookId=?";
//	public final String SELECT_BOOKS_OF_USER = "SELECT * FROM BOOKSOFUSER AS BOF CROSS JOIN BOOKS AS B ON BOF.bookId = B.id WHERE BOF.userId = ?";
//	public final String SELECT_BOOKS_OF_USER = "SELECT * FROM BOOKS, BOOKSOFUSER WHERE BOOKSOFUSER.userId = ? AND BOOKSOFUSER.bookId = BOOKS.id";
	
	
	//sql INSERT statements
	public final String INSERT_NEW_USER = "INSERT INTO USERS(Username, Email, Phone, Password, Nickname, Description, PhotoURL, Role, AddrStreet, AddrNumber, AddrCity, AddrZip) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
	public final String INSERT_NEW_USER_WITH_ID = "INSERT INTO USERS(Username, Email, Phone, Password, Nickname, Description, PhotoURL, Role, AddrStreet, AddrNumber, AddrCity, AddrZip, Id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public final String INSERT_NEW_BOOK = "INSERT INTO BOOKS(Name, Path, ImageUrl, Description, Price, Likes, Reviews) VALUES(?,?,?,?,?,?,?)";
	public final String INSERT_NEW_BOOK_WITH_ID = "INSERT INTO BOOKS(Name, Path, ImageUrl, Description, Price, Likes, Reviews, Id) VALUES(?,?,?,?,?,?,?,?)";
	public final String INSERT_NEW_REVIEW = "INSERT INTO REVIEWS(bookId, userId, score, content, date, status) VALUES(?,?,?,?,CURRENT_TIMESTAMP,?)";
	public final String INSERT_NEW_BOOK_OF_USER = "INSERT INTO BOOKSOFUSER(bookId, userId, isOpen, isLiked, isReviewed, scrollLocation) VALUES(?,?,?,?,?,?)";
	public final String INSERT_NEW_BOOK_LIKED_BY_USER = "INSERT INTO BOOKSLIKEDBYUSER(bookId, userId) VALUES(?,?)";
	
	
	//sql UPDATE statements
	public final String SET_REVIEW_AS_APPROVED = "UPDATE REVIEWS SET status='approved' WHERE bookId=? AND userId=?";
	public final String UPDATE_SCROLL_LOCATION = "UPDATE BOOKSOFUSER SET scrollLocation=? WHERE bookId=? AND userId=?";
	public final String UPDATE_BOOK_OF_USER_ISOPEN = "UPDATE BOOKSOFUSER SET isOpen=true WHERE bookId=? AND userId=?";
	public final String LIKE_A_BOOK = "UPDATE BOOKS SET likes = likes + 1 WHERE id=?";
	public final String UNLIKE_A_BOOK = "UPDATE BOOKS SET likes = likes - 1 WHERE id=?";
	public final String SET_BOOK_OF_USER_AS_LIKED = "UPDATE BOOKSOFUSER SET isLiked = true WHERE bookId=? AND userId=?";
	public final String SET_BOOK_OF_USER_AS_UNLIKED = "UPDATE BOOKSOFUSER SET isLiked = false WHERE bookId=? AND userId=?";
	public final String SET_BOOK_OF_USER_AS_REVIEWED = "UPDATE BOOKSOFUSER SET isReviewed = true WHERE bookId=? AND userId=?";
	public final String REVIEW_A_BOOK = "UPDATE BOOKS SET reviews = reviews + 1 WHERE id=?";
	
	//sql DELETE statements
	public final String DELETE_USER = "DELETE FROM USERS WHERE userId=?";
	
	//sql CREATE statements	
	public final String CREATE_USERS_TABLE = "CREATE TABLE USERS(" + 
			"id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 21, INCREMENT BY 1)," +
			"username varchar(10) NOT NULL," + 
			"email varchar(80) NOT NULL," + 
			"phone varchar(20) NOT NULL," + 
			"password varchar(32) NOT NULL," + 
			"nickname varchar(20) UNIQUE," + 
			"description varchar(50)," + 
			"photoUrl varchar(500)," + 
			"role integer NOT NULL default 0," +
			"addrStreet varchar(200) NOT NULL," + 
			"addrNumber integer NOT NULL," + 
			"addrCity varchar(200) NOT NULL," + 
			"addrZip varchar(200) NOT NULL," + 
			"PRIMARY KEY(id))";
	public final String CREATE_BOOKS_TABLE = "CREATE TABLE BOOKS(" + 
			"id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 11, INCREMENT BY 1)," +
			"name varchar(200) NOT NULL," + 
			"path varchar(500) NOT NULL," + 
			"imageUrl varchar(500)," +
			"price real NOT NULL," + 
			"description varchar(50)," + 
			"likes integer default 0," +
			"reviews integer default 0," + 
			"PRIMARY KEY(id))";
	public final String CREATE_REVIEWS_TABLE = "CREATE TABLE REVIEWS(" + 
			"bookId integer NOT NULL," + 
			"userId integer NOT NULL," + 
			"score integer NOT NULL," + 
			"content varchar(2000) NOT NULL," + 
			"date timestamp," + 
			"status varchar(20) default 'pending'," + 
			"FOREIGN KEY(userId) REFERENCES USERS (id)," + 
			"FOREIGN KEY(bookId) REFERENCES BOOKS (id))";
	public final String CREATE_BOOKS_OF_USER_TABLE = "CREATE TABLE BOOKSOFUSER(" + 
			"bookId integer NOT NULL," + 
			"userId integer NOT NULL," + 
			"isOpen boolean default false," + 
			"isLiked boolean default false," +
			"isReviewed boolean default false," +
			"scrollLocation varchar(200)," +
			"FOREIGN KEY(userId) REFERENCES USERS (id)," + 
			"FOREIGN KEY(bookId) REFERENCES BOOKS (id))";
	public final String CREATE_BOOKS_LIKED_BY_USER_TABLE = "CREATE TABLE BOOKSLIKEDBYUSER(" + 
			"bookId integer NOT NULL," + 
			"userId integer NOT NULL," + 
			"FOREIGN KEY(userId) REFERENCES USERS (id)," + 
			"FOREIGN KEY(bookId) REFERENCES BOOKS (id))";
}
