/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.entities.Profile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpa.controllers.exceptions.IllegalOrphanException;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.controllers.exceptions.PreexistingEntityException;
import jpa.entities.User;

/**
 *
 * @author Kronen
 */
public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) throws PreexistingEntityException, Exception {
        if (user.getProfileCollection() == null) {
            user.setProfileCollection(new ArrayList<Profile>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Profile> attachedProfileCollection = new ArrayList<Profile>();
            for (Profile profileCollectionProfileToAttach : user.getProfileCollection()) {
                profileCollectionProfileToAttach = em.getReference(profileCollectionProfileToAttach.getClass(), profileCollectionProfileToAttach.getId());
                attachedProfileCollection.add(profileCollectionProfileToAttach);
            }
            user.setProfileCollection(attachedProfileCollection);
            em.persist(user);
            for (Profile profileCollectionProfile : user.getProfileCollection()) {
                User oldUserOfProfileCollectionProfile = profileCollectionProfile.getUser();
                profileCollectionProfile.setUser(user);
                profileCollectionProfile = em.merge(profileCollectionProfile);
                if (oldUserOfProfileCollectionProfile != null) {
                    oldUserOfProfileCollectionProfile.getProfileCollection().remove(profileCollectionProfile);
                    oldUserOfProfileCollectionProfile = em.merge(oldUserOfProfileCollectionProfile);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUser(user.getUserName()) != null) {
                throw new PreexistingEntityException("User " + user + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getUserName());
            Collection<Profile> profileCollectionOld = persistentUser.getProfileCollection();
            Collection<Profile> profileCollectionNew = user.getProfileCollection();
            List<String> illegalOrphanMessages = null;
            for (Profile profileCollectionOldProfile : profileCollectionOld) {
                if (!profileCollectionNew.contains(profileCollectionOldProfile)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Profile " + profileCollectionOldProfile + " since its user field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Profile> attachedProfileCollectionNew = new ArrayList<Profile>();
            for (Profile profileCollectionNewProfileToAttach : profileCollectionNew) {
                profileCollectionNewProfileToAttach = em.getReference(profileCollectionNewProfileToAttach.getClass(), profileCollectionNewProfileToAttach.getId());
                attachedProfileCollectionNew.add(profileCollectionNewProfileToAttach);
            }
            profileCollectionNew = attachedProfileCollectionNew;
            user.setProfileCollection(profileCollectionNew);
            user = em.merge(user);
            for (Profile profileCollectionNewProfile : profileCollectionNew) {
                if (!profileCollectionOld.contains(profileCollectionNewProfile)) {
                    User oldUserOfProfileCollectionNewProfile = profileCollectionNewProfile.getUser();
                    profileCollectionNewProfile.setUser(user);
                    profileCollectionNewProfile = em.merge(profileCollectionNewProfile);
                    if (oldUserOfProfileCollectionNewProfile != null && !oldUserOfProfileCollectionNewProfile.equals(user)) {
                        oldUserOfProfileCollectionNewProfile.getProfileCollection().remove(profileCollectionNewProfile);
                        oldUserOfProfileCollectionNewProfile = em.merge(oldUserOfProfileCollectionNewProfile);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = user.getUserName();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getUserName();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Profile> profileCollectionOrphanCheck = user.getProfileCollection();
            for (Profile profileCollectionOrphanCheckProfile : profileCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Profile " + profileCollectionOrphanCheckProfile + " in its profileCollection field has a non-nullable user field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
