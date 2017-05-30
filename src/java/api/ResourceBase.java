package api;

import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;



@Produces("application/json")
public abstract class ResourceBase<T> {

    /* Methods declarations here */
    protected EntityManager getEntityManager() throws NamingException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChirperDbPU");
        return emf.createEntityManager();
    }

    @GET
    public List<T> getList() throws SQLException, NamingException {
        List records = getAllQuery();
        return records;
    }

    @GET
    @Path("{id}")
    public List<T> getSingle(@PathParam("id") int id) throws NamingException {
        List records = getSingleQuery(id);
        return records;
    }

    @POST
    public void insertTix(T t) throws NamingException, SQLException {
        createQuery(t);
    }
    
    abstract List getAllQuery() throws NamingException;
    abstract List getSingleQuery(int id) throws NamingException;
    abstract void createQuery(T t) throws SQLException, NamingException;
    
}
