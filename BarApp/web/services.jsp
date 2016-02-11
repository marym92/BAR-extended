<%-- 
    Document   : services
    Author     : mary
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="gr.unipi.webdev.barapp.entities.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Page Value -- index=1
    session.setAttribute("page", 4);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    
    <% if (session.getAttribute("loggedin") != null) { %>
    
    <script type="text/javascript" src="imports/import_header.js"></script>
    
        <body>

            <jsp:include page="imports/import_menu.jsp"/>

            <div id="contents">
                <script type="text/javascript" src="imports/import_title.js"></script>
            </div>
            <div id="footer">
                <ul id="services" class="wrapper clearfix">
                    <li>
                        <a href=""><img src="images/maplocation_img.png" alt="Img" height="204" width="220"></a>
                        <h3><a href="">MapLocation site</a></h3>
                    </li>
                    <li>
                        <a href=""><img src="images/timebooks_img.png" alt="Img" height="204" width="220"></a>
                        <h3><a href="">Timebooks site</a></h3>
                    </li>
                    <li>
                        <a href=""><img src="images/profinder_img.png" alt="Img" height="204" width="220"></a>
                        <h3><a href="">ProFinder site</a></h3>
                    </li>
                </ul>

                <script type="text/javascript" src="imports/import_footer.js"></script>

            </div>
        </body>
    <% }
    else {
        response.sendRedirect("login.jsp");
    } %>
</html>
