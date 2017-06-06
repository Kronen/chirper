package jpa.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.entities.Private;
import jpa.entities.Profile;

/**
 *
 * @author Alberto G. Lagos
 */
public class PrivateJpaController implements Serializable {

    public PrivateJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Private private1) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profile sender = private1.getSender();
            if (sender != null) {
                sender = em.getReference(sender.getClass(), sender.getId());
                private1.setSender(sender);
            }
            Profile receiver = private1.getReceiver();
            if (receiver != null) {
                receiver = em.getReference(receiver.getClass(), receiver.getId());
                private1.setReceiver(receiver);
            }
            em.persist(private1);
            if (sender != null) {
                sender.getPrivateCollection().add(private1);
                sender = em.merge(sender);
            }
            if (receiver != null) {
                receiver.getPrivateCollection().add(private1);
                receiver = em.merge(receiver);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Private private1) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Private persistentPrivate = em.find(Private.class, private1.getId());
            Profile senderOld = persistentPrivate.getSender();
            Profile senderNew = private1.getSender();
            Profile receiverOld = persistentPrivate.getReceiver();
            Profile receiverNew = private1.getReceiver();
            if (senderNew != null) {
                senderNew = em.getReference(senderNew.getClass(), senderNew.getId());
                private1.setSender(senderNew);
            }
            if (receiverNew != null) {
                receiverNew = em.getReference(receiverNew.getClass(), receiverNew.getId());
                private1.setReceiver(receiverNew);
            }
            private1 = em.merge(private1);
            if (senderOld != null && !senderOld.equals(senderNew)) {
                senderOld.getPrivateCollection().remove(private1);
                senderOld = em.merge(senderOld);
            }
            if (senderNew != null && !senderNew.equals(senderOld)) {
                senderNew.getPrivateCollection().add(private1);
                senderNew = em.merge(senderNew);
            }
            if (receiverOld != null && !receiverOld.equals(receiverNew)) {
                receiverOld.getPrivateCollection().remove(private1);
                receiverOld = em.merge(receiverOld);
            }
            if (receiverNew != null && !receiverNew.equals(receiverOld)) {
                receiverNew.getPrivateCollection().add(private1);
                receiverNew = em.merge(receiverNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = private1.getId();
                if (findPrivate(id) == null) {
                    throw new NonexistentEntityException("The private with id " + id + " no longer exists.");
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
            Private private1;
            try {
                private1 = em.getReference(Private.class, id);
                private1.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The private1 with id " + id + " no longer exists.", enfe);
            }
            Profile sender = private1.getSender();
            if (sender != null) {
                sender.getPrivateCollection().remove(private1);
                sender = em.merge(sender);
            }
            Profile receiver = private1.getReceiver();
            if (receiver != null) {
                receiver.getPrivateCollection().remove(private1);
                receiver = em.merge(receiver);
            }
            em.remove(private1);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Private> findPrivateEntities() {
        return findPrivateEntities(true, -1, -1);
    }

    public List<Private> findPrivateEntities(int maxResults, int firstResult) {
        return findPrivateEntities(false, maxResults, firstResult);
    }

    private List<Private> findPrivateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Private.class));
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

    public Private findPrivate(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Private.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrivateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Private> rt = cq.from(Private.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
