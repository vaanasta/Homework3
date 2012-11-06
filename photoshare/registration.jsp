<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="style.css" rel="stylesheet" type="text/css" id="stylesheet"/>
<title>Photoshare Register</title>
</head>

<body>

<script type="text/javascript">
	var RecaptchaOptions = { theme : 'blackglass' };
</script>

<form name="registrationform" method="post" action="register">

<table align="center"><tr>
  <td><img src="register_moto.png" width="1024" height="128" alt="register_moto" /></td></tr></table>

<table width="300px" align="center">
<tr><td colspan=2></td></tr>
  <tr>
  <td align="right"><b>Email</b></td>
  <td><input type="text" name="email" value=""></td>
  </tr>
  <tr>
  <td align="right"><b>Username</b></td>
  <td><input type="text" name="user" value=""></td>
  </tr>
  <tr>
  <td align="right"><b>Password</b></td>
  <td><input type="password" name="pass" value=""></td>
  </tr>
  <tr>
  <td align="right"><b>Retype Password</b></td>
  <td><input type="password" name="pass2" value=""></td>
  </tr>
  <tr>
  <td align="center" colspan=2>
  	<%
  	ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LeehNgSAAAAAHnQE0GJGjLn2w8Hbqx_1450PpkX", "6LeehNgSAAAAAP2nv9YmfNprFns5YX_6_rv-svzs ", false);
    out.print(c.createRecaptchaHtml(null, null));
	%>
  </td>
  </tr>
  <tr>
  <td align="center" colspan=2><input type="submit" name="Register" value="Register"></td>
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