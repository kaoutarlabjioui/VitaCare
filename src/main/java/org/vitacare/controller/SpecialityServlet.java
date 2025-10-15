package org.vitacare.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.vitacare.dto.department.DepartmentDTO;
import org.vitacare.dto.specialityDTO.SpecialityDTO;
import org.vitacare.entity.Department;
import org.vitacare.service.SpecialityService;
import org.vitacare.service.DepartmentService; // ✅ à importer

import java.io.IOException;
import java.util.List;

@WebServlet("/speciality/*")
public class SpecialityServlet extends HttpServlet {

    @Inject
    private SpecialityService specialityService;

    @Inject
    private DepartmentService departmentService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                handleShowCreateForm(req, resp);
                break;
            case "edit":
                handleShowEditForm(req, resp);
                break;
            case "delete":
                handleDelete(req, resp);
                break;
            case "list":
            default:
                handleList(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        System.out.println("✅ POST reçu : action=" + req.getParameter("action"));
        if (action == null) action = "create";

        switch (action) {
            case "create":
                handleCreate(req, resp);
                break;
            case "update":
                handleUpdate(req, resp);
                break;
            default:
                handleList(req, resp);
                break;
        }
    }

    // =====================
    // HANDLERS
    // =====================

    private void handleList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<SpecialityDTO> specialities = specialityService.findAllDto();
        req.setAttribute("specialities", specialities);
        req.getRequestDispatcher("/WEB-INF/views/admin/speciality/list.jsp").forward(req, resp);
    }

    private void handleShowCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<DepartmentDTO> departments = departmentService.getAll();
        req.setAttribute("departments", departments);

        req.getRequestDispatcher("/WEB-INF/views/admin/speciality/add.jsp").forward(req, resp);
    }

    private void handleCreate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SpecialityDTO dto = extractDTOFromRequest(req);
        System.out.println("=== DEBUG CREATE SPECIALITY ===");
        System.out.println("Nom : " + dto.getName());
        System.out.println("Code : " + dto.getCode());
        System.out.println("Description : " + dto.getDescription());
        System.out.println("Département : " + dto.getDepartmentName());

        specialityService.create(dto);
        resp.sendRedirect(req.getContextPath() + "/speciality");
    }

    private void handleShowEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        SpecialityDTO speciality = specialityService.findByIdDto(id);

        // 🔹 On envoie aussi la liste des départements au formulaire d’édition
        List<DepartmentDTO> departments = departmentService.getAll();
        req.setAttribute("departments", departments);
        req.setAttribute("speciality", speciality);

        req.getRequestDispatcher("/WEB-INF/views/admin/speciality/edit.jsp").forward(req, resp);
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        SpecialityDTO dto = extractDTOFromRequest(req);
        specialityService.update(id, dto);
        resp.sendRedirect(req.getContextPath() + "/speciality");
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        specialityService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/speciality");
    }

    private SpecialityDTO extractDTOFromRequest(HttpServletRequest req) {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String code = req.getParameter("code");
        String departmentName = req.getParameter("departmentName"); // récupéré depuis le <select>
        return new SpecialityDTO(null,code, description, name, departmentName);
    }
}
