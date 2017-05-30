package admin;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jpa.controllers.PostJpaController;
import jpa.entities.Post;

@WebServlet("/CRUDController")
public class CRUDController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public CRUDController() {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("action") != null) {
            String action = (String) request.getParameter("action");
            if(action.equals("list")) {
                try {
                    EntityManagerFactory emf = emf = Persistence.createEntityManagerFactory("ChirperDbPU");
                    EntityManager em = emf.createEntityManager();
                    PostJpaController pC = new PostJpaController(emf);
                    
                    List<Post> posts = pC.findPostEntities();

                    //Convert Java Object to Json
                    Gson gson = new Gson();
                    JsonElement element = gson.toJsonTree(posts, new TypeToken<List<Post>>() {
                    }.getType());
                    JsonArray jsonArray = element.getAsJsonArray();
                    String jsonData = jsonArray.toString();

                    //Return Json in the format required by jTable plugin
                    jsonData = "{\"Result\":\"OK\",\"Records\":" + jsonData + "}";
                    response.setContentType("application/json");
                    response.setHeader("Cache-control", "no-cache, no-store");
                    response.setHeader("Pragma", "no-cache");
                    response.setHeader("Expires", "-1");
                    response.getWriter().print(jsonData);
                    System.out.println(jsonData);
                } catch (Exception ex) {
                    String error = "{\"Result\":\"ERROR\",\"Message\":" + ex.getStackTrace() + "}";
                    response.getWriter().print(error);
                    System.out.println(error);
                }

            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
