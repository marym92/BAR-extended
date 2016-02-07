<%-- 
    Document   : signup
    Author     : mary
--%>

<%@page import="gr.unipi.webdev.barapp.control.WS_Captcha"%>
<%@page import="gr.unipi.webdev.barapp.entities.BARcaptcha"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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

            function Captcha(){
                <%
                BARcaptcha c = new BARcaptcha();
                c = WS_Captcha.getCaptcha();
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

            function dateInput(){
                var text = document.getElementById("birthday").value;
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
                var c = document.getElementById("birthday");

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
                    <div class="main">
                        <h1>Sign Up</h1>

                        <form action="index12314.html" method="post">
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
                                    <label>Re-enter your password.</label>
                                    <input type="password" value="Password" name="repassword" id="repassword" onBlur="javascript:checkPasswords(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}" pattern=".{6,}" required title="Password must be up to 6 characters">
                                </li>
                                <li>
                                    <label>Enter your email.</label>
                                    <input type="email" value="Email" name="email" id="email" onBlur="javascript:check_empty_fields(this.id)" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}">
                                </li>
                                <li>
                                    <label>Enter your date of birth.</label>
                                    <input type="date" value="dd/mm/yyyy" name="birthday" id="birthday" onBlur="javascript:dateResult()" onFocus="javascript:if(this.value==this.defaultValue){this.value='';}">
                                </li>
                                <li>
                                    <label>Prove you are a human.</label>
                                    <input type="text" readonly id="mainCaptcha" />
                                    <br/>
                                    <input type="text" id="txtInput" onBlur="javascript:captchaInput(this.id);"/>
                                </li>
                                <li>
                                    <a href="login.jsp">Having already an account? Login!</a>
                                </li>
                                <li>
                                    <input type="submit" value="Sign Up" class="btn3">
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
