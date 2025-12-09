package com.health.servlet;

import com.health.dao.DBUtil;
import com.health.dao.UserDAO;
import com.health.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name="AuthServlet", urlPatterns={"/auth/*"})
public class AuthServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    public void init() {
        DBUtil.init(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if(path == null || "/login".equals(path)){
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else if("/register".equals(path)){
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        } else if("/logout".equals(path)){
            HttpSession session = req.getSession(false);
            if(session != null) session.invalidate();
            resp.sendRedirect(req.getContextPath()+"/");
        } else {
            resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        try {
            if("/login".equals(path)){
                String email = req.getParameter("email");
                String pass = req.getParameter("password");
                User u = userDAO.findByEmail(email);
                if(u != null && userDAO.checkPassword(u, pass)){
                    req.getSession().setAttribute("user", u);
                    resp.sendRedirect(req.getContextPath()+"/app/predict");
                } else {
                    req.setAttribute("error", "Invalid credentials");
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                }
            } else if("/register".equals(path)){
                String name = req.getParameter("name");
                String email = req.getParameter("email");
                String pass = req.getParameter("password");
                boolean ok = userDAO.create(name, email, pass, "USER");
                if(ok) resp.sendRedirect(req.getContextPath()+"/auth/login");
                else {
                    req.setAttribute("error", "Registration failed");
                    req.getRequestDispatcher("/register.jsp").forward(req, resp);
                }
            } else {
                resp.sendError(404);
            }
        } catch (Exception e){
            throw new ServletException(e);
        }
    }
}
