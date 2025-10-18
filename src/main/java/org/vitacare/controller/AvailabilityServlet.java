package org.vitacare.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.vitacare.dto.auth.LoginResponseDTO;
import org.vitacare.dto.availability.AvailabilityDTO;
import org.vitacare.entity.enums.AvailabilityStatus;
import org.vitacare.service.AvailabilityService;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/availabilities/*")
public class AvailabilityServlet extends HttpServlet {

    @Inject
    private AvailabilityService availabilityService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null){
            path = "/";
        }

        switch (path) {
            case "/create" :
                showCreateForm(req, resp);
                break;
            case "/edit" :
                handleEdit(req, resp);
                break;
            case "/delete" :
                handleDelete(req, resp);
                break;
            case "/" :
                handleList(req, resp);
                break;
            default :
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Action non reconnue : " + path);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null){
            path = "/";
        }

        switch (path) {
            case "/create" :
                handleCreate(req, resp);
                break;
            case "/update" :
                handleUpdate(req, resp);
                break;
            default :
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Action non reconnue : " + path);
        }
    }



    private void handleList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        LoginResponseDTO user = (LoginResponseDTO) session.getAttribute("user");

        List<AvailabilityDTO> availabilities = availabilityService.getDoctorAvailabilities(user.getId());
        req.setAttribute("availabilities", availabilities);
        req.getRequestDispatcher("/WEB-INF/views/doctor/dashboard.jsp").forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/doctor/dashboard.jsp").forward(req, resp);
    }

    private void handleCreate(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LoginResponseDTO user = (LoginResponseDTO) req.getSession().getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");
            return;
        }

        try {
            DayOfWeek day = DayOfWeek.valueOf(req.getParameter("day").toUpperCase());
            LocalTime startTime = LocalTime.parse(req.getParameter("startTime"));
            LocalTime endTime = LocalTime.parse(req.getParameter("endTime"));

            AvailabilityDTO dto = new AvailabilityDTO();
            dto.setDay(day);
            dto.setStartTime(startTime);
            dto.setEndTime(endTime);
            dto.setStatus(AvailabilityStatus.AVAILABLE);

            // ✅ on envoie juste userId — plus besoin de créer un Doctor à la main
            availabilityService.createAvailability(dto, user.getId());

            resp.sendRedirect(req.getContextPath() + "/availabilities");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            showCreateForm(req, resp);
        }
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        AvailabilityDTO dto = availabilityService.getAvailabilityById(id);
        req.setAttribute("availability", dto);
        req.getRequestDispatcher("/WEB-INF/views/doctor/editAvailability.jsp").forward(req, resp);
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LoginResponseDTO user = (LoginResponseDTO) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");
            return;
        }

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            DayOfWeek day = DayOfWeek.valueOf(req.getParameter("day").toUpperCase());
            LocalTime startTime = LocalTime.parse(req.getParameter("startTime"));
            LocalTime endTime = LocalTime.parse(req.getParameter("endTime"));

            AvailabilityDTO dto = new AvailabilityDTO();
            dto.setId(id);
            dto.setDay(day);
            dto.setStartTime(startTime);
            dto.setEndTime(endTime);
            dto.setStatus(AvailabilityStatus.AVAILABLE);

            availabilityService.updateAvailability(dto, user.getId());

            resp.sendRedirect(req.getContextPath() + "/availabilities");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            handleList(req, resp);
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            availabilityService.markAsUnavailable(id);
            resp.sendRedirect(req.getContextPath() + "/availabilities");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erreur lors de la suppression");
        }
    }
}
