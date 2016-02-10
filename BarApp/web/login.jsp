<%-- 
    Document   : login
    Author     : mary
--%>

<%@page import="gr.unipi.webdev.barapp.db.DBinfo"%>
<%@page import="gr.unipi.webdev.barapp.entities.LoginData"%>
<%@page import="gr.unipi.webdev.barapp.control.WS_Users"%>
<%@page import="gr.unipi.webdev.barapp.security.RSAkeys"%>
<%@page import="javax.xml.bind.DatatypeConverter"%>
<%@page import="java.security.PublicKey"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gr.unipi.webdev.barapp.security.SHAencrypt"%>
<%@page import="gr.unipi.webdev.barapp.entities.BARsystemParams"%>
<%@page import="gr.unipi.webdev.barapp.control.WS_SystemParams"%>
<%@page import="gr.unipi.webdev.barapp.control.UserControl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%! 
    private static String serverNo = "1";
    private static String clusterNo = "1"; 
%>

<%
    // Page Value -- login=3
    session.setAttribute("page", 3);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    
    <% if (session.getAttribute("loggedin") == null) { %>
    
        <script type="text/javascript" src="imports/import_header.js"></script>

        <script>
            function check_empty_fields(id) {
                var x = document.getElementById(id);

                if (x.value.match(/^\s*$/)) {
                    x.style.borderColor = '#B20000';
                    x.style.borderWidth = "2px";
                    x.style.borderStyle = "solid";
                } else {
                    x.style.borderColor= '';
                    x.style.borderWidth = ""; 
                }
                if(x.value=='') {
                    x.value=x.defaultValue;
                }
            }
        </script>

        <body>

            <jsp:include page="imports/import_menu.jsp"/>

            <div id="contents">
                <script type="text/javascript" src="imports/import_title.js"></script>
            </div>
            <div id="footer">
                <div id="login" class="wrapper clearfix">
                    <%
                    if ("POST".equalsIgnoreCase(request.getMethod()) && request.getParameter("submit") != null) {
                        if (request.getParameter("username")!=null && request.getParameter("password")!=null) {
                            if (!request.getParameter("username").equalsIgnoreCase("Username") && !request.getParameter("password").equalsIgnoreCase("Password")) {
                                boolean notnull = true;

                                ArrayList<BARsystemParams> systemParams;
                                LoginData ld = new LoginData();

                                ld.setUsername(request.getParameter("username").toString());
                                ld.setPassword(request.getParameter("password").toString());

                                /* ----- (a) Get nym, pk ----- */
                                String nym = UserControl.getNym();
                                PublicKey pubKey = UserControl.getPK(false);

                                if (nym==null || pubKey==null) { %>
                                    <script language="javascript">
                                        alert("This is the first time you are using the application. \nPlease sign up first.");
                                    </script>
                                <%
                                    notnull = false;
                                }

                                if (notnull) {
                                    String pkString = DatatypeConverter.printBase64Binary(pubKey.getEncoded());

                                    // Compute hash(nym|pk)
                                    SHAencrypt.SHA256encrypt(nym + "-" + pkString);

                                    /* ----- (b) WS -- Get System Parameters ----- */
                                    systemParams = WS_SystemParams.getSystemParams();

                                    /* ----- (c) Create a pair of session keys ----- */
                                    RSAkeys.createKeys("");

                                    PublicKey sessionPubKey = UserControl.getPK(true);
                                    ld.setBridgedPk(DatatypeConverter.printBase64Binary(sessionPubKey.getEncoded()));

                                    // Get IP address
                                    ld.setIp(UserControl.getIP());

                                    /* ----- (d) WS -- Send Login Data ----- */
                                    ld.setServerNo(serverNo);
                                    ld.setClusterNo(clusterNo);

                                    String result = WS_Users.login(ld);

                                    if (result.equals("")) { %>
                                        <script language="javascript">
                                            alert("There was a problem contacting server. Please try again later!");
                                        </script>
                                    <%
                                    } else {
                                        if (Integer.parseInt(result) > 0) {
                                            DBinfo.dbBarIDInsert(Integer.parseInt(result));
                                            response.sendRedirect("signup-bar.jsp");
                                        } 
                                        else if (result.equals("-102")) { %>
                                            <script language="javascript">
                                                alert("There was a problem contacting server. Please try again later!");
                                            </script>
                                        <%
                                        } else if (result.equals("-105")) { %>
                                            <script language="javascript">
                                                alert("Username and/or Password is incorrect.\nPlease check your credentials and try again!");
                                            </script>
                                        <%
                                        } else if (result.equals("-101")) { %>
                                            <script language="javascript">
                                                alert("Due to many failed login attempts, your account is locked. Please recover your password and try again!");
                                            </script>
                                        <% }
                                    }
                                }
                            }
                            else { %>
                                <script language="javascript">
                                    alert("Both Username and Password must not be Null. \nPlease try again!");
                                </script>
                        <%  } 
                        } else { %>
                            <script language="javascript">
                                alert("Both Username and Password must not be Null. \nPlease try again!");
                            </script>
                    <%  }
                    } %>
                    
                    <div class="main">
                        <h1>Login</h1>

                        <form action="login.jsp" method="post">
                            <ul>
                                <li>
                                    <label>Enter your username.</label>
                                    <input type="text" value="Username" name="username" id="username" onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}" pattern=".{4,}" required title="Username must be up to 4 characters">
                                </li>
                                <li>
                                    <label>Enter your password.</label>
                                    <input type="password" value="Password" name="password" id="password" onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}" pattern=".{6,}" required title="Password must be up to 6 characters">
                                </li>
                                <li>
                                    <a href="signup.jsp">Not having an account? Sign Up Here!</a>
                                </li>
                                <li>
                                    <input type="submit" name="submit" value="Login" class="btn3">
                                </li>
                            </ul>
                        </form>
                    </div>
                </div>
            </div>

            <div id="footer">
                <script type="text/javascript" src="imports/import_footer.js"></script>
            </div>
        </body>
            
    <% }
    else {
        response.sendRedirect("account.jsp");
    } %>
    
</html>
