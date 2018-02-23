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
import server.model.LoginData;
import server.model.User;
import server.model.UserLite;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet(
		description = "Servlet to provide details about books", 
		urlPatterns = { "/admin", "/admin/allUsers" }
		)
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
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
    		
    		if (uri.indexOf(AppConstants.ALLUSERS) != -1){
	    		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
				// Data from client
				String gsonData = "";
				if (br != null) {
					gsonData = br.readLine();
				}
				String jsonData = gsonData;
				
				ArrayList<UserLite> users = AppFunctions.getAllUsers(conn);
	    		
	    		if (users == null || users.isEmpty()) {
					writer.write(gson.toJson(new Message(Status.NoUsersInDB, AppConstants.NO_USERS_IN_DB)));
					return;
				} 
				else {
					writer.write(gson.toJson(new Message(Status.LoginFailure, AppConstants.WARNING_WRONG_LOGIN)));
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
