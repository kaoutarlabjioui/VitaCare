package org.vitacare.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.vitacare.dto.auth.RegisterRequestDTO;
import org.vitacare.service.AuthService;
import org.vitacare.service.DepartmentService;
import org.vitacare.service.SpecialityService;

import java.io.IOException;

@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {

    @Inject
    private AuthService authService;

    @Inject
    private DepartmentService departmentService;

    @Inject
    private SpecialityService specialityService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("departments", departmentService.getAll());
        req.setAttribute("specialities", specialityService.findAllDto());
        req.getRequestDispatcher("/WEB-INF/views/admin/createUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String userType = req.getParameter("userType");

            Long specialityId = req.getParameter("specialityId") != null && !req.getParameter("specialityId").isEmpty()
                    ? Long.parseLong(req.getParameter("specialityId"))
                    : null;

            RegisterRequestDTO dto = new RegisterRequestDTO(
                    req.getParameter("email"),
                    req.getParameter("password"),
                    req.getParameter("firstName"),
                    req.getParameter("lastName"),
                    userType,
                    req.getParameter("title"),
                    req.getParameter("registration"),
                    req.getParameter("cin"),
                    req.getParameter("phone"),
                    req.getParameter("address"),
                    req.getParameter("position"),
                    specialityId
            );


            authService.register(dto);

            resp.sendRedirect(req.getContextPath() + "/admin/dashboard?success=User created successfully");

        } catch (Exception e) {
            req.setAttribute("departments", departmentService.getAll());
            req.setAttribute("specialities", specialityService.findAllDto());
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/admin/createUser.jsp").forward(req, resp);
        }
    }
}
