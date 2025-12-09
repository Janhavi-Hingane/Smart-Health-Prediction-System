package com.health.servlet;

import com.health.dao.*;
import com.health.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name="PredictServlet", urlPatterns={"/app/predict"})
public class PredictServlet extends HttpServlet {
    private SymptomDAO symptomDAO = new SymptomDAO();
    private DiseaseDAO diseaseDAO = new DiseaseDAO();
    private HistoryDAO historyDAO = new HistoryDAO();

    @Override
    public void init(){ DBUtil.init(getServletContext()); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("symptoms", symptomDAO.findAll());
            req.getRequestDispatcher("/predict.jsp").forward(req, resp);
        } catch (SQLException e){
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] selected = req.getParameterValues("symptoms");
        if(selected == null || selected.length == 0){
            req.setAttribute("error", "Please select at least one symptom.");
            doGet(req, resp);
            return;
        }
        try {
            Set<Integer> sel = new HashSet<>();
            for(String s : selected) sel.add(Integer.parseInt(s));

            Map<Integer, List<Integer>> map = diseaseDAO.getDiseaseSymptomMap();
            int bestDiseaseId = -1;
            int maxMatch = -1;

            for(var e : map.entrySet()){
                int matches = 0;
                for(Integer sid : e.getValue()){
                    if(sel.contains(sid)) matches++;
                }
                if(matches > maxMatch){
                    maxMatch = matches;
                    bestDiseaseId = e.getKey();
                }
            }

            Integer predicted = (bestDiseaseId == -1 || maxMatch == 0) ? null : bestDiseaseId;

            // Save history
            User u = (User) req.getSession().getAttribute("user");
            String inputText = Arrays.toString(selected);
            historyDAO.insert(u.getId(), inputText, predicted);

            req.setAttribute("predictionId", predicted);
            req.setAttribute("maxMatch", maxMatch);
            req.setAttribute("symptoms", symptomDAO.findAll());
            req.getRequestDispatcher("/result.jsp").forward(req, resp);
        } catch (Exception e){
            throw new ServletException(e);
        }
    }
}
