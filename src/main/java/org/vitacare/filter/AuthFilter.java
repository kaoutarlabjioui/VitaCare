package org.vitacare.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();

        // ✅ Ressources publiques
        boolean isPublic = uri.startsWith(contextPath + "/auth")
                || uri.endsWith(".css")
                || uri.endsWith(".js")
                || uri.endsWith(".png")
                || uri.endsWith(".jpg")
                || uri.endsWith(".jpeg")
                || uri.endsWith(".gif")
                || uri.equals(contextPath + "/")
                || uri.equals(contextPath + "/index.jsp");

        if (isPublic) {
            chain.doFilter(request, response); // laisse passer
        } else {
            resp.sendRedirect(contextPath + "/index.jsp"); // redirige vers index
        }
    }
}
