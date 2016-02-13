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
            <% 
            /** Initialize BAR protocol (python commands) */
            
            /** -------------------- Add the hidden services -------------------- */
            // ----- 1st service -----
            String[] cmd1 = {"/bin/sh", "-c", "cd BarApp/libs/BAR && "
                    + "python bin/bar contacts add --name a-service-name --label 9240aaebd1451db5a6284ebce0bdfec4 --sharedkey 1d8be2a6b0d3b886e549efab44985700"};
            Process process = Runtime.getRuntime().exec(cmd1);
            
            // ----- 2nd service -----
            String[] cmd2 = {"/bin/sh", "-c", "cd BarApp/libs/BAR && "
                    + "python bin/bar contacts add --name b-service-name --label 9240aaebd1451db5a6284ebce0bdfec4 --sharedkey 1d8be2a6b0d3b886e549efab44985700"};
            process = Runtime.getRuntime().exec(cmd2);
            
            // ----- 3rd service -----
            String[] cmd3 = {"/bin/sh", "-c", "cd BarApp/libs/BAR && "
                    + "python bin/bar contacts add --name c-service-name --label 9240aaebd1451db5a6284ebce0bdfec4 --sharedkey 1d8be2a6b0d3b886e549efab44985700"};
            process = Runtime.getRuntime().exec(cmd3);
             
            /** -------------------- Login to BAR server -------------------- */
            // ----- 1st service -----
            String[] cmd4 = {"/bin/sh", "-c", "cd BarApp/libs/BAR && "
                    + "python bin/bar login --name a-service-name --role hidden-client --server 83.212.169.68"};
            process = Runtime.getRuntime().exec(cmd4);
            
            // ----- 2nd service -----
            String[] cmd5 = {"/bin/sh", "-c", "cd BarApp/libs/BAR && "
                    + "python bin/bar login --name b-service-name --role hidden-client --server 83.212.169.68"};
            process = Runtime.getRuntime().exec(cmd5);
            
            // ----- 3rd service -----
            String[] cmd6 = {"/bin/sh", "-c", "cd BarApp/libs/BAR && "
                    + "python bin/bar login --name c-service-name --role hidden-client --server 83.212.169.68"};
            process = Runtime.getRuntime().exec(cmd6);
            
            %>

            <jsp:include page="imports/import_menu.jsp"/>

            <div id="contents">
                <script type="text/javascript" src="imports/import_title.js"></script>
            </div>
            <div id="footer">
                <ul id="services" class="wrapper clearfix">
                    <li>
                        <a href="http://maplocationservice.bar/"><img src="images/maplocation_img.png" alt="Img" height="204" width="220"></a>
                        <h3><a href="http://maplocationservice.bar/">MapLocation site</a></h3>
                    </li>
                    <li>
                        <a href="http://timebooksservice.bar/"><img src="images/timebooks_img.png" alt="Img" height="204" width="220"></a>
                        <h3><a href="http://timebooksservice.bar/">Timebooks site</a></h3>
                    </li>
                    <li>
                        <a href="http://profinderservice.bar/"><img src="images/profinder_img.png" alt="Img" height="204" width="220"></a>
                        <h3><a href="http://profinderservice.bar/">ProFinder site</a></h3>
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
