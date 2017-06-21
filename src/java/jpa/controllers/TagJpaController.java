package jpa.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.entities.Post;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.entities.Tag;

/**
 *
 * @author Alberto G. Lagos
 */
public class TagJpaController implements Serializable {

    public TagJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tag tag) {
        if (tag.getPostCollection() == null) {
            tag.setPostCollection(new ArrayList<Post>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Post> attachedPostCollection = new ArrayList<Post>();
            for (Post postCollectionPostToAttach : tag.getPostCollection()) {
                postCollectionPostToAttach = em.getReference(postCollectionPostToAttach.getClass(), postCollectionPostToAttach.getId());
                attachedPostCollection.add(postCollectionPostToAttach);
            }
            tag.setPostCollection(attachedPostCollection);
            em.persist(tag);
            for (Post postCollectionPost : tag.getPostCollection()) {
                postCollectionPost.getTagCollection().add(tag);
                postCollectionPost = em.merge(postCollectionPost);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tag tag) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tag persistentTag = em.find(Tag.class, tag.getId());
            Collection<Post> postCollectionOld = persistentTag.getPostCollection();
            Collection<Post> postCollectionNew = tag.getPostCollection();
            Collection<Post> attachedPostCollectionNew = new ArrayList<Post>();
            for (Post postCollectionNewPostToAttach : postCollectionNew) {
                postCollectionNewPostToAttach = em.getReference(postCollectionNewPostToAttach.getClass(), postCollectionNewPostToAttach.getId());
                attachedPostCollectionNew.add(postCollectionNewPostToAttach);
            }
            postCollectionNew = attachedPostCollectionNew;
            tag.setPostCollection(postCollectionNew);
            tag = em.merge(tag);
            for (Post postCollectionOldPost : postCollectionOld) {
                if (!postCollectionNew.contains(postCollectionOldPost)) {
                    postCollectionOldPost.getTagCollection().remove(tag);
                    postCollectionOldPost = em.merge(postCollectionOldPost);
                }
            }
            for (Post postCollectionNewPost : postCollectionNew) {
                if (!postCollectionOld.contains(postCollectionNewPost)) {
                    postCollectionNewPost.getTagCollection().add(tag);
                    postCollectionNewPost = em.merge(postCollectionNewPost);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tag.getId();
                if (findTag(id) == null) {
                    throw new NonexistentEntityException("The tag with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tag tag;
            try {
                tag = em.getReference(Tag.class, id);
                tag.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tag with id " + id + " no longer exists.", enfe);
            }
            Collection<Post> postCollection = tag.getPostCollection();
            for (Post postCollectionPost : postCollection) {
                postCollectionPost.getTagCollection().remove(tag);
                postCollectionPost = em.merge(postCollectionPost);
            }
            em.remove(tag);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tag> findTagEntities() {
        return findTagEntities(true, -1, -1);
    }

    public List<Tag> findTagEntities(int maxResults, int firstResult) {
        return findTagEntities(false, maxResults, firstResult);
    }

    private List<Tag> findTagEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tag.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Tag findTag(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tag.class, id);
        } finally {
            em.close();
        }
    }

    public int getTagCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tag> rt = cq.from(Tag.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public void createTagsWithPost(Post newPost, List<String> tags) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Tag tag = null;
            for(String tagName : tags) {
                TypedQuery<Tag> tq = em.createNamedQuery("Tag.findByTagName", Tag.class)
                        .setParameter("tagName", tagName);
                try {
                    tag = tq.getSingleResult();
                    tag.getPostCollection().add(newPost);
                    edit(tag);
                } catch (NoResultException|NonexistentEntityException e) {
                    tag = new Tag(null, tagName);
                    ArrayList<Post> posts = new ArrayList<>();
                    posts.add(newPost);
                    tag.setPostCollection(posts);
                    create(tag);
                }
            }
        } finally {
            em.close();
        }        
    }
    
    public List findTrendingTopics(int dias) {
        EntityManager em = getEntityManager();
        try {
            String sql =
                "SELECT t.id AS tagID, t.tag_name AS tag, count(pt.tag) AS count " +
                "FROM post_tag pt " +
                "   LEFT JOIN tag t ON (pt.tag = t.id) " +
                "   LEFT JOIN post p ON (pt.post = p.id) " +
                "WHERE p.pub_date >= now() - INTERVAL ? DAY " +
                "GROUP BY pt.tag";
            Query q = em.createNativeQuery(sql, "TrendingTopicsMappingXML")
                    .setParameter(1, dias)
                    .setMaxResults(10);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
