package org.vitacare.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.vitacare.dto.department.DepartmentDTO;
import org.vitacare.service.DepartmentService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/departments/*")
public class DepartmentServlet extends HttpServlet {
    @Inject
    private DepartmentService departmentService;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        if(path == null) {
            path = "/";
        }

        switch(path) {
            case "/create":
                showCreateForm(req, resp);
                break;
            case "/edit":
                showEditForm(req,resp);
                break;
            case "/delete":
                handelDelete(req,resp);
                break;
            case "/":
                showList(req,resp);
                break;
        }
    }

    protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException, ServletException {
        String path = req.getPathInfo();

        if(path == null) {
            path = "/";
        }

        switch(path){
            case "/create":
                handleCreate(req,resp);
                break;
            case "/update":
                handleUpdate(req,resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,"action non reconnue : " +path);
                break;
        }
    }

    private void showList(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
        String search = req.getParameter("search");
        List<DepartmentDTO> departments;

        if(search !=null && !search.isEmpty()){
            departments = departmentService.searchByName(search);
        }else{
            departments = departmentService.getAll();
        }

        req.setAttribute("departments", departments);
        req.getRequestDispatcher("/WEB-INF/views/admin/departments/list.jsp").forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/admin/departments/form.jsp").forward(req, resp);
    }

    private void handleCreate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String description = req.getParameter("description");

        DepartmentDTO departmentDto = new DepartmentDTO();
        departmentDto.setName(name);
        departmentDto.setCode(code);
        departmentDto.setDescription(description);

        departmentService.save(departmentDto);

        resp.sendRedirect(req.getContextPath()+"/departments");
    }

    private void showEditForm(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
        Long id = Long.parseLong(req.getParameter("id"));
        Optional<DepartmentDTO> optionalDepartmentDto = departmentService.getById(id);
        if(optionalDepartmentDto.isPresent()){
            req.setAttribute("department",optionalDepartmentDto.get());
            req.getRequestDispatcher("/WEB-INF/views/admin/departments/edit.jsp").forward(req,resp);
        }else{
            resp.sendRedirect(req.getContextPath()+"/departments");
        }
    }

    private void handleUpdate(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        Long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String code = req.getParameter("code");

        DepartmentDTO departmentDto = new DepartmentDTO();
        departmentDto.setId(id);
        departmentDto.setCode(code);
        departmentDto.setDescription(description);
        departmentDto.setName(name);
        departmentService.save(departmentDto);

        resp.sendRedirect(req.getContextPath()+"/departments");
    }

    private void handelDelete(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        Long id = Long.parseLong(req.getParameter("id"));
        departmentService.delete(id);
        resp.sendRedirect(req.getContextPath() +"/departments");
    }
}
