package com.health.servlet;

import com.health.dao.*;
import com.health.model.Disease;
import com.health.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name="DiseaseServlet", urlPatterns={"/app/diseases/*"})
public class DiseaseServlet extends HttpServlet {
    private DiseaseDAO diseaseDAO = new DiseaseDAO();
    private SymptomDAO symptomDAO = new SymptomDAO();

    @Override
    public void init(){ DBUtil.init(getServletContext()); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        try {
            if(path == null || "/".equals(path)){
                req.setAttribute("diseases", diseaseDAO.findAll());
                req.setAttribute("symptoms", symptomDAO.findAll());
                req.getRequestDispatcher("/diseases.jsp").forward(req, resp);
            } else {
                resp.sendError(404);
            }
        } catch (SQLException e){
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if("create".equals(action)){
                int id = diseaseDAO.create(req.getParameter("name"), req.getParameter("precaution"), req.getParameter("medicine"));
                String[] sym = req.getParameterValues("symptoms");
                if(sym != null){
                    List<Integer> ids = Arrays.stream(sym).map(Integer::parseInt).collect(Collectors.toList());
                    diseaseDAO.setSymptoms(id, ids);
                }
                resp.sendRedirect(req.getContextPath()+"/app/diseases");
            } else if("update".equals(action)){
                int id = Integer.parseInt(req.getParameter("id"));
                diseaseDAO.update(id, req.getParameter("name"), req.getParameter("precaution"), req.getParameter("medicine"));
                String[] sym = req.getParameterValues("symptoms");
                if(sym != null){
                    List<Integer> ids = Arrays.stream(sym).map(Integer::parseInt).collect(Collectors.toList());
                    diseaseDAO.setSymptoms(id, ids);
                }
                resp.sendRedirect(req.getContextPath()+"/app/diseases");
            } else if("delete".equals(action)){
                int id = Integer.parseInt(req.getParameter("id"));
                diseaseDAO.delete(id);
                resp.sendRedirect(req.getContextPath()+"/app/diseases");
            } else {
                resp.sendError(400);
            }
        } catch (Exception e){
            throw new ServletException(e);
        }
    }
}
