package server.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.google.gson.Gson;

import server.AppConstants;
import server.AppFunctions;
import server.Message;
import server.Status;
import server.model.Book;
import server.model.BookLikedByUser;
import server.model.BookOfUser;
import server.model.Id;
import server.model.Review;

/**
 * Servlet implementation class BooksServlet
 */
@WebServlet(
		description = "Servlet to provide details about books", 
		urlPatterns = { "/books",
				"/books/myBooks",
				"/books/closeBook",
				"/books/likeBook",
				"/books/unlikeBook"				
		})
public class BooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BooksServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.sendError(401);
		} 
		catch (Exception  e) {
			getServletContext().log("Error while closing connection", e);
			response.sendError(500);//internal server error
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			
        	//obtain ExampleDB data source from Tomcat's context
    		Context context = new InitialContext();
    		BasicDataSource ds = (BasicDataSource)context.lookup(getServletContext().getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
    		Connection conn = ds.getConnection();
    		String uri = request.getRequestURI();
    		PrintWriter writer = response.getWriter();

			Gson gson = new Gson();
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
    		// Data from client
			String gsonData = "";
			if (br != null) {
				gsonData = br.readLine();
			}
			String jsonData = gsonData;
			
    		if (uri.indexOf(AppConstants.MYBOOKS) != -1){ //filter books by specific user
    			Id userId = gson.fromJson(jsonData, Id.class);
    			ArrayList<BookOfUser> booksOfUser = AppFunctions.getBooksOfUser(conn, userId.getValue());
    			
    			if (booksOfUser == null || booksOfUser.isEmpty()) {
    				writer.write(gson.toJson(new Message(Status.BooksNotFound, AppConstants.NO_BOOKS_FOR_USER)));
    			}
    			else {
    				writer.write(gson.toJson(new Message(Status.BooksFound, "", booksOfUser)));
				}
    		}
    		else if (uri.indexOf(AppConstants.CLOSEBOOK) != -1){ 
    			BookOfUser bof = gson.fromJson(jsonData, BookOfUser.class);
    			
    			AppFunctions.performCloseBook(conn, bof);
    			
    		}
    		else if (uri.indexOf(AppConstants.UNLIKEBOOK) != -1){ 
    			BookLikedByUser blbu = gson.fromJson(jsonData, BookLikedByUser.class);
    			
    			AppFunctions.performUnlikeBook(conn, blbu);
    			
    			writer.write(gson.toJson(new Message(Status.Success, "Book Unliked")));
    		}
    		else if (uri.indexOf(AppConstants.LIKEBOOK) != -1){ 
				BookLikedByUser blbu = gson.fromJson(jsonData, BookLikedByUser.class);
    			
    			AppFunctions.performLikeBook(conn, blbu);
    			
    			writer.write(gson.toJson(new Message(Status.Success, "Book liked")));
    		}
    		else if (uri.indexOf(AppConstants.WRITE_REVIEW) != -1){ 
    			Review review = gson.fromJson(jsonData, Review.class);
    			
    			if(AppFunctions.performReviewBook(conn, review)){
    				writer.write(gson.toJson(new Message(Status.Success, "Review saved")));
    			}
    			else {
    				writer.write(gson.toJson(new Message(Status.Failure, "Review could not be saved")));
    			}
    			
    		}
    		else { //get all available books
    			ArrayList<Book> books = AppFunctions.getAllBooks(conn);
    			
    			if (books == null || books.isEmpty()) {
    				writer.write(gson.toJson(new Message(Status.BooksNotFound, AppConstants.NO_BOOKS_IN_DB)));
    			}
    			else {
    				writer.write(gson.toJson(new Message(Status.BooksFound, "", books)));
				}
    		}
			writer.close();
			conn.close();
    	} 
		catch (SQLException | NamingException e) {
    		getServletContext().log("Error while closing connection", e);
    		response.sendError(500);//internal server error
    	}
	}

}
