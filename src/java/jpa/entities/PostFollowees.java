/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kronen
 */
@Entity
@Table(name = "post_followees")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PostFollowees.findAll", query = "SELECT p FROM PostFollowees p")
    , @NamedQuery(name = "PostFollowees.findById", query = "SELECT p FROM PostFollowees p WHERE p.id = :id")
    , @NamedQuery(name = "PostFollowees.findByIdFollower", query = "SELECT p FROM PostFollowees p WHERE p.idFollower = :idFollower")
    , @NamedQuery(name = "PostFollowees.findByFollower", query = "SELECT p FROM PostFollowees p WHERE p.follower = :follower")
    , @NamedQuery(name = "PostFollowees.findByFollowee", query = "SELECT p FROM PostFollowees p WHERE p.followee = :followee")
    , @NamedQuery(name = "PostFollowees.findByText", query = "SELECT p FROM PostFollowees p WHERE p.text = :text")
    , @NamedQuery(name = "PostFollowees.findByOriginalPostId", query = "SELECT p FROM PostFollowees p WHERE p.originalPostId = :originalPostId")
    , @NamedQuery(name = "PostFollowees.findByPubDate", query = "SELECT p FROM PostFollowees p WHERE p.pubDate = :pubDate")
    , @NamedQuery(name = "PostFollowees.findByLatitude", query = "SELECT p FROM PostFollowees p WHERE p.latitude = :latitude")
    , @NamedQuery(name = "PostFollowees.findByLongitude", query = "SELECT p FROM PostFollowees p WHERE p.longitude = :longitude")
    , @NamedQuery(name = "PostFollowees.findByLikes", query = "SELECT p FROM PostFollowees p WHERE p.likes = :likes")
    , @NamedQuery(name = "PostFollowees.findByIdFollowerOrdered", query = "SELECT p FROM PostFollowees p WHERE p.idFollower = :idFollower ORDER BY p.pubDate DESC")})
public class PostFollowees implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "id_follower")
    private int idFollower;
    @Basic(optional = false)
    @Column(name = "follower")
    private String follower;
    @Basic(optional = false)
    @Column(name = "followee")
    private String followee;
    @Basic(optional = false)
    @Column(name = "text")
    private String text;
    @Column(name = "original_post_id")
    private Integer originalPostId;
    @Basic(optional = false)
    @Column(name = "pub_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pubDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private BigDecimal latitude;
    @Column(name = "longitude")
    private BigDecimal longitude;
    @Basic(optional = false)
    @Column(name = "likes")
    private int likes;

    public PostFollowees() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdFollower() {
        return idFollower;
    }

    public void setIdFollower(int idFollower) {
        this.idFollower = idFollower;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowee() {
        return followee;
    }

    public void setFollowee(String followee) {
        this.followee = followee;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getOriginalPostId() {
        return originalPostId;
    }

    public void setOriginalPostId(Integer originalPostId) {
        this.originalPostId = originalPostId;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
    
}
