/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kronen
 */
@Entity
@Table(name = "profile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profile.findAll", query = "SELECT p FROM Profile p")
    , @NamedQuery(name = "Profile.findById", query = "SELECT p FROM Profile p WHERE p.id = :id")
    , @NamedQuery(name = "Profile.findByEmail", query = "SELECT p FROM Profile p WHERE p.email = :email")
    , @NamedQuery(name = "Profile.findByLocation", query = "SELECT p FROM Profile p WHERE p.location = :location")
    , @NamedQuery(name = "Profile.findByWebsite", query = "SELECT p FROM Profile p WHERE p.website = :website")
    , @NamedQuery(name = "Profile.findByFullName", query = "SELECT p FROM Profile p WHERE p.fullName = :fullName")
    , @NamedQuery(name = "Profile.findByNumPosts", query = "SELECT p FROM Profile p WHERE p.numPosts = :numPosts")
    , @NamedQuery(name = "Profile.findByPhoto", query = "SELECT p FROM Profile p WHERE p.photo = :photo")
    , @NamedQuery(name = "Profile.findByUserName", query = "SELECT p FROM Profile p WHERE p.user.userName = :userName")})
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "location")
    private String location;
    @Column(name = "website")
    private String website;
    @Column(name = "full_name")
    private String fullName;
    @Basic(optional = false)
    @Column(name = "num_posts")
    private int numPosts;
    @Column(name = "photo")
    private String photo;
    @JoinTable(name = "follower_followee", joinColumns = {
        @JoinColumn(name = "followee", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "follower", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Profile> profileCollection;
    @ManyToMany(mappedBy = "profileCollection")
    private Collection<Profile> profileCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    private Collection<Private> privateCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiver")
    private Collection<Private> privateCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    @OrderBy("pubDate DESC")
    private Collection<Post> postCollection;
    @JoinColumn(name = "user", referencedColumnName = "user_name")
    @ManyToOne(optional = false)
    private User user;

    public Profile() {
    }

    public Profile(Integer id) {
        this.id = id;
    }

    public Profile(Integer id, int numPosts) {
        this.id = id;
        this.numPosts = numPosts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getNumPosts() {
        return numPosts;
    }

    public void setNumPosts(int numPosts) {
        this.numPosts = numPosts;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @XmlTransient
    public Collection<Profile> getProfileCollection() {
        return profileCollection;
    }

    public void setProfileCollection(Collection<Profile> profileCollection) {
        this.profileCollection = profileCollection;
    }

    @XmlTransient
    public Collection<Profile> getProfileCollection1() {
        return profileCollection1;
    }

    public void setProfileCollection1(Collection<Profile> profileCollection1) {
        this.profileCollection1 = profileCollection1;
    }

    @XmlTransient
    public Collection<Private> getPrivateCollection() {
        return privateCollection;
    }

    public void setPrivateCollection(Collection<Private> privateCollection) {
        this.privateCollection = privateCollection;
    }

    @XmlTransient
    public Collection<Private> getPrivateCollection1() {
        return privateCollection1;
    }

    public void setPrivateCollection1(Collection<Private> privateCollection1) {
        this.privateCollection1 = privateCollection1;
    }

    @XmlTransient
    public Collection<Post> getPostCollection() {
        return postCollection;
    }

    public void setPostCollection(Collection<Post> postCollection) {
        this.postCollection = postCollection;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profile)) {
            return false;
        }
        Profile other = (Profile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Profile[ id=" + id + " ]";
    }
    
}
