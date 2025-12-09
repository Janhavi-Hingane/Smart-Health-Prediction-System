package com.health.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import com.health.model.User;

public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getRequestURI();
        if(path.contains("/app/")){
            HttpSession session = req.getSession(false);
            User u = (session == null) ? null : (User) session.getAttribute("user");
            if(u == null){
                resp.sendRedirect(req.getContextPath()+"/auth/login");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
