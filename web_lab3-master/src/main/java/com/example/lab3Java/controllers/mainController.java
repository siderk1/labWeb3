package com.example.lab3Java.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "mainController", urlPatterns = {"/lab3/*"})
public class mainController extends HttpServlet {
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/lab3_web";
    private static final String ID = "root";
    private static final String PASS = "1234";

    Connection conn;
    Statement st;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String query = "select * from lab3list";
        List list = new LinkedList<String>();
        try {
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(DB_URL, ID, PASS);
            st = conn.createStatement();

            ResultSet rs = st.executeQuery(query);
            while (rs.next())
            {
                list.add(rs.getString("htmlText"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        config.getServletContext().setAttribute("list", list);
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "";
        }
        try {
            switch (pathInfo) {
                case "/start":
                    request.getRequestDispatcher("/WEB-INF/jsp/viewer.jsp").forward(request, response);
                    break;
                case "/logout":
                    logOut(request, response);
                    break;
                case "/login":
                    logIn(request, response);
                    break;
                case "/addTab":
                    addTab(request, response);
                    break;
                case "":
                    response.sendRedirect("/start");
                default:
                    request.getRequestDispatcher("/WEB-INF/jsp/viewer.jsp").forward(request, response);
                    break;
            }
        } catch (RuntimeException ex) {

        }
    }

    private void addTab(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("lab3input1");
        String text = request.getParameter("lab3input2");
        String query =  "SELECT COUNT(1) as count1 FROM lab3_web.lab3list;";
        int numOfTabs = 200;
        try {
            ResultSet rs = st.executeQuery(query);
            rs.next();
            System.out.print("Got number of items = ");
            System.out.println(rs.getInt(1));
            numOfTabs = rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        String htmlCode = "<li id=\"option" + (numOfTabs + 1) + "\">" +
                "<a href=\"#option" + (numOfTabs + 1) + "\">" + title + "</a>" +
                "<div>" +
                "<p>" + text + "</p>" +
                "</div>" +
                "</li>";
        List list = (List) request.getServletContext().getAttribute("list");
        if (list == null) {
            list = new LinkedList<String>();
            request.getServletContext().setAttribute("list", list);
        }
        list.add(htmlCode);
        query = "INSERT INTO lab3list VALUES (?, ?)";


        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, Integer.toString(numOfTabs + 1));
            preparedStmt.setString (2, htmlCode);
            preparedStmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        request.getRequestDispatcher("/WEB-INF/jsp/editor.jsp").forward(request, response);
    }

    private void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/viewer.jsp").forward(request, response);
    }

    private void logIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/editor.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
