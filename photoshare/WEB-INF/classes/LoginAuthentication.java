import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginAuthentication extends HttpServlet{

	private ServletConfig config;
	
	public void init(ServletConfig config)
	  throws ServletException{
		 this.config=config;
	   }
       
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
			
                HttpSession session;
		PrintWriter out = response.getWriter();
		String connectionURL = "jdbc:mysql://localhost/photoshare";
		Connection connection = null;
		ResultSet rs;
		String userName = new String("");
		String passwrd = new String("");
		response.setContentType("text/html");
		try {
			 // Load the database driver
			Class.forName("com.mysql.jdbc.Driver");
			// Get a Connection to the database
			connection = DriverManager.getConnection(connectionURL, "photoshare", "photoshare");
			//Add the data into the database
			String sql = "SELECT username, password FROM users";
			Statement s = connection.createStatement();
			s.executeQuery (sql);
			rs = s.getResultSet();
			while (rs.next ()){
				userName=rs.getString("username");
				passwrd=rs.getString("password");
                                if(userName.equals(request.getParameter("user")) && passwrd.equals(request.getParameter("pass"))){ break; }
			}
			rs.close ();
			s.close ();
			}catch(Exception e){
			System.out.println("Exception is ;"+e);
			}
			if(userName.equals(request.getParameter("user")) && passwrd.equals(request.getParameter("pass"))){
                                session = request.getSession(true);
                                session.setAttribute("username", userName);
                                response.sendRedirect(response.encodeRedirectURL("/photoshare/main.jsp"));
				//out.println("WELCOME "+ userName);
			}
			else{
                                out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
                                out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
                                out.println("<head>");
                                out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
                                out.println("<link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\" id=\"stylesheet\"/>");
                                out.println("<title>Photoshare</title>");
                                out.println("</head>");
                                out.println("<body>");
                                out.println("<table align=\"center\"><tr>");
                                out.println("  <td><img src=\"wrong_moto.png\" width=\"700\" height=\"128\" alt=\"wrong_moto\" /></td></tr>");
                                out.println("</table>");
                                out.println("<h2 align=\"center\">Invalid username or password.</h2>");
                                out.println("<table>");
                                out.println("<tr><td><img id=\"photoshare_logo\" src=\"photoshare_logo.png\" width=\"256\" height=\"256\" alt=\"photoshare_logo\" /></td></tr>");
                                out.println("<tr><td><a href=\"main.jsp\"><img id=\"main_black\" src=\"main_black.png\" width=\"250\" height=\"70\" alt=\"main\" /><img id=\"main_green\" src=\"main_green.png\" width=\"250\" height=\"70\" alt=\"main\" /></a></td></tr>");
                                out.println("<tr><td><a href=\"upload.jsp\"><img id=\"upload_black\" src=\"upload_black.png\" width=\"250\" height=\"70\" alt=\"upload\" /><img id=\"upload_green\" src=\"upload_green.png\" width=\"250\" height=\"70\" alt=\"upload\" /></a></td></tr>");
                                out.println("<tr><td><a href=\"login.jsp\"><img id=\"login_black\" src=\"login_black.png\" width=\"250\" height=\"70\" alt=\"login\" /><img id=\"login_green\" src=\"login_green.png\" width=\"250\" height=\"70\" alt=\"login\" /></a></td></tr>");
                                out.println("<tr><td><a href=\"registration.jsp\"><img id=\"register_black\" src=\"register_black.png\" width=\"250\" height=\"70\" alt=\"register\" /><img id=\"register_green\" src=\"register_green.png\" width=\"250\" height=\"70\" alt=\"register\" /></a></td></tr>");
                                out.println("</table>");
                                out.println("</body>");
                                out.println("</html>");
			}
	}
        
        public void destroy() {
 
        }
}	