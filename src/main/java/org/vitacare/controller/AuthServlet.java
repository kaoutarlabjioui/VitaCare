package org.vitacare.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
//import org.vitacare.config.AppContainer;
import org.vitacare.dto.auth.LoginRequestDTO;
import org.vitacare.dto.auth.LoginResponseDTO;
import org.vitacare.dto.auth.RegisterRequestDTO;
import org.vitacare.service.AuthService;

import java.io.IOException;

@WebServlet(name = "AuthServlet", urlPatterns = {"/auth"})
public class AuthServlet extends HttpServlet {
@Inject
    private AuthService authService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("logout".equalsIgnoreCase(action)) {
            handleLogout(req, resp);
            return;
        }

        if ("register".equalsIgnoreCase(action)) {
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("register".equalsIgnoreCase(action)) {
            handleRegister(req, resp);
        } else if ("login".equalsIgnoreCase(action)) {
            handleLogin(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action non reconnue");
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        LoginRequestDTO dto = new LoginRequestDTO(email, password);
        LoginResponseDTO response = authService.login(dto);

        if (response == null) {
            req.setAttribute("error", "Email ou mot de passe incorrect !");
            req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
            return;
        }


        HttpSession session = req.getSession();
        session.setAttribute("user", response);

        if (response.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            RegisterRequestDTO dto = new RegisterRequestDTO(
                    req.getParameter("email"),
                    req.getParameter("password"),
                    req.getParameter("firstName"),
                    req.getParameter("lastName"),
                    "PATIENT", // auto-inscription = patient
                    null, null,
                    req.getParameter("cin"),
                    req.getParameter("phone"),
                    req.getParameter("address"),
                    null, null, null
            );

            authService.register(dto);
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");

        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false); // false = ne pas créer de nouvelle session
        if (session != null) {
            session.invalidate(); // supprime la session existante
        }


        resp.sendRedirect(req.getContextPath() + "/");
    }
}
