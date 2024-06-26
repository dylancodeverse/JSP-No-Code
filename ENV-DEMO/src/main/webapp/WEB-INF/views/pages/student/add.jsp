<%@ page import="java.util.List" %>

<%@ page import="scaffold.framework.demo.entity.Student" %>

<%@ page import="scaffold.framework.demo.entity.Promotion" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../../templates/header.jsp">
    <jsp:param name="title" value="Add student"/>
</jsp:include>
<body>
    <div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed">
        <%@ include file="../../templates/nav.jsp" %>
        <div class="body-wrapper">
            <%@ include file="../../templates/app-header.jsp" %>
            <div class="container-fluid">
                <div class="container-fluid">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title fw-semibold mb-4">Add New Student</h5>
                            <form action="/students/add" method="post">                

                                <div class="mb-3">
                                    <label for="name" class="form-label">Name:</label>
                                    <input type="text" id="Name" name="Name" class="form-control" required  />
                                    <div class="form-text">For errors for example.</div>
                                </div>        
                                <div class="mb-3">
                                    <label for="name" class="form-label">First_name:</label>
                                    <input type="text" id="First_name" name="First_name" class="form-control" required  />
                                    <div class="form-text">For errors for example.</div>
                                </div>        
                                <div class="mb-3">
                                    <label for="name" class="form-label">Birthdate:</label>
                                    <input type="Date" id="Birthdate" name="Birthdate" class="form-control" required  />
                                    <div class="form-text">For errors for example.</div>
                                </div>        

                                <div class="mb-3">
                                    <label for="promotionId" class="form-label">Promotion:</label>
                                    <select id="promotionId" class="form-select" name="promotionId">
                                        <% List<Promotion> promotions = (List<Promotion>) request.getAttribute("promotions"); %>
                                            <option value="-1">Choose</option>
                                        <% for(int i = 0; i < promotions.size(); i++) { %>
                                            <option value="<%= promotions.get(i).getId() %>"><%= promotions.get(i).toString() %></option>
                                        <% } %>
                                    </select>
                                </div>        

                                <button type="submit" class="btn btn-primary">Add Student</button>
                            </form>
                            <a href="/students/list" class="mt-5">Back to List</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="../../templates/main-footer.jsp" %>
    <%@ include file="../../templates/page-footer.jsp" %>
</body>
</html>

