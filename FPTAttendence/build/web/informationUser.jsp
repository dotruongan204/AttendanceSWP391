<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="Dto.Dto" %>
<!DOCTYPE html>
<html>
    <head>
        <style>
            body {
                margin: 0;
                padding: 0;
                min-height: 100vh;
                display: flex;
                flex-direction: column;
            }

            main {
                flex: 1;
            }

            footer {
                background-color: #f8f9fa;
                text-align: center;
                padding: 1rem;
                .btn-primary {
                    background-color: #59e720;
                    border-color: #59e720;
                }

                .btn-primary:hover {
                    background-color: #59e720;
                    border-color: #59e720;
                }
            }
        </style>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="css/main.css" rel="stylesheet">
        <title>Your Website</title>
    </head>
    <body>
        <header>
            <div class="container-fluid">
                <div class="row">
                    <!-- <div class="col-auto"> -->
                    <img src="y-nghia-logo-fpt-lan-3.jpg" alt="Company Logo">
                    <!-- </div> -->
                    <!-- <div class="col text-end"> -->
                    <div class="user-info">
                        <p>thuy vy</p>
                        <img src="avatar.jpg" alt="Avatar">
                        <a href="#">Logout</a>
                    </div>
                    <!-- </div> -->
                </div>
            </div>
        </header>

        <div class="container-fluid mt-3">
            <div class="row">
                <aside class="col-lg-2 col-md-3">
                    <ul class="nav flex-column">
                        <li class="nav-item"><a id="attendance" class="nav-link" href="#">Điểm danh</a></li>
                        <li class="nav-item"><a id="profile" class="nav-link" href="#">Thông tin cá nhân</a></li>
                        <li class="nav-item"><a id="application"class="nav-link" href="#">Viết đơn</a></li>
                        <li class="nav-item"><a id="showapplication"class="nav-link" href="#">Xem các đơn đã gửi</a></li>
                        <li class="nav-item"><a id="Nofitication"class="nav-link" href="#">Thông báo</a></li>
                    </ul>
                </aside>

                <main class="col-lg-10 col-md-9" >

                    <%
                   Dto acc = (Dto) session.getAttribute("account");
                    %>
                    <c:set var="em" value="${requestScope.emp}" />
                    <section class="profile" id="profile-content">
                        <h1>Hồ sơ cá nhân</h1>
                        <div class="profile-container">
                            <div class="profile-image">
                                <img src="avatar.jpg" alt="Profile Picture">
                            </div>
                            <table class="table" border="1">
                                <tbody>

                                    <tr>
                                        <td>ID</td>
                                        <td>${em.empId}</td>
                                    </tr>
                                    <tr>
                                        <td>Full Name</td>
                                        <td>${em.empName}</td>
                                    </tr>
                                    <tr>
                                        <td>Phone</td>
                                        <td>${em.empNumber}</td>
                                    </tr>
                                    <tr>
                                        <td>Address</td>
                                        <td>${em.empAddress}</td>
                                    </tr>
                                    <tr>
                                        <td>Email</td>
                                        <td>${em.empEmail}</td>
                                    </tr>
                                    <tr>
                                        <td>Ngày sinh</td>
                                        <td>${em.empBirthdate}</td>
                                    </tr>
                                    <tr>
                                        <td>Hire Date</td>
                                        <td>${em.empHiredate}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </section>
                    <form action="HomeEmployees" method="post">
                        <%
                          int accid = acc.getUserID();// Lấy giá trị accid từ dữ liệu đăng nhập, chẳng hạn từ database
                        %>
                        <input type="hidden" name="idse" value="<%= accid %>">
                        <input class="btn btn-primary" type="submit" value="Update Information" name="btAction"></form>
                        <% 
            // Hiển thị thông báo lỗi nếu tồn tại
            String errorMessage = (String) request.getAttribute("ms");
            if (errorMessage != null) {
                        %>
                    <p class="er" style="color: red;"><%= errorMessage %></p>
                    <%
                        }
                    %>
                </main>
            </div>
        </div>

        <footer class="bg-light text-center p-3">
            <p>&copy; 2024 Your Company</p>
        </footer>

        <!-- Bootstrap JS và Popper.js (cần thiết cho một số tính năng của Bootstrap) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="js/js1.js"></script>
    </body>

</body>
</html>
