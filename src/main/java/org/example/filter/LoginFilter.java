package org.example.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.config.JwtGeneration;
import org.example.dao.UserDAO;
import org.example.exception.DataException;
import org.example.service.UserService;

import java.io.IOException;

public class LoginFilter implements Filter {
    private static final int SUB_STRING=7;
    UserService userService = new UserService();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        if (uri.contains("/user")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = req.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(SUB_STRING);

        try {

            String username = JwtGeneration.extractMail(token);
            String role =userService.getRole(username);


            if (JwtGeneration.validateToken(token, username)) {
                if(req.getMethod().equals("DELETE") || req.getMethod().equals("PUT")) {
                    if(!"ADMIN".equalsIgnoreCase(role)) {
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        res.getWriter().write("Only Admin Have Access to Edit");
                        return;
                    }}
                chain.doFilter(request, response);
               return;
            }

        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Invalid token");
            throw new DataException("failed",e);

        }

        chain.doFilter(request, response);
    }
}
