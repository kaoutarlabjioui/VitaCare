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
import org.vitacare.entity.enums.BloodGroup;
import java.io.IOException;
import java.time.LocalDate;

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

            // Conversion du blood group si présent
            BloodGroup blood = null;
            String bloodParam = req.getParameter("blood");
            if (bloodParam != null && !bloodParam.isEmpty()) {
                blood = BloodGroup.valueOf(bloodParam);
            }

            // Conversion de la date de naissance si présente
            LocalDate birthDate = null;
            String birthDateParam = req.getParameter("birthDate");
            if (birthDateParam != null && !birthDateParam.isEmpty()) {
                birthDate = LocalDate.parse(birthDateParam);
            }

            // Création du DTO complet
            RegisterRequestDTO dto = new RegisterRequestDTO(
                    req.getParameter("email"),
                    req.getParameter("password"),
                    req.getParameter("firstName"),
                    req.getParameter("lastName"),
                    userType,
                    req.getParameter("registration"), // doctor
                    req.getParameter("title"),        // doctor
                    req.getParameter("cin"),          // patient
                    birthDate,                        // patient
                    req.getParameter("sexe"),         // patient
                    req.getParameter("phone"),        // patient
                    req.getParameter("address"),      // patient
                    blood,                            // patient
                    req.getParameter("position"),     // staff
                    specialityId                      // doctor
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
