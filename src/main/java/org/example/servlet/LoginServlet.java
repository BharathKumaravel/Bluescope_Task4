package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.User;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
    ObjectMapper objectMapper = new ObjectMapper();
    UserService userService = new UserService();
    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Registration started");
        User user = objectMapper.readValue(req.getInputStream(), User.class);
        userService.addUser(user);
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        resp.getWriter().write("User Registered Successfully");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            LOG.info("Login request");
            String email =req.getParameter("mail");
            String password= req.getParameter("password");
            LOG.info("Parameter fetched");
            String token = userService.verify(email,password);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            resp.getWriter().write("Token: " + token);
        }catch (Exception e)
        {
            LOG.error(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
