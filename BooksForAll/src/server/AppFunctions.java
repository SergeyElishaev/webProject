package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import server.model.Book;
import server.model.BookLikedByUser;
import server.model.BookOfUser;
import server.model.Review;
import server.model.ReviewToShow;
import server.model.User;
import server.model.UserLite;

public class AppFunctions {

	private AppFunctions() { }
	
	public static User getUserByLogin(Connection conn, String username, String password) {
		User user = null;
		try {
			PreparedStatement prepStmt = conn.prepareStatement(AppConstants.SELECT_USER_BY_LOGIN_STMT);
			prepStmt.setString(1, username);
			prepStmt.setString(2, password);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) { // user was found
				user = new User(rs.getString("Username"), 
						rs.getString("Email"), 
						rs.getString("Phone"), 
						rs.getString("Password"), 
						rs.getString("Nickname"), 
						rs.getString("Description"), 
						rs.getString("PhotoUrl"), 
						rs.getString("AddrStreet"), 
						rs.getString("AddrCity"), 
						rs.getString("AddrZip"),
						Integer.parseInt(rs.getString("AddrNumber")), 
						Integer.parseInt(rs.getString("Role")),
						Integer.parseInt(rs.getString("Id"))
						); 
			}
			rs.close();
			prepStmt.close();
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static ArrayList<UserLite> getAllUsers(Connection conn) {
		ArrayList<UserLite> users = new ArrayList<UserLite>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(AppConstants.SELECT_ALL_USERS);
	        while (rs.next()) {
	        	users.add(new UserLite(rs.getString("Username"), 
						rs.getString("Email"), 
						rs.getString("Phone"), 
						rs.getString("Nickname"), 
						rs.getString("Description"), 
						rs.getString("PhotoUrl"), 
						rs.getString("AddrStreet"), 
						rs.getString("AddrCity"), 
						rs.getString("AddrZip"),
						Integer.parseInt(rs.getString("AddrNumber")), 
						Integer.parseInt(rs.getString("Role")),
						Integer.parseInt(rs.getString("Id"))
						));
	        }
	        rs.close();
	        stmt.close();
	        return users;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
		
	public static ArrayList<Book> getAllBooks(Connection conn) {
		ArrayList<Book> books = new ArrayList<Book>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(AppConstants.SELECT_ALL_BOOKS);
	        while (rs.next()) {
	        	books.add(new Book(rs.getString("Name"), 
						rs.getString("Path"), 
						rs.getString("ImageUrl"), 
						rs.getString("Description"), 
						Float.parseFloat(rs.getString("Price")), 
						Integer.parseInt(rs.getString("Likes")), 
						Integer.parseInt(rs.getString("Reviews")),
						Integer.parseInt(rs.getString("Id")) 
						));
	        }
	        rs.close();
	        stmt.close();
	        return books;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<BookOfUser> getBooksOfUser(Connection conn, int userId) {
		ArrayList<BookOfUser> booksOfUser = new ArrayList<BookOfUser>();
		
		String UID = new Integer(userId).toString();
		try {
			PreparedStatement prepStmt = conn.prepareStatement(AppConstants.SELECT_BOOKSOFUSER_BY_USERID);
			prepStmt.setString(1, UID);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) { // book of user was found
				int bookId = Integer.parseInt(rs.getString("bookId"));
				PreparedStatement prepStmt2 = conn.prepareStatement(AppConstants.SELECT_BOOKS_BY_BOOKID);
				prepStmt2.setString(1, new Integer(bookId).toString());
				ResultSet rs2 = prepStmt2.executeQuery();
				while (rs2.next()) { // books found
					ArrayList<ReviewToShow> Reviews = new ArrayList<ReviewToShow>();
					PreparedStatement prepStmt3 = conn.prepareStatement(AppConstants.SELECT_REVIEWS_BY_BOOKID);
					prepStmt3.setString(1, new Integer(bookId).toString());
					ResultSet rs3 = prepStmt3.executeQuery();
					while (rs3.next()) { // reviews found
						Reviews.add(new ReviewToShow(Integer.parseInt(rs3.getString("BookId")),
								Integer.parseInt(rs3.getString("UserId")),
								Integer.parseInt(rs3.getString("Score")),
								rs3.getString("Content"),
								Timestamp.valueOf(rs3.getString("Date"))
						));
					}
					booksOfUser.add(new BookOfUser(bookId,
							userId,
							Integer.parseInt(rs2.getString("Likes")),
							Integer.parseInt(rs2.getString("Reviews")),
							Boolean.parseBoolean(rs.getString("IsOpen")),
							Boolean.parseBoolean(rs.getString("IsLiked")),
							Boolean.parseBoolean(rs.getString("IsReviewed")),
							rs2.getString("Name"), 
							rs2.getString("Path"), 
							rs2.getString("ImageUrl"), 
							rs2.getString("Description"),
							Integer.parseInt(rs.getString("ScrollLocation")),
							Reviews							
					));
					rs3.close();
					prepStmt3.close();
				}
				rs2.close();
				prepStmt2.close();
			}
			rs.close();
			prepStmt.close();
			
			return booksOfUser;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void performCloseBook(Connection conn, BookOfUser bof) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement(AppConstants.UPDATE_SCROLL_LOCATION);
			prepStmt.setInt(1, bof.getScrollLocation());
			prepStmt.setInt(2, bof.getBookId());
			prepStmt.setInt(3, bof.getUserId());
			prepStmt.executeUpdate();
			prepStmt.close();
			
			PreparedStatement prepStmt2 = conn.prepareStatement(AppConstants.UPDATE_BOOK_OF_USER_ISOPEN);
			prepStmt2.setInt(1, bof.getBookId());
			prepStmt2.setInt(2, bof.getUserId());
			prepStmt2.executeUpdate();
			prepStmt2.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void performUnlikeBook(Connection conn, BookLikedByUser blbu) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement(AppConstants.SET_BOOK_OF_USER_AS_UNLIKED);
			prepStmt.setInt(1, blbu.getBookId());
			prepStmt.setInt(2, blbu.getUserId());
			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement prepStmt = conn.prepareStatement(AppConstants.UNLIKE_A_BOOK);
			prepStmt.setInt(1, blbu.getBookId());
			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void performLikeBook(Connection conn, BookLikedByUser blbu) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement(AppConstants.SET_BOOK_OF_USER_AS_LIKED);
			prepStmt.setInt(1, blbu.getBookId());
			prepStmt.setInt(2, blbu.getUserId());
			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement prepStmt = conn.prepareStatement(AppConstants.LIKE_A_BOOK);
			prepStmt.setInt(1, blbu.getBookId());
			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean performReviewBook(Connection conn, Review review) {
		boolean result = true;
		try {
			PreparedStatement prepStmt = conn.prepareStatement(AppConstants.SET_BOOK_OF_USER_AS_REVIEWED);
			prepStmt.setInt(1, review.getBookId());
			prepStmt.setInt(2, review.getUserId());
			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		}
		try {
			PreparedStatement prepStmt = conn.prepareStatement(AppConstants.REVIEW_A_BOOK);
			prepStmt.setInt(1, review.getBookId());
			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		}
		try {
			PreparedStatement prepStmt = conn.prepareStatement(AppConstants.INSERT_NEW_REVIEW);
			prepStmt.setInt(1, review.getBookId());
			prepStmt.setInt(2, review.getUserId());
			prepStmt.setInt(3, review.getScore());
			prepStmt.setString(4, review.getContent());
			prepStmt.setString(5, AppConstants.PENDING);
			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public static boolean isUsernameExist(Connection conn, String username) {
		boolean result = false;
		try {
			PreparedStatement prepStmt = conn.prepareStatement(AppConstants.SELECT_USERS_BY_USERNAME);
			prepStmt.setString(1, username);
			ResultSet rs = prepStmt.executeQuery();
			if (rs.next()) { // user exist
				result = true;
			}
			rs.close();
			prepStmt.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	public static boolean registerNewUser(Connection conn, User user) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement(AppConstants.INSERT_NEW_USER);
			prepStmt.setString(1, user.getUsername());
			prepStmt.setString(2, user.getEmail());
			prepStmt.setString(3, user.getPhone());
			prepStmt.setString(4, user.getPassword());
			prepStmt.setString(5, user.getNickname());
			prepStmt.setString(6, user.getDescription());
			prepStmt.setString(7, user.getPhotoURL());
			prepStmt.setString(8, user.getRoleAsString());
			prepStmt.setString(9, user.getAddrStreet());
			prepStmt.setString(10, user.getAddrNumberAsString());
			prepStmt.setString(11, user.getAddrCity());
			prepStmt.setString(12, user.getAddrZip());
			
			prepStmt.executeUpdate();
			prepStmt.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	
}
