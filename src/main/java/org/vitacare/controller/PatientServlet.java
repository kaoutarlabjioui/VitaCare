package org.vitacare.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.vitacare.dto.auth.LoginResponseDTO;
import org.vitacare.dto.user.PatientDTO;
import org.vitacare.service.AuthService;
import org.vitacare.service.PatientService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/patients/*")
public class PatientServlet extends HttpServlet {

    @Inject
    private PatientService patientService;
    @Inject
  private   AuthService userService;

    protected void doGet(HttpServletRequest req , HttpServletResponse resp) throws ServletException, IOException{
        String path = req.getPathInfo();

        if(path == null ){
            path ="/";
        }

        switch (path) {
            case "/edit":
                showEditForm(req, resp);
                break;
            case "/delete":
                handelDelete(req,resp);
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

        if (!"PATIENT".equalsIgnoreCase(user.getUserType()) && !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/access-denied.jsp");
            return;
        }

        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/patient/dashboard.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException, ServletException {
        String path = req.getPathInfo();

        if(path == null) {
            path = "/";
        }

        if (path.equals("/update")) {
            handleUpdate(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "action non reconnue : " + path);
        }
    }



    private void showEditForm(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
        Long id = Long.parseLong(req.getParameter("id"));
        Optional<PatientDTO> optionalPatientDto = patientService.getById(id);
        if(optionalPatientDto.isPresent()){
            req.setAttribute("patient",optionalPatientDto.get());
            req.getRequestDispatcher("/WEB-INF/views/patient/edit.jsp").forward(req,resp);
        }else{
            resp.sendRedirect(req.getContextPath()+"/patients");
        }
    }


    private void showList(HttpServletRequest req ,HttpServletResponse resp) throws ServletException,IOException{
        List<PatientDTO> patients = patientService.getAllPatients();
        req.setAttribute("patients" ,patients);
        req.getRequestDispatcher("/WEB-INF/views/patient/list.jsp").forward(req,resp);
    }



    private void handelDelete(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        Long id = Long.parseLong(req.getParameter("id"));
        userService.deactivateUser(id);
        resp.sendRedirect(req.getContextPath() +"/patients");
    }


    private void handleUpdate(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        Long id = Long.parseLong(req.getParameter("id"));
    //todologique
        resp.sendRedirect(req.getContextPath() +"/patients");
    }
}
