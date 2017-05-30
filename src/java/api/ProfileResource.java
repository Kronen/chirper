package api;

import java.sql.SQLException;
import static java.util.Collections.singletonList;
import java.util.List;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import jpa.entities.Profile;

@Path("/profile")
@Produces("application/json")
public class ProfileResource extends ResourceBase<Profile> {

    private EntityManager em;
    private List<Profile> listProfiles;

    @Override
    protected List<Profile> getAllQuery() throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        listProfiles = em.createQuery("SELECT p FROM Profile p").getResultList();
        em.getTransaction().commit();
        em.close();
        return listProfiles;
    }

    @Override
    protected List getSingleQuery(int id) throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        listProfiles = singletonList(em.find(Profile.class, id));
        em.getTransaction().commit();
        em.close();
        return listProfiles;
    }

    @Override
    void createQuery(Profile t) throws SQLException, NamingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
