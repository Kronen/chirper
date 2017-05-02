/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Kronen
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
            Profile senderId = private1.getSenderId();
            if (senderId != null) {
                senderId = em.getReference(senderId.getClass(), senderId.getId());
                private1.setSenderId(senderId);
            }
            Profile receiverId = private1.getReceiverId();
            if (receiverId != null) {
                receiverId = em.getReference(receiverId.getClass(), receiverId.getId());
                private1.setReceiverId(receiverId);
            }
            em.persist(private1);
            if (senderId != null) {
                senderId.getPrivateCollection().add(private1);
                senderId = em.merge(senderId);
            }
            if (receiverId != null) {
                receiverId.getPrivateCollection().add(private1);
                receiverId = em.merge(receiverId);
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
            Profile senderIdOld = persistentPrivate.getSenderId();
            Profile senderIdNew = private1.getSenderId();
            Profile receiverIdOld = persistentPrivate.getReceiverId();
            Profile receiverIdNew = private1.getReceiverId();
            if (senderIdNew != null) {
                senderIdNew = em.getReference(senderIdNew.getClass(), senderIdNew.getId());
                private1.setSenderId(senderIdNew);
            }
            if (receiverIdNew != null) {
                receiverIdNew = em.getReference(receiverIdNew.getClass(), receiverIdNew.getId());
                private1.setReceiverId(receiverIdNew);
            }
            private1 = em.merge(private1);
            if (senderIdOld != null && !senderIdOld.equals(senderIdNew)) {
                senderIdOld.getPrivateCollection().remove(private1);
                senderIdOld = em.merge(senderIdOld);
            }
            if (senderIdNew != null && !senderIdNew.equals(senderIdOld)) {
                senderIdNew.getPrivateCollection().add(private1);
                senderIdNew = em.merge(senderIdNew);
            }
            if (receiverIdOld != null && !receiverIdOld.equals(receiverIdNew)) {
                receiverIdOld.getPrivateCollection().remove(private1);
                receiverIdOld = em.merge(receiverIdOld);
            }
            if (receiverIdNew != null && !receiverIdNew.equals(receiverIdOld)) {
                receiverIdNew.getPrivateCollection().add(private1);
                receiverIdNew = em.merge(receiverIdNew);
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
            Profile senderId = private1.getSenderId();
            if (senderId != null) {
                senderId.getPrivateCollection().remove(private1);
                senderId = em.merge(senderId);
            }
            Profile receiverId = private1.getReceiverId();
            if (receiverId != null) {
                receiverId.getPrivateCollection().remove(private1);
                receiverId = em.merge(receiverId);
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
