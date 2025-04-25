package com.t2308e.exam.javaservletexam.servlet;

import com.t2308e.exam.javaservletexam.DAO.PlayerIndexDAO;
import com.t2308e.exam.javaservletexam.entity.PlayerIndex;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/PlayerServlet")
public class PlayerServlet extends HttpServlet {
    private PlayerIndexDAO playerIndexDAO;

    @Override
    public void init() {
        playerIndexDAO = new PlayerIndexDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null || action.isEmpty()) {
                listPlayerIndexes(request, response);
            } else if (action.equals("edit")) {
                showEditForm(request, response);
            } else if (action.equals("delete")) {
                deletePlayerIndex(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action.equals("add")) {
                addPlayerIndex(request, response);
            } else if (action.equals("update")) {
                updatePlayerIndex(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listPlayerIndexes(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<PlayerIndex> playerIndexList = playerIndexDAO.getAllPlayerIndexes();
        request.setAttribute("playerIndexList", playerIndexList);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private void addPlayerIndex(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String playerName = request.getParameter("playerName");
        String fullName = request.getParameter("fullName");
        int playerAge = Integer.parseInt(request.getParameter("playerAge"));
        String indexName = request.getParameter("indexName");
        float value = Float.parseFloat(request.getParameter("value"));

        // Validate dữ liệu
        if (playerName.isEmpty() || fullName.isEmpty() || playerAge < 0 || value < 0) {
            request.setAttribute("error", "Invalid input data!");
            listPlayerIndexes(request, response);
            return;
        }

        PlayerIndex playerIndex = new PlayerIndex();
        playerIndex.setPlayerName(playerName);
        playerIndex.setFullName(fullName);
        playerIndex.setPlayerAge(playerAge);
        playerIndex.setIndexName(indexName);
        playerIndex.setValue(value);

        playerIndexDAO.addPlayerIndex(playerIndex);
        response.sendRedirect("PlayerServlet");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        PlayerIndex playerIndex = playerIndexDAO.getPlayerIndexById(id);
        request.setAttribute("playerIndex", playerIndex);
        request.getRequestDispatcher("edit.jsp").forward(request, response);
    }

    private void updatePlayerIndex(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String playerName = request.getParameter("playerName");
        int playerAge = Integer.parseInt(request.getParameter("playerAge"));
        String indexName = request.getParameter("indexName");
        float value = Float.parseFloat(request.getParameter("value"));

        if (playerName.isEmpty() || playerAge < 0 || value < 0) {
            request.setAttribute("error", "Invalid input data!");
            request.setAttribute("playerIndex", playerIndexDAO.getPlayerIndexById(id));
            request.getRequestDispatcher("edit.jsp").forward(request, response);
            return;
        }

        PlayerIndex playerIndex = new PlayerIndex();
        playerIndex.setId(id);
        playerIndex.setPlayerName(playerName);
        playerIndex.setPlayerAge(playerAge);
        playerIndex.setIndexName(indexName);
        playerIndex.setValue(value);

        playerIndexDAO.updatePlayerIndex(playerIndex);
        response.sendRedirect("PlayerServlet");
    }

    private void deletePlayerIndex(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        playerIndexDAO.deletePlayerIndex(id);
        response.sendRedirect("PlayerServlet");
    }
}