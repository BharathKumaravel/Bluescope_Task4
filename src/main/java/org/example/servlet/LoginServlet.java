package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.DataException;
import org.example.model.User;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID=1L;
    ObjectMapper objectMapper = new ObjectMapper();
    private UserService userService = new UserService();
    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = objectMapper.readValue(req.getInputStream(), User.class);
        userService.addUser(user);
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        resp.getWriter().write("User Registered Successfully");
         LOG.info("Registered Successfully");
    }

    @Override
   public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String email =req.getParameter("mail");
            String password= req.getParameter("password");
            String token = userService.verify(email,password);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            resp.getWriter().write("Token: " + token);
            LOG.info("Token Generated");
        }catch (Exception e)
        {

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new DataException("Failed",e);
        }
    }
}
