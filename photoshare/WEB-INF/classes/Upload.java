import java.io.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
 
 
public class Upload extends HttpServlet {
	private static final String TMP_DIR_PATH = "/var/lib/tomcat6/webapps/photoshare/server_data/temp";
	private File tmpDir;
	private static final String DESTINATION_DIR_PATH ="/server_data/files";
	private File destinationDir;
 
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		tmpDir = new File(TMP_DIR_PATH);
		if(!tmpDir.isDirectory()) {
			throw new ServletException(TMP_DIR_PATH + " is not a directory");
		}
		String realPath = getServletContext().getRealPath(DESTINATION_DIR_PATH);
		destinationDir = new File(realPath);
		if(!destinationDir.isDirectory()) {
			throw new ServletException(DESTINATION_DIR_PATH+" is not a directory");
		}
 
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
            
            
	    PrintWriter out = response.getWriter();
	    response.setContentType("text/html");
	    out.println();
 
		DiskFileItemFactory  fileItemFactory = new DiskFileItemFactory ();
		/*
		 *Set the size threshold, above which content will be stored on disk.
		 */
		fileItemFactory.setSizeThreshold(1*1024*1024); //1 MB
		/*
		 * Set the temporary directory to store the uploaded files of size above threshold.
		 */
		fileItemFactory.setRepository(tmpDir);
 
		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		try {
			/*
			 * Parse the request
			 */
			List items = uploadHandler.parseRequest(request);
			Iterator itr = items.iterator();
                        
                        String caption = new String("");
			while(itr.hasNext()) {
                            
				FileItem item = (FileItem) itr.next();
				/*
				 * Handle Form Fields.
				 */
				if(item.isFormField()) {
                                        
                                        String emptyString = new String("");
                                        String tempString = new String("caption");
                                        
                                        if(tempString.equals(item.getFieldName())){
                                            if(emptyString.equals(item.getString())){ caption = "No Title"; }
                                            else{ caption = item.getString(); }
                                        }
                                        
				} else {
                                    
                                        Calendar calendar = new GregorianCalendar();
                                        int hour = calendar.get(Calendar.HOUR);
                                        int minute = calendar.get(Calendar.MINUTE);
                                        int second = calendar.get(Calendar.SECOND);
                                        String fileName = new String( calendar.get(Calendar.DAY_OF_MONTH)+ "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.YEAR) + "_" + calendar.get(Calendar.HOUR) + "." + calendar.get(Calendar.MINUTE) + "." + calendar.get(Calendar.SECOND)+ "." + item.getName());
                                        
                                        File file = new File(destinationDir, fileName);
                                        item.write(file);
                                        BufferedImage bimg = ImageIO.read(file);
                                        
                                        if(bimg == null){ response.sendRedirect(response.encodeRedirectURL("/photoshare/upload.jsp")); out.close(); return; }
                                        
                                        int width = bimg.getWidth();
                                        int height = bimg.getHeight();
                                    
                                        HttpSession session = request.getSession(true);
                                        
                                        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
                                        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
                                        out.println("<head>");
                                        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
                                        out.println("<link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\" id=\"stylesheet\"/>");
                                        out.println("<title>Photoshare Upload</title>");
                                        out.println("</head>");
                                        out.println("<body>");
                                        out.println("<table align=\"center\"><tr>");
                                        out.println("  <td><img src=\"upload_moto.png\" width=\"1024\" height=\"128\" alt=\"upload_moto\" /></td></tr>");
                                        out.println("</table>");
                                        //out.println(("<h2 align=\"center\">" + (int)item.getSize()/1024) + "KB : File " + item.getName() + " uploaded successfully.</h2>");
                                        
                                        int boxWidth = 1024;
                                        int boxHeight = (boxWidth*height)/width;
                                        
                                        out.println("<div align=\"center\" class=\"post\">");
                                        out.println("   <link href=\"cropzoom/jquery-ui-1.7.2.custom.css\" rel=\"Stylesheet\" type=\"text/css\" />");
                                        out.println("   <link href=\"cropzoom/jquery.cropzoom.css\" rel=\"Stylesheet\" type=\"text/css\" />");
                                        out.println("   <script type=\"text/javascript\" src=\"cropzoom/jquery-1.6.2.min.js\"></script>");
                                        out.println("   <script type=\"text/javascript\" src=\"cropzoom/jquery-ui-1.8.14.custom.min.js\"></script>");
                                        out.println("   <script type=\"text/javascript\" src=\"cropzoom/jquery.cropzoom.js\"></script>");
                                        out.println("   <script type=\"text/javascript\">");
                                        out.println("       $(document).ready(function(){");
                                        out.println("           var cropzoom = $('#crop_container').cropzoom({");
                                        out.println("               width:" + boxWidth + ",");
                                        out.println("               height:" + boxHeight + ",");
                                        out.println("               bgColor: '#CCC',");
                                        out.println("               enableRotation:true,");
                                        out.println("               enableZoom:true,");
                                        out.println("               zoomSteps:10,");
                                        out.println("               rotationSteps:10,");
                                        out.println("               selector:{");
                                        out.println("                   centered:true,");
                                        out.println("                   borderColor:'black',");
                                        out.println("                   borderColorHover:'red',");
                                        out.println("                   startWithOverlay: true");
                                        out.println("               },");
                                        out.println("               image:{");
                                        out.println("                   source:'server_data/files/" + fileName + "',");
                                        out.println("                   width:" + width + ",");
                                        out.println("                   height:" + height + ",");
                                        out.println("                   minZoom:50,");
                                        out.println("                   maxZoom:500");
                                        out.println("               }");
                                        out.println("           });");
                                        out.println("           $('#crop').click(function(){ ");
                                        out.println("               cropzoom.send('resize_and_crop.php','POST',{},function(rta){");
                                        out.println("               self.location=\"main.jsp\";");
                                        //out.println("                  $('.result').find('img').remove();");
                                        //out.println("                   var img = $('<img />').attr('src',rta);");
                                        //out.println("                   $('.result').find('.txt').hide().end().append(img);");
                                        out.println("               });");
                                        out.println("           });");
                                        out.println("           $('#restore').click(function(){ cropzoom.restore(); });");
                                        out.println("       });");
                                        out.println("   </script>");
                                        out.println("");
                                        out.println("<h2 align=\"center\">" + caption + "</h2>");
                                        out.println("<div id=\"crop_container\"></div>");
                                        out.println("<p align=\"center\"><button id=\"crop\">Save Changes</button><button id=\"restore\">Restore Image</button><br /></p>");
                                        out.println("<div style=\"clear:both\"></div>");
                                        out.println("");
                                        out.println("<div id=\"movement\" style=\"float:left;width:80px\"></div>");
                                        out.println("<div id=\"sliders\" style=\"float:left;width:400px\">");
                                        out.println("<div id=\"zoom\" style=\"margin:15px 0 0 0\"></div>");
                                        out.println("<div id=\"rot\" style=\"clear:both\"></div>");
                                        out.println("");
                                        out.println("<div style=\"clear:both\"></div>");
                                        out.println("<div align=\"center\" class=\"result\"></div>");
                                        out.println("</div>");
                                                
                                        
                                        //out.println("<table align=\"center\"><tr>");
                                        //out.println("   <td><img src=\"/photoshare" + DESTINATION_DIR_PATH + "/" + item.getName() + "\"/></td></tr>");
                                        //out.println("</table>");
                                        
					
                                        
                                        out.println("<table>");
                                        out.println("<tr><td><img id=\"photoshare_logo\" src=\"photoshare_logo.png\" width=\"256\" height=\"256\" alt=\"photoshare_logo\" /></td></tr>");
                                        out.println("<tr><td><a href=\"main.jsp\"><img id=\"main_black\" src=\"main_black.png\" width=\"250\" height=\"70\" alt=\"main\" /><img id=\"main_green\" src=\"main_green.png\" width=\"250\" height=\"70\" alt=\"main\" /></a></td></tr>");
                                        out.println("<tr><td><a href=\"upload.jsp\"><img id=\"upload_black\" src=\"upload_black.png\" width=\"250\" height=\"70\" alt=\"upload\" /><img id=\"upload_green\" src=\"upload_green.png\" width=\"250\" height=\"70\" alt=\"upload\" /></a></td></tr>");
                                        out.println("<tr><td><a href=\"login.jsp\"><img id=\"login_black\" src=\"login_black.png\" width=\"250\" height=\"70\" alt=\"login\" /><img id=\"login_green\" src=\"login_green.png\" width=\"250\" height=\"70\" alt=\"login\" /></a></td></tr>");
                                        out.println("<tr><td><a href=\"registration.jsp\"><img id=\"register_black\" src=\"register_black.png\" width=\"250\" height=\"70\" alt=\"register\" /><img id=\"register_green\" src=\"register_green.png\" width=\"250\" height=\"70\" alt=\"register\" /></a></td></tr>");
                                        out.println("</table>");
                                        out.println("</body>");
                                        out.println("</html>");
                                        
                                        
					
                                        File xml = new File(destinationDir, fileName + ".xml");
                                        BufferedWriter output = new BufferedWriter(new FileWriter(xml));
                                        output.write("<img>\n");
                                        output.write("  <filename>" + fileName + "</filename>\n");
                                        output.write("  <user>" + (String)session.getAttribute("username") + "</user>\n");
                                        output.write("  <caption>" + caption + "</caption>\n");
                                        output.write("</img>\n");
                                        output.close();
				}
				
			}
		}catch(FileUploadException ex) {
			log("Error encountered while parsing the request",ex);
		} catch(Exception ex) {
			log("Error encountered while uploading file",ex);
		}
                
                out.close();
 
	}
 
}