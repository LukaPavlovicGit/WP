package com.example.homework4.servlets;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import static com.example.homework4.constants.Constants.DATASOURCE_PATH;
import static com.example.homework4.constants.Constants.DAYS;

@WebServlet(name = "chooseFood", value = "/choose-food")
public class ChooseMealServlet extends HttpServlet {
    private String message;

    private Map<String, List<String>> meals = new HashMap<>();
    // key=day, value=list of mealNames

    private Map<String, List<AbstractMap.SimpleEntry<String, String>>> map = new HashMap<>();
    // key=sessionId, value=list of pairs, where pair represents day,mealName

    public void init() {
        loadMeals();
        message = "Choose your meals";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if(getServletContext().getAttribute(request.getSession().getId()) != null) {
            out.println("<html>");
                out.println("<body>");
                    out.println("<div>");
                        out.println("<h1>" + "Order already created!" + "</h1>");
                    out.println("</div>");
                    out.println("<div>");
                        out.println("<h2>" + "Here is what you've ordered..." + "</h2>");
                    out.println("</div>");
                    out.println("<div>");
                        List<AbstractMap.SimpleEntry<String,String>> list = map.get(request.getSession().getId());
                        for(AbstractMap.SimpleEntry<String,String> key_val : list){
                            out.println("<div>");
                                out.println(key_val.getKey() + " : " + key_val.getValue()) ;
                            out.println("</div>");
                            out.println("<br>");
                        }
                    out.println("</div>");
                out.println("</body>");
            out.println("</html>");

            return;
        }

        out.println("<html>");
            out.println("<body>");
              out.println("<div>");
                out.println("<form method=\"POST\" action = \"choose-food\">");
                    out.println("<h1>" + message + "</h1>");
                        for(String day : DAYS){
                            out.println("<div>");
                                out.println("<h3>" + day + "</h3>");
                                out.println("<select name = \""+ day + "\" id=\"" + day + "\">");
                                for(String meal: meals.get(day))
                                    out.println("<option value = \"" + meal + "\" selected>" + meal + "</option>");
                                out.println("</select><br>");
                            out.println("</div>");
                        }
                        out.println("<br><br>");
                        out.println("<input type=\"submit\" name=\"submit\" value=\"Save\"/>");
                    out.println("</form>");
                out.println("</div>");
            out.println("</body>");
        out.println("</html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<AbstractMap.SimpleEntry<String, String>> day_meal_pairs = new ArrayList<>();
        for (String day: DAYS) {
            String mealName = request.getParameter(day);
            day_meal_pairs.add(new AbstractMap.SimpleEntry<>(day,mealName));
        }
        map.put(request.getSession().getId(), day_meal_pairs);
        getServletContext().setAttribute("createdOrders", map);
        getServletContext().setAttribute(request.getSession().getId(),true);

        // kreiramo odgovor
        PrintWriter out = response.getWriter();
        out.println("<html>");
            out.println("<body>");
                out.println("<div>");
                    out.println("<h1>" + "Order created successfully!" + "</h1>");
                out.println("</div>");
            out.println("</body>");
        out.println("</html>");
    }

    private void loadMeals(){
        try {
            for(String day : DAYS){
                meals.put(day, new ArrayList<>());
                FileReader fileReader = new FileReader(DATASOURCE_PATH + "/" + day + ".txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while(bufferedReader.ready()){
                    String mealName = bufferedReader.readLine();
                    meals.get(day).add(mealName);
                }
            }
        } catch (IOException e) { throw new RuntimeException(e); }
    }
}