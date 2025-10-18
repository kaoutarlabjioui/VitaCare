package org.vitacare.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.vitacare.entity.Doctor;
import org.vitacare.entity.Patient;
import org.vitacare.entity.Staff;
import org.vitacare.service.AuthService;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminManageUsersServlet extends HttpServlet {

    @Inject
    private AuthService authService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String type = req.getParameter("type");
        if (type == null) type = "doctors";

        switch (type) {
            case "patients" -> {
                List<Patient> patients = authService.getAllPatients();
                req.setAttribute("users", patients);
                req.setAttribute("title", "Liste des Patients");
                req.setAttribute("type", "patients");
            }
            case "staff" -> {
                List<Staff> staffs = authService.getAllStaff();
                req.setAttribute("users", staffs);
                req.setAttribute("title", "Liste du Staff");
                req.setAttribute("type", "staff");
            }
            default -> {
                List<Doctor> doctors = authService.getAllDoctors();
                req.setAttribute("users", doctors);
                req.setAttribute("title", "Liste des Docteurs");
                req.setAttribute("type", "doctors");
            }
        }

        req.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        String type = req.getParameter("type");

        if ("deactivate".equalsIgnoreCase(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            authService.deactivateUser(id);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/dashboard?type=" + type);
    }
}
