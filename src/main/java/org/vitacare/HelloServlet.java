package org.vitacare;

import java.io.*;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "sdfghjk World!";
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            EntityManagerFactory emf =
                    Persistence.createEntityManagerFactory("VitaCarePU");
            emf.createEntityManager().createNativeQuery("SELECT 1").getSingleResult();
            resp.getWriter().println("✅ Database connection OK!");
        } catch (Exception e) {
            e.printStackTrace(resp.getWriter());
        }
    }

    public void destroy() {
    }
}