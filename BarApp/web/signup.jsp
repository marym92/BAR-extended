<%-- 
    Document   : signup
    Author     : mary
--%>

<%@page import="gr.unipi.webdev.barapp.control.WS_Users"%>
<%@page import="gr.unipi.webdev.barapp.entities.SignupData"%>
<%@page import="gr.unipi.webdev.barapp.control.WS_Captcha"%>
<%@page import="gr.unipi.webdev.barapp.entities.BARcaptcha"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%!
    private static BARcaptcha c;
%>

<%
    // Page Value -- signup=3
    session.setAttribute("page", 3);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    
    <% if (session.getAttribute("account") == null) { %>
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
            
            // Checks for passwords
            function checkPasswords(id){
                var pass = document.getElementById("password").value;
                var repass = document.getElementById("repassword").value;

                if(repass=='') {
                    return check_empty_fields(id);
                } 
                if(!validRepass()) {
                    alert("Passwords don't match. Please try again!");
                }
            }
            function validRepass(){
                var pass = document.getElementById('password').value;
                var repass = document.getElementById('repassword').value;
                if (pass == repass){
                    return true;
                }
                else{        
                    return false;
                }
            }
            
            // Checks for Captcha code
            function Captcha(){
                <%
                if (!("POST".equalsIgnoreCase(request.getMethod()) && request.getParameter("submit") != null)) {    
                    c = new BARcaptcha();
                    c = WS_Captcha.getCaptcha();
                }
                %>

                document.getElementById("mainCaptcha").style.cssText = "\
                    border: solid 1px #000; \n\
                    margin-bottom: 5px; \n\
                    width: 200px; \n\
                    height: 50px; \n\
                    text-align: center; \n\
                    font-family: Porkys-Regular; \n\
                    font-size: 22pt; \n\
                    background-image: url(./images/bg-captcha.jpg);";

                document.getElementById("mainCaptcha").value = '<%= c.getCaptcha() %>';
            }
            function ValidCaptcha(){
                var string1 = removeSpaces(document.getElementById('mainCaptcha').value);
                var string2 = removeSpaces(document.getElementById('txtInput').value);
                if (string1 == string2){
                    return true;
                }
                else{        
                    return false;
                }
            }
            function removeSpaces(string){
                return string.split(' ').join('');
            }
            function captchaInput(id) {
                var c = document.getElementById("txtInput");

                if(c.value=='') {
                    return check_empty_fields(id);
                }
                if(!ValidCaptcha()) {
                    alert("Captcha is incorrect. Please try again!");
                }
            }
            
            // Checks for birthdate
            function dateInput(){
                var text = document.getElementById("birthdate").value;
                var comp = text.split('/');
                var d = parseInt(comp[0], 10);
                var m = parseInt(comp[1], 10);
                var y = parseInt(comp[2], 10);
                var date = new Date(y,m-1,d);
                if (date.getFullYear() == y && date.getMonth() + 1 == m && date.getDate() == d) {
                    return true;
                } else {
                    return false;
                }
            }
            function dateResult(id) {
                var c = document.getElementById("birthdate");

                if(c.value=='') {
                    return check_empty_fields(id);
                }
                if(!dateInput()) {
                    alert("Birth date is incorrect. Please try again!");
                }
            }
        </script>

        <body onload="Captcha();">

            <jsp:include page="imports/import_menu.jsp"/>

            <div id="contents">
                <script type="text/javascript" src="imports/import_title.js"></script>
            </div>
            <div id="footer">
                <div id="login" class="wrapper clearfix">
                    
                    <%
                    if ("POST".equalsIgnoreCase(request.getMethod()) && request.getParameter("submit") != null) {
                        if (request.getParameter("username")!=null && request.getParameter("password")!=null && request.getParameter("repassword")!=null && request.getParameter("email")!=null && request.getParameter("birthdate")!=null && request.getParameter("txtInput")!=null) {
                            if (!request.getParameter("username").equalsIgnoreCase("Username") && !request.getParameter("password").equalsIgnoreCase("Password") && !request.getParameter("repassword").equalsIgnoreCase("Password") && !request.getParameter("email").equalsIgnoreCase("Email") && !request.getParameter("birthdate").equalsIgnoreCase("dd/mm/yyyy") && !request.getParameter("txtInput").equalsIgnoreCase("")) {
                                SignupData sd = new SignupData();

                                sd.setUsername(request.getParameter("username").toString());
                                sd.setPassword(request.getParameter("password").toString());
                                sd.setPasswordVer(request.getParameter("repassword").toString());
                                sd.setEmail(request.getParameter("email").toString());
                                sd.setBirthdate(request.getParameter("birthdate").toString());
                                sd.setCaptcha(new BARcaptcha(c.getcID(), request.getParameter("txtInput").toString()));

                                /* ----- WS -- Send Signup Data ----- */
                                String result = WS_Users.signup(sd);
                                
                                if (result.equals("")) { %>
                                    <script language="javascript">
                                        alert("There was a problem contacting server. Please try again later!");
                                    </script>
                                <%
                                } else {
                                    if (Integer.parseInt(result) > 0) {
                                        session.setAttribute("userID", Integer.parseInt(result));
                                        response.sendRedirect("signup-bar.jsp");
                                    } 
                                    else if (result.equals("-208")) { %>
                                        <script language="javascript">
                                            alert("There was a problem contacting server. Please try again later!");
                                        </script>
                                    <%
                                    } else if (result.equals("-201")) { %>
                                        <script language="javascript">
                                            alert("The two passwords don't match.\nPlease check your credentials and try again!");
                                        </script>
                                    <%
                                    } else if (result.equals("-202")) { %>
                                        <script language="javascript">
                                            alert("The email address that you provided is incorrect. Please check the email provided and try again!");
                                        </script>
                                    <%
                                    } else if (result.equals("-203") || result.equals("-204")) { %>
                                        <script language="javascript">
                                            alert("Username and/or email already exist. Please check your credentials and try again!");
                                        </script>
                                    <%
                                    } else if (result.equals("-205") || result.equals("-206")) { %>
                                        <script language="javascript">
                                            alert("The birth date you provided is incorrect. Please try again!");
                                        </script>
                                    <%
                                    } else if (result.equals("-207")) { %>
                                        <script language="javascript">
                                            alert("The captcha code you entered is incorrect. Please try again!");
                                        </script>
                                    <% }
                                }
                            }
                            else { %>
                                <script language="javascript">
                                    alert("All info must be provided.\nPlease try again!");
                                </script>
                        <%  }
                        } else { %>
                            <script language="javascript">
                                alert("Both Username and Password must not be Null. \nPlease try again!");
                            </script>
                    <%  }
                    }   
                    %>
                    
                    <div class="main">
                        <h1>Sign Up</h1>

                        <form action="signup.jsp" method="post" id="signup_form">
                            <ul>
                                <li>
                                    <label>Enter your username.</label>
                                    <input type="text" placeholder="Username" name="username" id="username" required onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}" pattern=".{4,}" required title="Username must be up to 4 characters">
                                </li>
                                <li>
                                    <label>Enter your password.</label>
                                    <input type="password" placeholder="Password" name="password" id="password" required onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}" pattern=".{6,}" required title="Password must be up to 6 characters">
                                </li>
                                <li>
                                    <label>Re-enter your password.</label>
                                    <input type="password" placeholder="Password" name="repassword" id="repassword" required onBlur="javascript:checkPasswords(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}" pattern=".{6,}" required title="Password must be up to 6 characters">
                                </li>
                                <li>
                                    <label>Enter your email.</label>
                                    <input type="email" placeholder="Email" name="email" id="email" required onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}">
                                </li>
                                <li>
                                    <label>Enter your date of birth.</label>
                                    <input type="text" placeholder="dd/mm/yyyy" name="birthdate" id="birthdate" required onBlur="javascript:dateResult(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}">
                                </li>
                                <li>
                                    <label>Prove you are human.</label>
                                    <input type="text" readonly id="mainCaptcha" />
                                    <br/>
                                    <input type="text" name="txtInput" id="txtInput" required onBlur="javascript:captchaInput(this.id);"/>
                                </li>
                                <li>
                                    <a href="login.jsp">Already having an account? Login!</a>
                                </li>
                                <li>
                                    <input type="submit" name="submit" value="Sign Up" class="btn3" />
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
