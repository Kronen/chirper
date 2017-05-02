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
import jpa.entities.User;
import jpa.entities.Profile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import jpa.controllers.exceptions.IllegalOrphanException;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.entities.Private;
import jpa.entities.Post;

/**
 *
 * @author Kronen
 */
public class ProfileJpaController implements Serializable {

    public ProfileJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Profile profile) throws IllegalOrphanException {
        if (profile.getProfileCollection() == null) {
            profile.setProfileCollection(new ArrayList<Profile>());
        }
        if (profile.getProfileCollection1() == null) {
            profile.setProfileCollection1(new ArrayList<Profile>());
        }
        if (profile.getPrivateCollection() == null) {
            profile.setPrivateCollection(new ArrayList<Private>());
        }
        if (profile.getPrivateCollection1() == null) {
            profile.setPrivateCollection1(new ArrayList<Private>());
        }
        if (profile.getPostCollection() == null) {
            profile.setPostCollection(new ArrayList<Post>());
        }
        List<String> illegalOrphanMessages = null;
        User userNameOrphanCheck = profile.getUserName();
        if (userNameOrphanCheck != null) {
            Profile oldProfileOfUserName = userNameOrphanCheck.getProfile();
            if (oldProfileOfUserName != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The User " + userNameOrphanCheck + " already has an item of type Profile whose userName column cannot be null. Please make another selection for the userName field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userName = profile.getUserName();
            if (userName != null) {
                userName = em.getReference(userName.getClass(), userName.getUserName());
                profile.setUserName(userName);
            }
            Collection<Profile> attachedProfileCollection = new ArrayList<Profile>();
            for (Profile profileCollectionProfileToAttach : profile.getProfileCollection()) {
                profileCollectionProfileToAttach = em.getReference(profileCollectionProfileToAttach.getClass(), profileCollectionProfileToAttach.getId());
                attachedProfileCollection.add(profileCollectionProfileToAttach);
            }
            profile.setProfileCollection(attachedProfileCollection);
            Collection<Profile> attachedProfileCollection1 = new ArrayList<Profile>();
            for (Profile profileCollection1ProfileToAttach : profile.getProfileCollection1()) {
                profileCollection1ProfileToAttach = em.getReference(profileCollection1ProfileToAttach.getClass(), profileCollection1ProfileToAttach.getId());
                attachedProfileCollection1.add(profileCollection1ProfileToAttach);
            }
            profile.setProfileCollection1(attachedProfileCollection1);
            Collection<Private> attachedPrivateCollection = new ArrayList<Private>();
            for (Private privateCollectionPrivateToAttach : profile.getPrivateCollection()) {
                privateCollectionPrivateToAttach = em.getReference(privateCollectionPrivateToAttach.getClass(), privateCollectionPrivateToAttach.getId());
                attachedPrivateCollection.add(privateCollectionPrivateToAttach);
            }
            profile.setPrivateCollection(attachedPrivateCollection);
            Collection<Private> attachedPrivateCollection1 = new ArrayList<Private>();
            for (Private privateCollection1PrivateToAttach : profile.getPrivateCollection1()) {
                privateCollection1PrivateToAttach = em.getReference(privateCollection1PrivateToAttach.getClass(), privateCollection1PrivateToAttach.getId());
                attachedPrivateCollection1.add(privateCollection1PrivateToAttach);
            }
            profile.setPrivateCollection1(attachedPrivateCollection1);
            Collection<Post> attachedPostCollection = new ArrayList<Post>();
            for (Post postCollectionPostToAttach : profile.getPostCollection()) {
                postCollectionPostToAttach = em.getReference(postCollectionPostToAttach.getClass(), postCollectionPostToAttach.getId());
                attachedPostCollection.add(postCollectionPostToAttach);
            }
            profile.setPostCollection(attachedPostCollection);
            em.persist(profile);
            if (userName != null) {
                userName.setProfile(profile);
                userName = em.merge(userName);
            }
            for (Profile profileCollectionProfile : profile.getProfileCollection()) {
                profileCollectionProfile.getProfileCollection().add(profile);
                profileCollectionProfile = em.merge(profileCollectionProfile);
            }
            for (Profile profileCollection1Profile : profile.getProfileCollection1()) {
                profileCollection1Profile.getProfileCollection().add(profile);
                profileCollection1Profile = em.merge(profileCollection1Profile);
            }
            for (Private privateCollectionPrivate : profile.getPrivateCollection()) {
                Profile oldSenderIdOfPrivateCollectionPrivate = privateCollectionPrivate.getSenderId();
                privateCollectionPrivate.setSenderId(profile);
                privateCollectionPrivate = em.merge(privateCollectionPrivate);
                if (oldSenderIdOfPrivateCollectionPrivate != null) {
                    oldSenderIdOfPrivateCollectionPrivate.getPrivateCollection().remove(privateCollectionPrivate);
                    oldSenderIdOfPrivateCollectionPrivate = em.merge(oldSenderIdOfPrivateCollectionPrivate);
                }
            }
            for (Private privateCollection1Private : profile.getPrivateCollection1()) {
                Profile oldReceiverIdOfPrivateCollection1Private = privateCollection1Private.getReceiverId();
                privateCollection1Private.setReceiverId(profile);
                privateCollection1Private = em.merge(privateCollection1Private);
                if (oldReceiverIdOfPrivateCollection1Private != null) {
                    oldReceiverIdOfPrivateCollection1Private.getPrivateCollection1().remove(privateCollection1Private);
                    oldReceiverIdOfPrivateCollection1Private = em.merge(oldReceiverIdOfPrivateCollection1Private);
                }
            }
            for (Post postCollectionPost : profile.getPostCollection()) {
                Profile oldIdAuthorOfPostCollectionPost = postCollectionPost.getIdAuthor();
                postCollectionPost.setIdAuthor(profile);
                postCollectionPost = em.merge(postCollectionPost);
                if (oldIdAuthorOfPostCollectionPost != null) {
                    oldIdAuthorOfPostCollectionPost.getPostCollection().remove(postCollectionPost);
                    oldIdAuthorOfPostCollectionPost = em.merge(oldIdAuthorOfPostCollectionPost);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Profile profile) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profile persistentProfile = em.find(Profile.class, profile.getId());
            User userNameOld = persistentProfile.getUserName();
            User userNameNew = profile.getUserName();
            Collection<Profile> profileCollectionOld = persistentProfile.getProfileCollection();
            Collection<Profile> profileCollectionNew = profile.getProfileCollection();
            Collection<Profile> profileCollection1Old = persistentProfile.getProfileCollection1();
            Collection<Profile> profileCollection1New = profile.getProfileCollection1();
            Collection<Private> privateCollectionOld = persistentProfile.getPrivateCollection();
            Collection<Private> privateCollectionNew = profile.getPrivateCollection();
            Collection<Private> privateCollection1Old = persistentProfile.getPrivateCollection1();
            Collection<Private> privateCollection1New = profile.getPrivateCollection1();
            Collection<Post> postCollectionOld = persistentProfile.getPostCollection();
            Collection<Post> postCollectionNew = profile.getPostCollection();
            List<String> illegalOrphanMessages = null;
            if (userNameNew != null && !userNameNew.equals(userNameOld)) {
                Profile oldProfileOfUserName = userNameNew.getProfile();
                if (oldProfileOfUserName != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The User " + userNameNew + " already has an item of type Profile whose userName column cannot be null. Please make another selection for the userName field.");
                }
            }
            for (Private privateCollectionOldPrivate : privateCollectionOld) {
                if (!privateCollectionNew.contains(privateCollectionOldPrivate)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Private " + privateCollectionOldPrivate + " since its senderId field is not nullable.");
                }
            }
            for (Private privateCollection1OldPrivate : privateCollection1Old) {
                if (!privateCollection1New.contains(privateCollection1OldPrivate)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Private " + privateCollection1OldPrivate + " since its receiverId field is not nullable.");
                }
            }
            for (Post postCollectionOldPost : postCollectionOld) {
                if (!postCollectionNew.contains(postCollectionOldPost)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Post " + postCollectionOldPost + " since its idAuthor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userNameNew != null) {
                userNameNew = em.getReference(userNameNew.getClass(), userNameNew.getUserName());
                profile.setUserName(userNameNew);
            }
            Collection<Profile> attachedProfileCollectionNew = new ArrayList<Profile>();
            for (Profile profileCollectionNewProfileToAttach : profileCollectionNew) {
                profileCollectionNewProfileToAttach = em.getReference(profileCollectionNewProfileToAttach.getClass(), profileCollectionNewProfileToAttach.getId());
                attachedProfileCollectionNew.add(profileCollectionNewProfileToAttach);
            }
            profileCollectionNew = attachedProfileCollectionNew;
            profile.setProfileCollection(profileCollectionNew);
            Collection<Profile> attachedProfileCollection1New = new ArrayList<Profile>();
            for (Profile profileCollection1NewProfileToAttach : profileCollection1New) {
                profileCollection1NewProfileToAttach = em.getReference(profileCollection1NewProfileToAttach.getClass(), profileCollection1NewProfileToAttach.getId());
                attachedProfileCollection1New.add(profileCollection1NewProfileToAttach);
            }
            profileCollection1New = attachedProfileCollection1New;
            profile.setProfileCollection1(profileCollection1New);
            Collection<Private> attachedPrivateCollectionNew = new ArrayList<Private>();
            for (Private privateCollectionNewPrivateToAttach : privateCollectionNew) {
                privateCollectionNewPrivateToAttach = em.getReference(privateCollectionNewPrivateToAttach.getClass(), privateCollectionNewPrivateToAttach.getId());
                attachedPrivateCollectionNew.add(privateCollectionNewPrivateToAttach);
            }
            privateCollectionNew = attachedPrivateCollectionNew;
            profile.setPrivateCollection(privateCollectionNew);
            Collection<Private> attachedPrivateCollection1New = new ArrayList<Private>();
            for (Private privateCollection1NewPrivateToAttach : privateCollection1New) {
                privateCollection1NewPrivateToAttach = em.getReference(privateCollection1NewPrivateToAttach.getClass(), privateCollection1NewPrivateToAttach.getId());
                attachedPrivateCollection1New.add(privateCollection1NewPrivateToAttach);
            }
            privateCollection1New = attachedPrivateCollection1New;
            profile.setPrivateCollection1(privateCollection1New);
            Collection<Post> attachedPostCollectionNew = new ArrayList<Post>();
            for (Post postCollectionNewPostToAttach : postCollectionNew) {
                postCollectionNewPostToAttach = em.getReference(postCollectionNewPostToAttach.getClass(), postCollectionNewPostToAttach.getId());
                attachedPostCollectionNew.add(postCollectionNewPostToAttach);
            }
            postCollectionNew = attachedPostCollectionNew;
            profile.setPostCollection(postCollectionNew);
            profile = em.merge(profile);
            if (userNameOld != null && !userNameOld.equals(userNameNew)) {
                userNameOld.setProfile(null);
                userNameOld = em.merge(userNameOld);
            }
            if (userNameNew != null && !userNameNew.equals(userNameOld)) {
                userNameNew.setProfile(profile);
                userNameNew = em.merge(userNameNew);
            }
            for (Profile profileCollectionOldProfile : profileCollectionOld) {
                if (!profileCollectionNew.contains(profileCollectionOldProfile)) {
                    profileCollectionOldProfile.getProfileCollection().remove(profile);
                    profileCollectionOldProfile = em.merge(profileCollectionOldProfile);
                }
            }
            for (Profile profileCollectionNewProfile : profileCollectionNew) {
                if (!profileCollectionOld.contains(profileCollectionNewProfile)) {
                    profileCollectionNewProfile.getProfileCollection().add(profile);
                    profileCollectionNewProfile = em.merge(profileCollectionNewProfile);
                }
            }
            for (Profile profileCollection1OldProfile : profileCollection1Old) {
                if (!profileCollection1New.contains(profileCollection1OldProfile)) {
                    profileCollection1OldProfile.getProfileCollection().remove(profile);
                    profileCollection1OldProfile = em.merge(profileCollection1OldProfile);
                }
            }
            for (Profile profileCollection1NewProfile : profileCollection1New) {
                if (!profileCollection1Old.contains(profileCollection1NewProfile)) {
                    profileCollection1NewProfile.getProfileCollection().add(profile);
                    profileCollection1NewProfile = em.merge(profileCollection1NewProfile);
                }
            }
            for (Private privateCollectionNewPrivate : privateCollectionNew) {
                if (!privateCollectionOld.contains(privateCollectionNewPrivate)) {
                    Profile oldSenderIdOfPrivateCollectionNewPrivate = privateCollectionNewPrivate.getSenderId();
                    privateCollectionNewPrivate.setSenderId(profile);
                    privateCollectionNewPrivate = em.merge(privateCollectionNewPrivate);
                    if (oldSenderIdOfPrivateCollectionNewPrivate != null && !oldSenderIdOfPrivateCollectionNewPrivate.equals(profile)) {
                        oldSenderIdOfPrivateCollectionNewPrivate.getPrivateCollection().remove(privateCollectionNewPrivate);
                        oldSenderIdOfPrivateCollectionNewPrivate = em.merge(oldSenderIdOfPrivateCollectionNewPrivate);
                    }
                }
            }
            for (Private privateCollection1NewPrivate : privateCollection1New) {
                if (!privateCollection1Old.contains(privateCollection1NewPrivate)) {
                    Profile oldReceiverIdOfPrivateCollection1NewPrivate = privateCollection1NewPrivate.getReceiverId();
                    privateCollection1NewPrivate.setReceiverId(profile);
                    privateCollection1NewPrivate = em.merge(privateCollection1NewPrivate);
                    if (oldReceiverIdOfPrivateCollection1NewPrivate != null && !oldReceiverIdOfPrivateCollection1NewPrivate.equals(profile)) {
                        oldReceiverIdOfPrivateCollection1NewPrivate.getPrivateCollection1().remove(privateCollection1NewPrivate);
                        oldReceiverIdOfPrivateCollection1NewPrivate = em.merge(oldReceiverIdOfPrivateCollection1NewPrivate);
                    }
                }
            }
            for (Post postCollectionNewPost : postCollectionNew) {
                if (!postCollectionOld.contains(postCollectionNewPost)) {
                    Profile oldIdAuthorOfPostCollectionNewPost = postCollectionNewPost.getIdAuthor();
                    postCollectionNewPost.setIdAuthor(profile);
                    postCollectionNewPost = em.merge(postCollectionNewPost);
                    if (oldIdAuthorOfPostCollectionNewPost != null && !oldIdAuthorOfPostCollectionNewPost.equals(profile)) {
                        oldIdAuthorOfPostCollectionNewPost.getPostCollection().remove(postCollectionNewPost);
                        oldIdAuthorOfPostCollectionNewPost = em.merge(oldIdAuthorOfPostCollectionNewPost);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = profile.getId();
                if (findProfile(id) == null) {
                    throw new NonexistentEntityException("The profile with id " + id + " no longer exists.");
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
            Profile profile;
            try {
                profile = em.getReference(Profile.class, id);
                profile.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The profile with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Private> privateCollectionOrphanCheck = profile.getPrivateCollection();
            for (Private privateCollectionOrphanCheckPrivate : privateCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Profile (" + profile + ") cannot be destroyed since the Private " + privateCollectionOrphanCheckPrivate + " in its privateCollection field has a non-nullable senderId field.");
            }
            Collection<Private> privateCollection1OrphanCheck = profile.getPrivateCollection1();
            for (Private privateCollection1OrphanCheckPrivate : privateCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Profile (" + profile + ") cannot be destroyed since the Private " + privateCollection1OrphanCheckPrivate + " in its privateCollection1 field has a non-nullable receiverId field.");
            }
            Collection<Post> postCollectionOrphanCheck = profile.getPostCollection();
            for (Post postCollectionOrphanCheckPost : postCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Profile (" + profile + ") cannot be destroyed since the Post " + postCollectionOrphanCheckPost + " in its postCollection field has a non-nullable idAuthor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User userName = profile.getUserName();
            if (userName != null) {
                userName.setProfile(null);
                userName = em.merge(userName);
            }
            Collection<Profile> profileCollection = profile.getProfileCollection();
            for (Profile profileCollectionProfile : profileCollection) {
                profileCollectionProfile.getProfileCollection().remove(profile);
                profileCollectionProfile = em.merge(profileCollectionProfile);
            }
            Collection<Profile> profileCollection1 = profile.getProfileCollection1();
            for (Profile profileCollection1Profile : profileCollection1) {
                profileCollection1Profile.getProfileCollection().remove(profile);
                profileCollection1Profile = em.merge(profileCollection1Profile);
            }
            em.remove(profile);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Profile> findProfileEntities() {
        return findProfileEntities(true, -1, -1);
    }

    public List<Profile> findProfileEntities(int maxResults, int firstResult) {
        return findProfileEntities(false, maxResults, firstResult);
    }

    private List<Profile> findProfileEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Profile.class));
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

    public Profile findProfile(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Profile.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfileCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Profile> rt = cq.from(Profile.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Profile findProfileByUserName(String username) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Profile> tq = em.createNamedQuery("Profile.findByUserName", Profile.class)
                    .setParameter("userName", username);

            return tq.getSingleResult();
        } catch(NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
