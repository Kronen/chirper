package jpa.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.controllers.exceptions.PreexistingEntityException;
import jpa.entities.PostFollowees;

/**
 *
 * @author Alberto G. Lagos
 */
public class PostFolloweesJpaController implements Serializable {

    public PostFolloweesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PostFollowees postFollowees) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(postFollowees);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPostFollowees(postFollowees.getId()) != null) {
                throw new PreexistingEntityException("PostFollowees " + postFollowees + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PostFollowees postFollowees) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            postFollowees = em.merge(postFollowees);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = postFollowees.getId();
                if (findPostFollowees(id) == null) {
                    throw new NonexistentEntityException("The postFollowees with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PostFollowees postFollowees;
            try {
                postFollowees = em.getReference(PostFollowees.class, id);
                postFollowees.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The postFollowees with id " + id + " no longer exists.", enfe);
            }
            em.remove(postFollowees);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PostFollowees> findPostFolloweesEntities() {
        return findPostFolloweesEntities(true, -1, -1);
    }

    public List<PostFollowees> findPostFolloweesEntities(int maxResults, int firstResult) {
        return findPostFolloweesEntities(false, maxResults, firstResult);
    }

    private List<PostFollowees> findPostFolloweesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PostFollowees.class));
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

    public PostFollowees findPostFollowees(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PostFollowees.class, id);
        } finally {
            em.close();
        }
    }

    public int getPostFolloweesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PostFollowees> rt = cq.from(PostFollowees.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List findPostsFollowees(int id_follower) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<PostFollowees> tq = em.createNamedQuery("PostFollowees.findByIdFollowerOrdered", PostFollowees.class)
                    .setParameter("idFollower", id_follower);

            return tq.getResultList();
        } catch(NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
