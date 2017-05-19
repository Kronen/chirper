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
import jpa.controllers.exceptions.PreexistingEntityException;
import jpa.entities.TrendingTopics;

/**
 *
 * @author Kronen
 */
public class TrendingTopicsJpaController implements Serializable {

    public TrendingTopicsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TrendingTopics trendingTopics) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(trendingTopics);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTrendingTopics(trendingTopics.getId()) != null) {
                throw new PreexistingEntityException("TrendingTopics " + trendingTopics + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TrendingTopics trendingTopics) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            trendingTopics = em.merge(trendingTopics);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = trendingTopics.getId();
                if (findTrendingTopics(id) == null) {
                    throw new NonexistentEntityException("The trendingTopics with id " + id + " no longer exists.");
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
            TrendingTopics trendingTopics;
            try {
                trendingTopics = em.getReference(TrendingTopics.class, id);
                trendingTopics.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trendingTopics with id " + id + " no longer exists.", enfe);
            }
            em.remove(trendingTopics);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TrendingTopics> findTrendingTopicsEntities() {
        return findTrendingTopicsEntities(true, -1, -1);
    }

    public List<TrendingTopics> findTrendingTopicsEntities(int maxResults, int firstResult) {
        return findTrendingTopicsEntities(false, maxResults, firstResult);
    }

    private List<TrendingTopics> findTrendingTopicsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TrendingTopics.class));
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

    public TrendingTopics findTrendingTopics(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TrendingTopics.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrendingTopicsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TrendingTopics> rt = cq.from(TrendingTopics.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
