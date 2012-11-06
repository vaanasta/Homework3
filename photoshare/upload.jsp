<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="style.css" rel="stylesheet" type="text/css" id="stylesheet"/>
<title>Photoshare Upload File</title>
</head>

<body>

		<%
			String name=(String) session.getAttribute("username");
			if(name==null){
			%>
				<table align="center"><tr>
  					<td><img src="wrong_moto.png" width="700" height="128" alt="wrong_moto" /></td></tr>
				</table>
				<h2 align="center">You hava to login in order to upload a photo.</h2>
			<%
            }
			else{
		%>
        
<table align="center"><tr>
<td><img src="upload_moto.png" width="1024" height="128" alt="upload_moto" /></td></tr></table>
        
		<form action="upload" enctype="multipart/form-data" method="POST">
        
		<table width="200px" align="center">
          <tr><td></td></tr>
          <tr>
          <td align="left"><input type="text" name="caption" value=""><b> Photo Title</b></td>
          </tr>
          <tr>
          <td align="left"><input type="file" name="file1" accept="image/x-png, image/gif, image/jpeg"></td>
          </tr>
          
          <tr><td></td></tr>
          <tr>
          <td align="left"><input type="Submit" value="Upload Photo "></td>
          </tr>
        </table>
        
        
        
        <%}%>
        
        <table>
			<tr><td><img id="photoshare_logo" src="photoshare_logo.png" width="256" height="256" alt="photoshare_logo" /></td></tr>
			<tr><td><a href="main.jsp"><img id="main_black" src="main_black.png" width="250" height="70" alt="main" /><img id="main_green" src="main_green.png" width="250" height="70" alt="main" /></a></td></tr>
			<tr><td><a href="upload.jsp"><img id="upload_black" src="upload_black.png" width="250" height="70" alt="upload" /><img id="upload_green" src="upload_green.png" width="250" height="70" alt="upload" /></a></td></tr>
			<tr><td><a href="login.jsp"><img id="login_black" src="login_black.png" width="250" height="70" alt="login" /><img id="login_green" src="login_green.png" width="250" height="70" alt="login" /></a></td></tr>
			<tr><td><a href="registration.jsp"><img id="register_black" src="register_black.png" width="250" height="70" alt="register" /><img id="register_green" src="register_green.png" width="250" height="70" alt="register" /></a></td></tr>
		</table>

</body>
</html>