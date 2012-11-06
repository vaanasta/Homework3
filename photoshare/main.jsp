<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page language="java" %>
<%@ page import="java.io.*" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.*" %>
<%@ page import="org.w3c.dom.*" %>
<%@ page import="javax.xml.parsers.DocumentBuilder" %>
<%@ page import="javax.xml.parsers.DocumentBuilderFactory" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="style.css" rel="stylesheet" type="text/css" id="stylesheet"/>
<title>Photoshare</title>

<script>
	function show_main_photo(imgName) {
    	var curImage = document.getElementById('currentImg');
    	var thePath = 'server_data/files/';
    	var theSource = thePath + imgName;
		curImage.src = theSource;
    	curImage.alt = imgName;
    	curImage.title = imgName;
	 }
</script>


</head>




<body>

<table>
<tr>

<td width="300">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</td>

<td>

<table align="center"><tr>
  <td><img src="main_moto.png" width="768" height="128" alt="main_moto" /></td></tr>
</table>

<div align="center">
	<img id="currentImg" src="" />
</div>

<div id="gallery">

<%
	String path = new String("/var/lib/tomcat6/webapps/photoshare/server_data/files");
	String srv_path = new String("/photoshare/server_data/files");

	File gallery_dir = new File(path);
	File[] files_list = gallery_dir.listFiles();
	
	for (int i = 0; i < files_list.length; i++) {
		
		Arrays.sort( files_list, new Comparator()
		{
			public int compare(Object o1, Object o2) {
		
				if (((File)o1).lastModified() > ((File)o2).lastModified()) {
					return -1;
				} else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
					return +1;
				} else {
					return 0;
				}
			}
		
		}); 
		
    	if (files_list[i].isFile()) {
			
                        String file_name = files_list[i].getName();
						int pos = file_name.lastIndexOf('.');
						String file_ext = file_name.substring(pos+1);
						
						if(file_ext.equals("xml")){
							
							DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    						DocumentBuilder db = dbf.newDocumentBuilder();
							Document doc = db.parse(path + "/" + file_name);
							
							NodeList nodeListFN = doc.getElementsByTagName("filename");
  							Element elementsFN = (Element)nodeListFN.item(0);
  							String filename = elementsFN.getChildNodes().item(0).getNodeValue();
							
							NodeList nodeListU = doc.getElementsByTagName("user");
  							Element elementsU = (Element)nodeListU.item(0);
  							String user = elementsU.getChildNodes().item(0).getNodeValue();
							
							NodeList nodeListC = doc.getElementsByTagName("caption");
  							Element elementsC = (Element)nodeListC.item(0);
  							String caption = elementsC.getChildNodes().item(0).getNodeValue();
							
							out.println("<table class=\"thumbnail\">");
							out.println("	<tr><td align=\"center\">" + caption + "</td></tr>");
							out.println("	<tr><td class=\"thumbnail_cell\"><img onclick=\"show_main_photo('" + filename + "');\" src=\"" + srv_path + "/" + filename + "\" alt=\"" + filename + "\" /></td></tr>");
							out.println("	<tr><td align=\"center\">by: " + user + "</td></tr>");
							out.println("</table>");
						}
        }
    }
%>
	
</div>
       
</td>


</tr>
</table>

<table>
<tr><td><img id="photoshare_logo" src="photoshare_logo.png" width="256" height="256" alt="photoshare_logo" /></td></tr>
<tr><td><a href="main.jsp"><img id="main_black" src="main_black.png" width="250" height="70" alt="main" /><img id="main_green" src="main_green.png" width="250" height="70" alt="main" /></a></td></tr>
<tr><td><a href="upload.jsp"><img id="upload_black" src="upload_black.png" width="250" height="70" alt="upload" /><img id="upload_green" src="upload_green.png" width="250" height="70" alt="upload" /></a></td></tr>
<tr><td><a href="login.jsp"><img id="login_black" src="login_black.png" width="250" height="70" alt="login" /><img id="login_green" src="login_green.png" width="250" height="70" alt="login" /></a></td></tr>
<tr><td><a href="registration.jsp"><img id="register_black" src="register_black.png" width="250" height="70" alt="register" /><img id="register_green" src="register_green.png" width="250" height="70" alt="register" /></a></td></tr>
</table>

</body>
</html>