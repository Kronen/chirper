package admin;

import admin.dbcontroller.DbController;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CRUDController")
public class CRUDController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public CRUDController() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("action") != null) {
            String action = (String) request.getParameter("action");
            if(action.equals("list")) {
                try {                    
                    String jsonData = DbController.queryToJson("SELECT * FROM post");


                    //Return Json in the format required by jTable plugin
                    jsonData = "{\"Result\":\"OK\",\"Records\":" + jsonData + "}";
                    response.setContentType("application/json");
                    response.setHeader("Cache-control", "no-cache, no-store");
                    response.setHeader("Pragma", "no-cache");
                    response.setHeader("Expires", "-1");
                    response.getWriter().print(jsonData);
                    System.out.println(jsonData);
                } catch(IOException ex) {
                    String error = "{\"Result\":\"ERROR\",\"Message\":" + ex.getStackTrace() + "}";
                    response.getWriter().print(error);
                    System.out.println(error);
                }

            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
