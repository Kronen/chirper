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
import jpa.entities.Tag;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.entities.Post;

/**
 *
 * @author Kronen
 */
public class PostJpaController implements Serializable {

    public PostJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Post post) {
        if (post.getTagCollection() == null) {
            post.setTagCollection(new ArrayList<Tag>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profile author = post.getAuthor();
            if (author != null) {
                author = em.getReference(author.getClass(), author.getId());
                post.setAuthor(author);
            }
            Collection<Tag> attachedTagCollection = new ArrayList<Tag>();
            for (Tag tagCollectionTagToAttach : post.getTagCollection()) {
                tagCollectionTagToAttach = em.getReference(tagCollectionTagToAttach.getClass(), tagCollectionTagToAttach.getId());
                attachedTagCollection.add(tagCollectionTagToAttach);
            }
            post.setTagCollection(attachedTagCollection);
            em.persist(post);
            if (author != null) {
                author.getPostCollection().add(post);
                author = em.merge(author);
            }
            for (Tag tagCollectionTag : post.getTagCollection()) {
                tagCollectionTag.getPostCollection().add(post);
                tagCollectionTag = em.merge(tagCollectionTag);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Post post) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Post persistentPost = em.find(Post.class, post.getId());
            Profile authorOld = persistentPost.getAuthor();
            Profile authorNew = post.getAuthor();
            Collection<Tag> tagCollectionOld = persistentPost.getTagCollection();
            Collection<Tag> tagCollectionNew = post.getTagCollection();
            if (authorNew != null) {
                authorNew = em.getReference(authorNew.getClass(), authorNew.getId());
                post.setAuthor(authorNew);
            }
            Collection<Tag> attachedTagCollectionNew = new ArrayList<Tag>();
            for (Tag tagCollectionNewTagToAttach : tagCollectionNew) {
                tagCollectionNewTagToAttach = em.getReference(tagCollectionNewTagToAttach.getClass(), tagCollectionNewTagToAttach.getId());
                attachedTagCollectionNew.add(tagCollectionNewTagToAttach);
            }
            tagCollectionNew = attachedTagCollectionNew;
            post.setTagCollection(tagCollectionNew);
            post = em.merge(post);
            if (authorOld != null && !authorOld.equals(authorNew)) {
                authorOld.getPostCollection().remove(post);
                authorOld = em.merge(authorOld);
            }
            if (authorNew != null && !authorNew.equals(authorOld)) {
                authorNew.getPostCollection().add(post);
                authorNew = em.merge(authorNew);
            }
            for (Tag tagCollectionOldTag : tagCollectionOld) {
                if (!tagCollectionNew.contains(tagCollectionOldTag)) {
                    tagCollectionOldTag.getPostCollection().remove(post);
                    tagCollectionOldTag = em.merge(tagCollectionOldTag);
                }
            }
            for (Tag tagCollectionNewTag : tagCollectionNew) {
                if (!tagCollectionOld.contains(tagCollectionNewTag)) {
                    tagCollectionNewTag.getPostCollection().add(post);
                    tagCollectionNewTag = em.merge(tagCollectionNewTag);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = post.getId();
                if (findPost(id) == null) {
                    throw new NonexistentEntityException("The post with id " + id + " no longer exists.");
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
            Post post;
            try {
                post = em.getReference(Post.class, id);
                post.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The post with id " + id + " no longer exists.", enfe);
            }
            Profile author = post.getAuthor();
            if (author != null) {
                author.getPostCollection().remove(post);
                author = em.merge(author);
            }
            Collection<Tag> tagCollection = post.getTagCollection();
            for (Tag tagCollectionTag : tagCollection) {
                tagCollectionTag.getPostCollection().remove(post);
                tagCollectionTag = em.merge(tagCollectionTag);
            }
            em.remove(post);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Post> findPostEntities() {
        return findPostEntities(true, -1, -1);
    }

    public List<Post> findPostEntities(int maxResults, int firstResult) {
        return findPostEntities(false, maxResults, firstResult);
    }

    private List<Post> findPostEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Post.class));
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

    public Post findPost(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Post.class, id);
        } finally {
            em.close();
        }
    }

    public int getPostCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Post> rt = cq.from(Post.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List findPostReplies(Integer originalPost) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Post> tq = em.createNamedQuery("Post.findByOriginalPostOrdered", Post.class)
                    .setParameter("originalPost", originalPost);

            return tq.getResultList();
        } catch(NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
