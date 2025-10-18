package org.vitacare.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.vitacare.dto.auth.LoginResponseDTO;
import org.vitacare.dto.user.DoctorDTO;
import org.vitacare.service.AuthService;
import org.vitacare.service.DoctorService;
import org.vitacare.service.AvailabilityService;

import org.vitacare.dto.availability.AvailabilityDTO;
import org.vitacare.dto.appointment.AppointmentDTO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/doctors/*")
public class DoctorServlet extends HttpServlet {

    @Inject
    private DoctorService doctorService;

    @Inject
    private AuthService userService;

    @Inject
    private AvailabilityService availabilityService;



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path == null) {
            path = "/";
        }

        switch (path) {
            case "/edit":
                showEditForm(req, resp);
                break;
            case "/delete":
                handleDelete(req, resp);
                break;
            case "/dashboard":
                showDashboard(req, resp);
                break;
            case "/":
                showList(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showDashboard(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        LoginResponseDTO user = (LoginResponseDTO) session.getAttribute("user");

        if (!"DOCTOR".equalsIgnoreCase(user.getUserType()) && !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/access-denied.jsp");
            return;
        }


        List<AvailabilityDTO> availabilities = availabilityService.getDoctorAvailabilities(user.getId());

        //   les rendez-vous du docteur
      //  List<AppointmentDTO> appointments = appointmentService.getDoctorUpcomingAppointments(user.getId());

      
        req.setAttribute("user", user);
        req.setAttribute("availabilities", availabilities);
       // req.setAttribute("appointments", appointments);

        req.getRequestDispatcher("/WEB-INF/views/doctor/dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String path = req.getPathInfo();

        if (path == null) {
            path = "/";
        }

        if (path.equals("/update")) {
            handleUpdate(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Action non reconnue : " + path);
        }
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Optional<DoctorDTO> optionalDoctorDto = doctorService.getById(id);
        if (optionalDoctorDto.isPresent()) {
            req.setAttribute("doctor", optionalDoctorDto.get());
            req.getRequestDispatcher("/WEB-INF/views/doctor/edit.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/doctors");
        }
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<DoctorDTO> doctors = doctorService.getAllDoctors();
        req.setAttribute("doctors", doctors);
        req.getRequestDispatcher("/WEB-INF/views/doctor/list.jsp").forward(req, resp);
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        userService.deactivateUser(id);
        resp.sendRedirect(req.getContextPath() + "/doctors");
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        // TODO: logique de mise à jour du docteur
        resp.sendRedirect(req.getContextPath() + "/staffs");
    }
}
