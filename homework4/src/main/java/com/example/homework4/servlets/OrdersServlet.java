package com.example.homework4.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.homework4.constants.Constants.DATASOURCE_PATH;
import static com.example.homework4.constants.Constants.PASSWORD_FILE_NAME;

@WebServlet(name = "orders", value = "/orders")
public class OrdersServlet extends HttpServlet {

    private String password = null;

    public void init() {
        loadPassword();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if(request.getParameter("password") == null || !request.getParameter("password").equals(password)){
            out.println("<html>");
                out.println("<body>");
                    out.println("<div>");
                        out.println("<h1>" + "Incorrect password! Try again..." + "</h1>");
                    out.println("</div>");
                 out.println("</body>");
            out.println("</html>");

            return;
        }

        // key=sessionId, value=list of pairs, where pair represents day,mealName
        Map<String, List<AbstractMap.SimpleEntry<String, String>>> map;
        map = (Map<String, List<AbstractMap.SimpleEntry<String, String>>>) request.getServletContext().getAttribute("createdOrders");

        Map<String,Map<String, Integer>> ans = new HashMap<>();

        for(String sessionId : map.keySet()){
            for(AbstractMap.SimpleEntry<String, String> day_meal_pair : map.get(sessionId)){
                String day = day_meal_pair.getKey();
                String mealName = day_meal_pair.getValue();

                Map<String, Integer> currMap = ans.get(day);
                if(currMap == null) currMap = new HashMap<>();

                Integer numberOfOrders = currMap.get(mealName);
                if(numberOfOrders == null) numberOfOrders = 0;

                currMap.put(mealName, numberOfOrders + 1);

                ans.put(day, currMap);
            }
        }

        out.println("<html>");
            out.println("<style>");
                out.println(" table, th, td { ");
                out.println("border:1px solid black;");
                out.println("}");
            out.println("</style>");
        out.println("<body>");

                out.println("<div>");
                    out.println("<h1>" + "All orders" + "</h1>");
                out.println("</div>");

                for(String day : ans.keySet()){
                    out.println("<h2>" + day + "</h2>");
                        out.println("<table style=\"width:20%\">");
                            out.println("<tr>");
                                out.println("<th>" + "mealName" + "</th>");
                                out.println("<th>" + "number of orders" + "</th>");
                            out.println("</tr>");

                            for(String mealName : ans.get(day).keySet()){
                                out.println("<tr>");
                                    out.println("<td>" + mealName + "</td>");
                                    out.println("<td>" + ans.get(day).get(mealName) + "</td>");
                                out.println("</tr>");
                            }
                        out.println("</table>");
                }

                out.println("<br><br>");
                out.println("<form method=\"POST\" action=\"/orders?password="+password+"\">");
                out.println("<input type=\"submit\" name=\"submit\" value=\"Delete\"/>");
                out.println("</form>");

            out.println("</body>");
        out.println("</html>");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // key=sessionId, value=list of pairs, where pair represents day,mealName
        Map<String, List<AbstractMap.SimpleEntry<String, String>>> map;
        map = (Map<String, List<AbstractMap.SimpleEntry<String, String>>>)request.getServletContext().getAttribute("createdOrders");
        for(String sessionId : map.keySet()){
            map.put(sessionId, null);
            request.getServletContext().setAttribute(sessionId, null);
        }
        request.getServletContext().setAttribute("createdOrders", map);

        // kreiramo odgovor
        PrintWriter out = response.getWriter();
        out.println("<html>");
            out.println("<body>");
                out.println("<div>");
                    out.println("<h1>" + "Orders deleted!" + "</h1>");
                out.println("</div>");
             out.println("</body>");
        out.println("</html>");
    }

    private void loadPassword(){
        try {
            FileReader fileReader = new FileReader(DATASOURCE_PATH + "/" + PASSWORD_FILE_NAME + ".txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            if(bufferedReader.ready())
                password = bufferedReader.readLine();

        } catch (IOException e) { throw new RuntimeException(e); }
    }
}
