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
import jpa.controllers.exceptions.IllegalOrphanException;
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
            Profile idAuthor = post.getIdAuthor();
            if (idAuthor != null) {
                idAuthor = em.getReference(idAuthor.getClass(), idAuthor.getId());
                post.setIdAuthor(idAuthor);
            }
            Collection<Tag> attachedTagCollection = new ArrayList<Tag>();
            for (Tag tagCollectionTagToAttach : post.getTagCollection()) {
                tagCollectionTagToAttach = em.getReference(tagCollectionTagToAttach.getClass(), tagCollectionTagToAttach.getId());
                attachedTagCollection.add(tagCollectionTagToAttach);
            }
            post.setTagCollection(attachedTagCollection);
            em.persist(post);
            if (idAuthor != null) {
                idAuthor.getPostCollection().add(post);
                idAuthor = em.merge(idAuthor);
            }
            for (Tag tagCollectionTag : post.getTagCollection()) {
                Post oldIdPostOfTagCollectionTag = tagCollectionTag.getIdPost();
                tagCollectionTag.setIdPost(post);
                tagCollectionTag = em.merge(tagCollectionTag);
                if (oldIdPostOfTagCollectionTag != null) {
                    oldIdPostOfTagCollectionTag.getTagCollection().remove(tagCollectionTag);
                    oldIdPostOfTagCollectionTag = em.merge(oldIdPostOfTagCollectionTag);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Post post) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Post persistentPost = em.find(Post.class, post.getId());
            Profile idAuthorOld = persistentPost.getIdAuthor();
            Profile idAuthorNew = post.getIdAuthor();
            Collection<Tag> tagCollectionOld = persistentPost.getTagCollection();
            Collection<Tag> tagCollectionNew = post.getTagCollection();
            List<String> illegalOrphanMessages = null;
            for (Tag tagCollectionOldTag : tagCollectionOld) {
                if (!tagCollectionNew.contains(tagCollectionOldTag)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tag " + tagCollectionOldTag + " since its idPost field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idAuthorNew != null) {
                idAuthorNew = em.getReference(idAuthorNew.getClass(), idAuthorNew.getId());
                post.setIdAuthor(idAuthorNew);
            }
            Collection<Tag> attachedTagCollectionNew = new ArrayList<Tag>();
            for (Tag tagCollectionNewTagToAttach : tagCollectionNew) {
                tagCollectionNewTagToAttach = em.getReference(tagCollectionNewTagToAttach.getClass(), tagCollectionNewTagToAttach.getId());
                attachedTagCollectionNew.add(tagCollectionNewTagToAttach);
            }
            tagCollectionNew = attachedTagCollectionNew;
            post.setTagCollection(tagCollectionNew);
            post = em.merge(post);
            if (idAuthorOld != null && !idAuthorOld.equals(idAuthorNew)) {
                idAuthorOld.getPostCollection().remove(post);
                idAuthorOld = em.merge(idAuthorOld);
            }
            if (idAuthorNew != null && !idAuthorNew.equals(idAuthorOld)) {
                idAuthorNew.getPostCollection().add(post);
                idAuthorNew = em.merge(idAuthorNew);
            }
            for (Tag tagCollectionNewTag : tagCollectionNew) {
                if (!tagCollectionOld.contains(tagCollectionNewTag)) {
                    Post oldIdPostOfTagCollectionNewTag = tagCollectionNewTag.getIdPost();
                    tagCollectionNewTag.setIdPost(post);
                    tagCollectionNewTag = em.merge(tagCollectionNewTag);
                    if (oldIdPostOfTagCollectionNewTag != null && !oldIdPostOfTagCollectionNewTag.equals(post)) {
                        oldIdPostOfTagCollectionNewTag.getTagCollection().remove(tagCollectionNewTag);
                        oldIdPostOfTagCollectionNewTag = em.merge(oldIdPostOfTagCollectionNewTag);
                    }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Collection<Tag> tagCollectionOrphanCheck = post.getTagCollection();
            for (Tag tagCollectionOrphanCheckTag : tagCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Post (" + post + ") cannot be destroyed since the Tag " + tagCollectionOrphanCheckTag + " in its tagCollection field has a non-nullable idPost field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Profile idAuthor = post.getIdAuthor();
            if (idAuthor != null) {
                idAuthor.getPostCollection().remove(post);
                idAuthor = em.merge(idAuthor);
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

    public List getPostCountByAuthor(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List findPostsByAuthor(int id_author) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Post> tq = em.createNamedQuery("Post.findByAuthor", Post.class)
                    .setParameter("idAuthor", id_author);

            return tq.getResultList();
        } catch(NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
