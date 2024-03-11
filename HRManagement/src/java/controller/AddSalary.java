/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.SalaryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.SQLException;
import java.util.List;
import models.AccountDTO;
import models.Attendance;
import models.Department;
import models.Employee;
import models.EmployeeSalary;
import models.Salary;

/**
 *
 * @author Dan
 */
@WebServlet(name = "AddSalaryController", urlPatterns = {"/AddSalary"})
public class AddSalary extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddSalary</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddSalary at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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

        String username = request.getParameter("username");

        request.setAttribute("username", username);
        request.getRequestDispatcher("AddSalary.jsp").forward(request, response);
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
        String username = request.getParameter("username");
        double basicSalary = Double.parseDouble(request.getParameter("basicSalary"));
        double allowance = Double.parseDouble(request.getParameter("allowance"));
        double tax = Double.parseDouble(request.getParameter("tax"));
        double bonus = Double.parseDouble(request.getParameter("bonus"));

        SalaryDAO salaryDAO = new SalaryDAO();

     
        try {
            EmployeeSalary employeeSalary = salaryDAO.getEmployeeSalaryByUserName1(username);
            salaryDAO.addSalary(new Salary(employeeSalary.getEmployeeId(), employeeSalary.getDepartmentId(),
                    employeeSalary.getAttendanceId(), basicSalary, allowance, tax, bonus,
                    employeeSalary.getHireDate(), employeeSalary.getUserId()));

            response.sendRedirect("ListSalary");
        } catch (Exception exception) {

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
