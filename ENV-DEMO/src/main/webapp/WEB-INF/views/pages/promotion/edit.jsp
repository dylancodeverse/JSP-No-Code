<%@ page import="scaffold.framework.demo.entity.Promotion" %>


        <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <html>
        <jsp:include page="../../templates/header.jsp">
            <jsp:param name="title" value="Edit promotion"/>
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
                                    <h5 class="card-title fw-semibold mb-4">Edit Promotion</h5>
                                    <% Promotion promotion = (Promotion) request.getAttribute("promotion"); %>
                                    <form action="/promotions/edit" method="post">
                                        <input type="hidden" name="id" value="<%= promotion.getId() %>">

                                <div class="mb-3">
                                    <label for="name" class="form-label">Name:</label>
                                    <input type="text" id="Name" name="Name" class="form-control" value="<%= promotion.getName() %>"/>
                                    <div class="form-text">For errors for example.</div>
                                </div>                
                                <div class="mb-3">
                                    <label for="name" class="form-label">Year:</label>
                                    <input type="number" id="Year" name="Year" class="form-control" value="<%= promotion.getYear() %>"/>
                                    <div class="form-text">For errors for example.</div>
                                </div>                


                                <button type="submit" class="btn btn-primary">Edit Promotion</button>
                            </form>
                            <a href="/promotions/list" class="mt-5">Back to List</a>
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

