/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AttendanceDAO;
import dal.EmployeeDAO;
import dal.RemaindayDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.sql.SQLException;
import models.AccountDTO;
import models.Employee;

/**
 *
 * @author ThuyVy
 */
@WebServlet(name = "EmployeesController", urlPatterns = {"/HomeEmployees"})
public class EmployeesController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            AccountDTO acc = (AccountDTO) session.getAttribute("account");
            EmployeeDAO dao = new EmployeeDAO();
            RemaindayDAO DAO = new RemaindayDAO();
            try {
                Employee em = dao.getin4(acc.getUserID());
                int remainDay = DAO.getRemainDayById(em.employee_id);
                request.setAttribute("re", remainDay);
                request.setAttribute("emp", em);
            } catch (Exception e) {
                System.out.println(e);
            }

            if (acc == null) {
                response.sendRedirect("Login");
            } else {
                request.getRequestDispatcher("HomeEmployees.jsp").forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("checkedInTime") != null) {
            // Nếu đã chấm công vào, chuyển hướng đến trang thông báo
            response.sendRedirect("employee");
        } else {
            if (session != null) {
                AccountDTO acc = (AccountDTO) session.getAttribute("account");
                if (acc != null) {
                    try {
                        EmployeeDAO dao = new EmployeeDAO();
                        RemaindayDAO DAO = new RemaindayDAO();
                        Employee em = dao.getin4(acc.getUserID());
                        int remainDay = DAO.getRemainDayById(em.employee_id);
                        request.setAttribute("re", remainDay);
                        request.setAttribute("emp", em);
                        if (em != null) {
                            int em_id = em.getEmployee_id();
                            int dep_id = dao.GetDepIDfromEmployee(em_id);
                            int remain_id = dao.GetRemainIDfromEmployee(em_id);
                            String img = em.getImage();
                            String notes = request.getParameter("message");
                            AttendanceDAO attendanceDAO = new AttendanceDAO();
                            int attendanceId = attendanceDAO.CheckIn(em_id, notes, img, remain_id, dep_id);
                            System.out.println("con so bi mat la " + attendanceId);
                            if (attendanceId != -1) {
                                Timestamp checkInTime = new Timestamp(System.currentTimeMillis());
                                session.setAttribute("checkInTime", checkInTime);
                                session.setAttribute("attendanceId", attendanceId);
                                request.getRequestDispatcher("CheckOut.jsp").forward(request, response);

                                return;
                            }
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        System.out.println(ex);
                    }
                }
                if (acc == null) {
                    response.sendRedirect("Login");
                } else {

//         Nếu không có session hoặc không có account, chuyển hướng đến trang lỗi hoặc xử lý khác
                    response.sendRedirect("error.jsp");
                }
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
