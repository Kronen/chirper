package api;

import java.sql.SQLException;
import static java.util.Collections.singletonList;
import java.util.List;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import jpa.entities.Post;

@Path("/post")
@Produces("application/json")
public class PostResource extends ResourceBase<Post> {

    private EntityManager em;
    private List<Post> listPosts;

    @Override
    protected List<Post> getAllQuery() throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        listPosts = em.createQuery("SELECT p FROM Post p").getResultList();
        em.getTransaction().commit();
        em.close();
        return listPosts;
    }

    @Override
    protected List getSingleQuery(int id) throws NamingException {
        em = getEntityManager();
        em.getTransaction().begin();
        listPosts = singletonList(em.find(Post.class, id));
        em.getTransaction().commit();
        em.close();
        return listPosts;
    }

    @Override
    protected void createQuery(Post t) throws SQLException, NamingException {
        Post post = new Post();
        em = getEntityManager();
        em.getTransaction().begin();
        post.setAuthor(t.getAuthor());
        post.setLatitude(t.getLatitude());
        post.setLongitude(t.getLongitude());
        post.setLikes(t.getLikes());
        post.setOriginalPost(t.getOriginalPost());
        post.setPubDate(t.getPubDate());
        post.setText(t.getText());
        em.persist(post);
        em.getTransaction().commit();
        em.close();
    }

}
