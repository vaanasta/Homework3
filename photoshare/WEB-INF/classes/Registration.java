import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

public class Registration extends HttpServlet{

	private ServletConfig config;
        PrintWriter out;
	
	public void init(ServletConfig config)
	  throws ServletException{
		 this.config=config;
	   }
        
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
			
		out = response.getWriter();
		String connectionURL = "jdbc:mysql://localhost/photoshare";
		Connection connection = null;
		ResultSet rs;
                
                String email = new String("");
		String userName = new String("");
		String passwrd = new String("");
                String remoteAddr = new String("");
		response.setContentType("text/html");
                
                out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
                out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
                out.println("<head>");
                out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
                out.println("<link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\" id=\"stylesheet\"/>");
                out.println("<title>Photoshare</title>");
                out.println("</head>");
                out.println("<body>");
                
                int error = 0;
		try {
                    
                        if(email.equalsIgnoreCase(request.getParameter("email"))){ print_wrong_once(error); out.println("<h2 align=\"center\">Email field is empty.</h2>"); error = 1; }
                        if(userName.equalsIgnoreCase(request.getParameter("user"))){ print_wrong_once(error); out.println("<h2 align=\"center\">Username field is empty.</h2>"); error = 1; }
                        if(passwrd.equalsIgnoreCase(request.getParameter("pass"))){ print_wrong_once(error); out.println("<h2 align=\"center\">Password field is empty.</h2>"); error = 1; }
                        if(error == 1){ print_menu(); out.println("</body>"); out.println("</html>"); return; }
                    
			 // Load the database driver
			Class.forName("com.mysql.jdbc.Driver");
			// Get a Connection to the database
			connection = DriverManager.getConnection(connectionURL, "photoshare", "photoshare");
			//Add the data into the database
			String sql = "SELECT username, email FROM users";
			Statement s = connection.createStatement();
			s.executeQuery (sql);
			rs = s.getResultSet();
                        
			while (rs.next ()){
                                email=rs.getString("email");
				userName=rs.getString("username");
                                if(email.equals(request.getParameter("email"))){ print_wrong_once(error); out.println("<h2 align=\"center\">Email '" + email +"' already exists.</h2>"); error = 1; }
                                if(userName.equals(request.getParameter("user"))){ print_wrong_once(error); out.println("<h2 align=\"center\">Username '" + userName +"' already exists.</h2>"); error = 1; }
                                if(error == 1){ break; }
			}
                        
                        passwrd = request.getParameter("pass");
                        if(!passwrd.equalsIgnoreCase(request.getParameter("pass2"))){ print_wrong_once(error); out.println("<h2 align=\"center\">Passwords don't match.</h2>"); error = 1; }
                        
                        remoteAddr = request.getRemoteAddr();
                        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
                        reCaptcha.setPrivateKey("6LeehNgSAAAAAP2nv9YmfNprFns5YX_6_rv-svzs ");

                        String challenge = request.getParameter("recaptcha_challenge_field");
                        String uresponse = request.getParameter("recaptcha_response_field");
                        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);
                        
                        if (!reCaptchaResponse.isValid()) { print_wrong_once(error); out.print("<h2 align=\"center\">Validation code is wrong.</h2>"); error = 1; }
                        
                        if(error == 1){
                            print_menu(); 
                            out.println("</body>"); 
                            out.println("</html>");
                            rs.close ();
                            s.close (); 
                            return; 
                        }else{
                            
                            sql = "INSERT INTO `photoshare`.`users` (`username`, `password`, `email`) VALUES ('" + request.getParameter("user") + "', '" + request.getParameter("pass") + "', '" + request.getParameter("email") + "')";
                            s.executeUpdate(sql);
                            
                            out.println("<table align=\"center\"><tr>");
                            out.println("  <td><img src=\"success_moto.png\" width=\"512\" height=\"128\" alt=\"success_moto\" /></td></tr>");
                            out.println("</table>");
                            out.println("<h2 align=\"center\">Registration was successful.</h2>");
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

                        
			rs.close ();
			s.close ();
			}catch(Exception e){
                            System.out.println("Unexpected error: " + e);
			}
			
	}
        
        public void destroy() {
 
        }
        
        private void print_wrong_once(int error){
            if(error==0){
                out.println("<table align=\"center\"><tr>");
                out.println("  <td><img src=\"wrong_moto.png\" width=\"700\" height=\"128\" alt=\"wrong_moto\" /></td></tr>");
                out.println("</table>");
            }
        }
        
        private void print_menu(){
            out.println("<table>");
            out.println("<tr><td><img id=\"photoshare_logo\" src=\"photoshare_logo.png\" width=\"256\" height=\"256\" alt=\"photoshare_logo\" /></td></tr>");
            out.println("<tr><td><a href=\"main.jsp\"><img id=\"main_black\" src=\"main_black.png\" width=\"250\" height=\"70\" alt=\"main\" /><img id=\"main_green\" src=\"main_green.png\" width=\"250\" height=\"70\" alt=\"main\" /></a></td></tr>");
            out.println("<tr><td><a href=\"upload.jsp\"><img id=\"upload_black\" src=\"upload_black.png\" width=\"250\" height=\"70\" alt=\"upload\" /><img id=\"upload_green\" src=\"upload_green.png\" width=\"250\" height=\"70\" alt=\"upload\" /></a></td></tr>");
            out.println("<tr><td><a href=\"login.jsp\"><img id=\"login_black\" src=\"login_black.png\" width=\"250\" height=\"70\" alt=\"login\" /><img id=\"login_green\" src=\"login_green.png\" width=\"250\" height=\"70\" alt=\"login\" /></a></td></tr>");
            out.println("<tr><td><a href=\"registration.jsp\"><img id=\"register_black\" src=\"register_black.png\" width=\"250\" height=\"70\" alt=\"register\" /><img id=\"register_green\" src=\"register_green.png\" width=\"250\" height=\"70\" alt=\"register\" /></a></td></tr>");
            out.println("</table>");
        }
}	