<%-- 
    Document   : signup-bar
    Author     : mary
--%>

<%@page import="gr.unipi.webdev.barapp.control.DataControl"%>
<%@page import="gr.unipi.webdev.barapp.control.WS_ActiveUsers"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gr.unipi.webdev.barapp.entities.BARactiveUsers"%>
<%@page import="javax.xml.bind.DatatypeConverter"%>
<%@page import="gr.unipi.webdev.barapp.control.UserControl"%>
<%@page import="java.security.PublicKey"%>
<%@page import="gr.unipi.webdev.barapp.security.RSAkeys"%>
<%@page import="gr.unipi.webdev.barapp.control.WS_Users"%>
<%@page import="gr.unipi.webdev.barapp.entities.BarSignupData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Page Value -- signup-bar=3
    session.setAttribute("page", 3);
%>
<%! 
    private static int onionNodes = 3;
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
                        if (request.getParameter("pseudonym")!=null) {
                            if (!request.getParameter("pseudonym").equalsIgnoreCase("Pseudonym")) {
                                BarSignupData bsd = new BarSignupData();
                                
                                String encCoordiKey = "";
                                
                                /* ----- (a) WS -- Get Coordi Key ----- */
                                encCoordiKey = WS_Users.getCoordiKey();
                                if (encCoordiKey.equals("")) { %>
                                    <script language="javascript">
                                        alert("There was a problem contacting server. Please try again later!");
                                    </script>
                                <%
                                } else {
                                    bsd.setUserID(session.getAttribute("userID").toString());
                                    bsd.setPseudonym(request.getParameter("pseudonym").toString());

                                    /* ----- (b) Create a pair of keys ----- */
                                    RSAkeys.createKeys(request.getParameter("pseudonym"));

                                    PublicKey pubKey = UserControl.getPK(false);
                                    bsd.setPk(DatatypeConverter.printBase64Binary(pubKey.getEncoded()));

                                    /* ----- (c) WS -- Get Active Users ----- */
                                    ArrayList<BARactiveUsers> activeUsers = WS_ActiveUsers.getActiveUsers();

                                    /* ----- (d) Check if activeUsers>3 to set onion path routing ----- */
                                    if (activeUsers.size() < onionNodes) { %>
                                        <script language="javascript">
                                            alert("BAR Service is not available due to lack of active users. Please try again later!");
                                        </script>
                                    <%
                                    }
                                    else {
                                        /* ----- (e) Choose randomly 3 active users ----- */
                                        ArrayList<BARactiveUsers> rndAU = WS_ActiveUsers.getRndActiveUsers(activeUsers);
                                        
                                        /* ----- (f) Encrypt data with Coordi PK ----- */
                                        String encData = DataControl.encryptDataToCoordi(encCoordiKey, bsd);
                                        if (encData.equals("")) { %>
                                            <script language="javascript">
                                                alert("There was a problem contacting server. Please try again later!");
                                            </script>
                                        <%
                                        } else {
                                            /* ----- (g) Encrypt data with each of the 3 random users' PKs ----- */
                                            encData = DataControl.encryptDataToAU(rndAU, encData);
                                                
                                            if (encData.equals("")) { %>
                                                <script language="javascript">
                                                    alert("There was a problem contacting server. Please try again later!");
                                                </script>
                                            <%
                                            } else {
                                                /* ----- (h) Onion path routing to send enc{BarSignupData} ----- */
                                            // CHANGE STRING RESULT TO CALL signup    
                                                String result="";

                                                if (result.equals("")) { %>
                                                    <script language="javascript">
                                                        alert("There was a problem contacting server. Please try again later!");
                                                    </script>
                                                <%
                                                } else {
                                                    if (encCoordiKey.equals("-501")) { %>
                                                        <script language="javascript">
                                                            alert("Given pseudonym already exists. Please choose another pseudonym and try again!");
                                                        </script>
                                                    <%
                                                    } else if (encCoordiKey.equals("-502")) { %>
                                                        <script language="javascript">
                                                            alert("You are already a registered user in the BAR protocol.");
                                                        </script>
                                                    <%
                                                    } else if (encCoordiKey.equals("-503")) { %>
                                                        <script language="javascript">
                                                            alert("Something went wrong during Register process. Please try again!");
                                                        </script>
                                                    <%
                                                    } else if (encCoordiKey.equals("-504")) { %>
                                                        <script language="javascript">
                                                            alert("There was a problem contacting server. Please try again later!");
                                                        </script>
                                                    <%
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            else { %>
                                <script language="javascript">
                                    alert("Pseudonym must be provided.\nPlease try again!");
                                </script>
                        <%  }
                        } else { %>
                            <script language="javascript">
                                alert("Pseudonym must not be Null. \nPlease try again!");
                            </script>
                    <%  }
                    }   
                    %>
                    
                    <div class="main">
                        <h1>Sign Up</h1>

                        <form action="signup-add.jsp" method="post" id="signup-add_form">
                            <ul>
                                <li>
                                    <label>Enter the pseudonym for the BAR network.</label>
                                    <input type="text" placeholder="Pseudonym" name="pseudonym" id="pseudonym" required onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}" pattern=".{4,}" required title="Pseudonym must be up to 4 characters">
                                </li>
                                <li>
                                    <input type="submit" name="submit" value="Submit" class="btn3" />
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
        response.sendRedirect("services.jsp");
    } %>
</html>
