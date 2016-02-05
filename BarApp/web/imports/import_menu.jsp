<%@page import="java.sql.ResultSet"%>
<%@page import="gr.unipi.webdev.barapp.db.DBmenu"%>

<div id="header">
            
    <%
    DBmenu.dbConnect();

    if (session.getAttribute("menuAdded") == null) {
        DBmenu.dbMenuInsert();
    }

    ResultSet menuRs = null;
    if (session.getAttribute("account") == null){
        menuRs = DBmenu.dbSelect(false);
    } else {
        menuRs = DBmenu.dbSelect(true);
    }
    %>

    <div class="wrapper clearfix">
        <div id="logo">
            <a href="./index.jsp"><img src="images/logo.png" alt="LOGO"></a>
        </div>
        <ul id="navigation">
            <%
            while (menuRs.next()) { 
                if ((Integer) session.getAttribute("page") == menuRs.getInt("position")) {
            %>
                    <li class="selected">
                        <a href="<%= menuRs.getString("link") %>"> <%= menuRs.getString("entry") %> </a>
                    </li>
                <% 
                } else { %>
                <li>
                    <a href="<%= menuRs.getString("link") %>"> <%= menuRs.getString("entry") %> </a>
                </li>
            <%
                }
            } %>
        </ul>
    </div>
        
    <% 
    DBmenu.dbClose();
    %>
    
</div>