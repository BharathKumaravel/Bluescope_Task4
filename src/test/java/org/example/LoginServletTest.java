package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Branch;
import org.example.model.User;
import org.example.service.BranchService;
import org.example.service.UserService;
import org.example.servlet.BranchServlet;
import org.example.servlet.LoginServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

 class LoginServletTest {
    ObjectMapper objectMapper =new ObjectMapper();
    private LoginServlet loginServlet;
    private HttpServletResponse response;
    private HttpServletRequest request;
    private UserService userService;


    @BeforeEach
    void set() throws Exception{
        loginServlet =new LoginServlet();
        request=mock(HttpServletRequest.class);
        response=mock(HttpServletResponse.class);
        userService= mock(UserService.class);

        var field = LoginServlet.class.getDeclaredField("userService");
        field.setAccessible(true);
        field.set(loginServlet, userService);

    }
    @Test
    void testDoPost() throws Exception {

        User user = new User();
        user.setEmail("abc@gmail.com");
        user.setPassword("1234");

        String json = objectMapper.writeValueAsString(user);
        when(request.getInputStream()).thenReturn(inputStream(json));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);


        loginServlet.doPost(request,response);

        verify(userService).addUser(any(User.class));
        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    void testDoGet() throws Exception{
        when(request.getParameter("mail")).thenReturn("abc@gmail.com");
        when(request.getParameter("password")).thenReturn("1234");
        when(request.getMethod()).thenReturn("GET");
        StringWriter writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        loginServlet.doGet(request, response);

        verify(userService).verify("abc@gmail.com","1234");
    }

    private ServletInputStream inputStream(String json) {
        ByteArrayInputStream bis =new ByteArrayInputStream(json.getBytes());
        return  new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return bis.available()==0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }



            @Override
            public int read() throws IOException {
                return bis.read();
            }
        };
    }
}
