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
import org.vitacare.dto.department.DepartmentDTO;
import org.vitacare.dto.specialityDTO.SpecialityDTO;
import org.vitacare.service.DoctorService;
import org.vitacare.service.DepartmentService;
import org.vitacare.service.SpecialityService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/search-doctors")
public class SearchDoctorServlet extends HttpServlet {

    @Inject
    private DoctorService doctorService;

    @Inject
    private DepartmentService departmentService;

    @Inject
    private SpecialityService specialityService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
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

        // Récupération des paramètres de recherche
        String searchName = req.getParameter("name");
        String specialityFilter = req.getParameter("speciality");
        String departmentFilter = req.getParameter("department");

        // Récupération de tous les médecins actifs
        List<DoctorDTO> doctors = doctorService.getAllDoctors();

        // Filtrage par nom
        if (searchName != null && !searchName.trim().isEmpty()) {
            String search = searchName.toLowerCase();
            doctors = doctors.stream()
                    .filter(d -> (d.getFirstName() + " " + d.getLastName()).toLowerCase().contains(search))
                    .collect(Collectors.toList());
        }

        // Filtrage par spécialité
        if (specialityFilter != null && !specialityFilter.isEmpty()) {
            doctors = doctors.stream()
                    .filter(d -> d.getSpeciality() != null &&
                            d.getSpeciality().equalsIgnoreCase(specialityFilter))
                    .collect(Collectors.toList());
        }

        // Filtrage par département
        if (departmentFilter != null && !departmentFilter.isEmpty()) {
            doctors = doctors.stream()
                    .filter(d -> d.getSpeciality().getDepartmentName() != null &&
                            d.getSpeciality().getDepartmentName().equalsIgnoreCase(departmentFilter))
                    .collect(Collectors.toList());
        }

        // Récupération des listes pour les filtres
        List<DepartmentDTO> departments = departmentService.getAll();
        List<SpecialityDTO> specialities = specialityService.findAllDto();

        req.setAttribute("doctors", doctors);
        req.setAttribute("departments", departments);
        req.setAttribute("specialities", specialities);
        req.setAttribute("selectedName", searchName);
        req.setAttribute("selectedSpeciality", specialityFilter);
        req.setAttribute("selectedDepartment", departmentFilter);

        req.getRequestDispatcher("/WEB-INF/views/patient/search-doctors.jsp").forward(req, resp);
    }
}
