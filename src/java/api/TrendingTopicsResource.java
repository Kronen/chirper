package api;

import java.sql.SQLException;
import static java.util.Collections.singletonList;
import java.util.List;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import jpa.entities.TrendingTopics;

@Path("/tt")
@Produces("application/json")
public class TrendingTopicsResource extends ResourceBase<TrendingTopics> {

    private EntityManager em;
    private List<TrendingTopics> listTrendingTopicss;

    @Override
    protected List<TrendingTopics> getAllQuery() throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        listTrendingTopicss = em.createQuery("SELECT p FROM TrendingTopics p").getResultList();
        em.getTransaction().commit();
        em.close();
        return listTrendingTopicss;
    }

    @Override
    protected List getSingleQuery(int id) throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        listTrendingTopicss = singletonList(em.find(TrendingTopics.class, id));
        em.getTransaction().commit();
        em.close();
        return listTrendingTopicss;
    }

    @Override
    void createQuery(TrendingTopics t) throws SQLException, NamingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
