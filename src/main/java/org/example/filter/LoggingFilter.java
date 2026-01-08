package org.example.filter;



import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class LoggingFilter implements Filter {

    private  static final Logger LOG = LoggerFactory.getLogger(LoggingFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {


        LOG.info("new request have come in");

        chain.doFilter(request,response);

        LOG.info("Response have been set");

    }
}
