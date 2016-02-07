<%-- 
    Document   : index
    Author     : mary
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="gr.unipi.webdev.barapp.entities.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Page Value -- index=1
    session.setAttribute("page", 1);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    
    <script type="text/javascript" src="imports/import_header.js"></script>
    
    <body>
        
        <jsp:include page="imports/import_menu.jsp"/>
	
	<div id="contents">
            <script type="text/javascript" src="imports/import_title.js"></script>
	</div>
	<div id="footer">
		<ul id="featured" class="wrapper clearfix">
			<li>
                            <img src="images/login_icon.png" alt="Img" height="204" width="220">
                            <h3><a href="login.jsp">Login Now!</a></h3>
                            <p>
                                    You know what to do! Let's become anonymous...
                            </p>
			</li>
                        <li>
				
			</li>
			<li>
                            <img src="images/register_icon.jpg" alt="Img" height="204" width="220">
                            <h3><a href="signup.jsp">New to BAR? Register Now!</a></h3>
                            <p>
                                    Join now the BAR network and let us lead your way to anonymity!
                            </p>
			</li>
		</ul>
		
            <script type="text/javascript" src="imports/import_footer.js"></script>
            
	</div>
</body>
</html>
