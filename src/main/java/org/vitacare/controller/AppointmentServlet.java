package org.vitacare.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.vitacare.dto.auth.LoginResponseDTO;
import org.vitacare.dto.appointment.AppointmentDTO;
import org.vitacare.dto.user.DoctorDTO;
import org.vitacare.dto.availability.AvailabilityDTO;
import org.vitacare.entity.enums.AppointmentStatus;
import org.vitacare.service.AppointmentService;
import org.vitacare.service.DoctorService;
import org.vitacare.service.AvailabilityService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@WebServlet("/appointments/*")
public class AppointmentServlet extends HttpServlet {

    @Inject
    private AppointmentService appointmentService;

    @Inject
    private DoctorService doctorService;

    @Inject
    private AvailabilityService availabilityService;


    private static final int MIN_LEAD_TIME_HOURS = 2;
    private static final int MAX_CANCELLATION_HOURS = 12;
    private static final int BUFFER_MINUTES = 5;
    private static final LocalTime LUNCH_START = LocalTime.of(12, 0);
    private static final LocalTime LUNCH_END = LocalTime.of(13, 0);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path == null) {
            path = "/";
        }

        switch (path) {
            case "/book":
                showBookingForm(req, resp);
                break;
            case "/available-slots":
                showAvailableSlots(req, resp);
                break;
            case "/cancel":
                handleCancel(req, resp);
                break;
            case "/history":
                showHistory(req, resp);
                break;
            case "/details":
                showDetails(req, resp);
                break;
            case "/":
                showMyAppointments(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String path = req.getPathInfo();

        if (path == null) {
            path = "/";
        }

        if (path.equals("/book")) {
            handleBooking(req, resp);
        } else if (path.equals("/filter-history")) {
            handleFilterHistory(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Action non reconnue : " + path);
        }
    }

    private void showBookingForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");
            return;
        }

        LoginResponseDTO user = (LoginResponseDTO) session.getAttribute("user");

        if (!"PATIENT".equalsIgnoreCase(user.getUserType()) && !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/access-denied.jsp");
            return;
        }

        String doctorIdStr = req.getParameter("doctorId");
        if (doctorIdStr == null || doctorIdStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/search-doctors");
            return;
        }

        Long doctorId = Long.parseLong(doctorIdStr);

        Optional<DoctorDTO> doctorOptional = doctorService.getById(doctorId);
        if (!doctorOptional.isPresent()) {
            req.setAttribute("error", "Médecin introuvable");
            resp.sendRedirect(req.getContextPath() + "/search-doctors");
            return;
        }

        req.setAttribute("doctor", doctorOptional.get());
        req.getRequestDispatcher("/WEB-INF/views/patient/book-appointment.jsp").forward(req, resp);
    }

    private void showAvailableSlots(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");
            return;
        }

        try {
            int doctorId = Integer.parseInt(req.getParameter("doctorId"));
            LocalDate selectedDate = LocalDate.parse(req.getParameter("date"));
            int duration = Integer.parseInt(req.getParameter("duration")); // 15 ou 30

            LocalDateTime minDateTime = LocalDateTime.now().plusHours(MIN_LEAD_TIME_HOURS);
            LocalDateTime selectedDateTime = selectedDate.atStartOfDay();

            if (selectedDateTime.isBefore(minDateTime.toLocalDate().atStartOfDay())) {
                req.setAttribute("error", "Vous devez prendre rendez-vous au moins " + MIN_LEAD_TIME_HOURS + " heures à l'avance.");
                showBookingForm(req, resp);
                return;
            }

            List<LocalDateTime> availableSlots = appointmentService.getAvailableSlots(
                    doctorId,
                    selectedDate,
                    duration,
                    BUFFER_MINUTES,
                    LUNCH_START,
                    LUNCH_END,
                    MIN_LEAD_TIME_HOURS
            );

            Optional<DoctorDTO> doctorOptional = doctorService.getById((long) doctorId);

            req.setAttribute("doctor", doctorOptional.get());
            req.setAttribute("selectedDate", selectedDate);
            req.setAttribute("duration", duration);
            req.setAttribute("availableSlots", availableSlots);

            req.getRequestDispatcher("/WEB-INF/views/patient/available-slots.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", "Erreur : " + e.getMessage());
            showBookingForm(req, resp);
        }
    }

    private void handleBooking(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        LoginResponseDTO user = (LoginResponseDTO) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");
            return;
        }

        try {
            int doctorId = Integer.parseInt(req.getParameter("doctorId"));
            LocalDateTime appointmentDateTime = LocalDateTime.parse(req.getParameter("appointmentDateTime"));
            int duration = Integer.parseInt(req.getParameter("duration"));

            LocalDateTime now = LocalDateTime.now();

            long hoursUntilAppointment = ChronoUnit.HOURS.between(now, appointmentDateTime);
            if (hoursUntilAppointment < MIN_LEAD_TIME_HOURS) {
                req.setAttribute("error", "Vous devez prendre rendez-vous au moins " + MIN_LEAD_TIME_HOURS + " heures à l'avance.");
                showBookingForm(req, resp);
                return;
            }

            LocalTime appointmentTime = appointmentDateTime.toLocalTime();
            if (isInLunchBreak(appointmentTime, duration)) {
                req.setAttribute("error", "Les rendez-vous ne sont pas possibles pendant la pause déjeuner (12h00-13h00).");
                showBookingForm(req, resp);
                return;
            }


            if (appointmentService.hasOverlap(doctorId, appointmentDateTime, duration, BUFFER_MINUTES)) {
                req.setAttribute("error", "Ce créneau n'est plus disponible (chevauchement détecté).");
                showBookingForm(req, resp);
                return;
            }


            if (!appointmentService.isDoctorAvailable(doctorId, appointmentDateTime)) {
                req.setAttribute("error", "Le médecin n'est pas disponible à cette date/heure.");
                showBookingForm(req, resp);
                return;
            }


            AppointmentDTO dto = new AppointmentDTO();
            dto.setPatientId(user.getId());
            dto.setDoctorId(doctorId);
            dto.setAppointmentDate(appointmentDateTime);
            dto.setDuration(duration);

            dto.setStatus(AppointmentStatus.PLANNED);


            appointmentService.createAppointment(dto);

            resp.sendRedirect(req.getContextPath() + "/appointments?success=Rendez-vous créé avec succès");

        } catch (Exception e) {
            req.setAttribute("error", "Erreur lors de la création du rendez-vous : " + e.getMessage());
            showBookingForm(req, resp);
        }
    }

    private boolean isInLunchBreak(LocalTime startTime, int durationMinutes) {
        LocalTime endTime = startTime.plusMinutes(durationMinutes);


        return (startTime.isBefore(LUNCH_END) && endTime.isAfter(LUNCH_START));
    }


    private void showMyAppointments(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");
            return;
        }

        LoginResponseDTO user = (LoginResponseDTO) session.getAttribute("user");

        if (!"PATIENT".equalsIgnoreCase(user.getUserType()) && !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/access-denied.jsp");
            return;
        }

        List<AppointmentDTO> upcomingAppointments = appointmentService.getPatientUpcomingAppointments(user.getId());

        req.setAttribute("upcomingAppointments", upcomingAppointments);
        req.setAttribute("user", user);
        req.setAttribute("maxCancellationHours", MAX_CANCELLATION_HOURS);

        req.getRequestDispatcher("/WEB-INF/views/patient/my-appointments.jsp").forward(req, resp);
    }


    private void handleCancel(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        LoginResponseDTO user = (LoginResponseDTO) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");
            return;
        }

        try {
            int appointmentId = Integer.parseInt(req.getParameter("id"));

            Optional<AppointmentDTO> appointmentOptional = appointmentService.getById(appointmentId);

            if (!appointmentOptional.isPresent()) {
                resp.sendRedirect(req.getContextPath() + "/appointments?error=Rendez-vous introuvable");
                return;
            }

            AppointmentDTO appointment = appointmentOptional.get();

            if (appointment.getPatientId()!=(user.getId())) {
                resp.sendRedirect(req.getContextPath() + "/appointments?error=Non autorisé");
                return;
            }


            if (!appointment.getStatus().canBeCancelled()) {
                resp.sendRedirect(req.getContextPath() + "/appointments?error=Ce rendez-vous ne peut plus être annulé");
                return;
            }


            LocalDateTime now = LocalDateTime.now();
            long hoursUntilAppointment = ChronoUnit.HOURS.between(now, appointment.getAppointmentDate());

            if (hoursUntilAppointment < MAX_CANCELLATION_HOURS) {
                resp.sendRedirect(req.getContextPath() +
                        "/appointments?error=Vous ne pouvez annuler qu'au moins " + MAX_CANCELLATION_HOURS + " heures avant le rendez-vous");
                return;
            }


            appointmentService.cancelAppointment(appointmentId);

            resp.sendRedirect(req.getContextPath() + "/appointments?success=Rendez-vous annulé avec succès");

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/appointments?error=" + e.getMessage());
        }
    }


    private void showHistory(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");
            return;
        }

        LoginResponseDTO user = (LoginResponseDTO) session.getAttribute("user");

        if (!"PATIENT".equalsIgnoreCase(user.getUserType()) && !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/404.jsp");
            return;
        }

        List<AppointmentDTO> allAppointments = appointmentService.getPatientAllAppointments(user.getId());

        req.setAttribute("appointments", allAppointments);
        req.setAttribute("user", user);

        req.getRequestDispatcher("/WEB-INF/views/patient/appointment-history.jsp").forward(req, resp);
    }

    private void handleFilterHistory(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        LoginResponseDTO user = (LoginResponseDTO) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");
            return;
        }

        String statusFilter = req.getParameter("status");
        String startDateStr = req.getParameter("startDate");
        String endDateStr = req.getParameter("endDate");

        LocalDate startDate = (startDateStr != null && !startDateStr.isEmpty()) ? LocalDate.parse(startDateStr) : null;
        LocalDate endDate = (endDateStr != null && !endDateStr.isEmpty()) ? LocalDate.parse(endDateStr) : null;
        AppointmentStatus status = (statusFilter != null && !statusFilter.isEmpty()) ? AppointmentStatus.valueOf(statusFilter) : null;

        List<AppointmentDTO> filteredAppointments = appointmentService.getFilteredAppointments(
                user.getId(),
                status,
                startDate,
                endDate
        );

        req.setAttribute("appointments", filteredAppointments);
        req.setAttribute("user", user);
        req.setAttribute("selectedStatus", statusFilter);
        req.setAttribute("startDate", startDateStr);
        req.setAttribute("endDate", endDateStr);

        req.getRequestDispatcher("/WEB-INF/views/patient/appointment-history.jsp").forward(req, resp);
    }


    private void showDetails(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");
            return;
        }

        LoginResponseDTO user = (LoginResponseDTO) session.getAttribute("user");

        int appointmentId = Integer.parseInt(req.getParameter("id"));
        Optional<AppointmentDTO> appointmentOptional = appointmentService.getById(appointmentId);

        if (!appointmentOptional.isPresent()) {
            resp.sendRedirect(req.getContextPath() + "/appointments?error=Rendez-vous introuvable");
            return;
        }

        AppointmentDTO appointment = appointmentOptional.get();

        if (appointment.getPatientId()!=(user.getId()) && !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/appointments?error=Non autorisé");
            return;
        }


        LocalDateTime now = LocalDateTime.now();
        long hoursUntilAppointment = ChronoUnit.HOURS.between(now, appointment.getAppointmentDate());
        boolean canCancel = hoursUntilAppointment >= MAX_CANCELLATION_HOURS &&
                appointment.getStatus() == AppointmentStatus.PLANNED;

        req.setAttribute("appointment", appointment);
        req.setAttribute("user", user);
        req.setAttribute("canCancel", canCancel);
        req.setAttribute("hoursUntilAppointment", hoursUntilAppointment);

        req.getRequestDispatcher("/WEB-INF/views/patient/appointment-details.jsp").forward(req, resp);
    }
}