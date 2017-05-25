package jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alberto G. Lagos
 */
@Entity
@Table(name = "trending_topics")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrendingTopics.findAll", query = "SELECT t FROM TrendingTopics t")
    , @NamedQuery(name = "TrendingTopics.findById", query = "SELECT t FROM TrendingTopics t WHERE t.id = :id")
    , @NamedQuery(name = "TrendingTopics.findByIdTag", query = "SELECT t FROM TrendingTopics t WHERE t.idTag = :idTag")
    , @NamedQuery(name = "TrendingTopics.findByTagName", query = "SELECT t FROM TrendingTopics t WHERE t.tagName = :tagName")
    , @NamedQuery(name = "TrendingTopics.findByPostCount", query = "SELECT t FROM TrendingTopics t WHERE t.postCount = :postCount")})
public class TrendingTopics implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "id_tag")
    private Integer idTag;
    @Column(name = "tag_name")
    private String tagName;
    @Basic(optional = false)
    @Column(name = "post_count")
    private long postCount;

    public TrendingTopics() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdTag() {
        return idTag;
    }

    public void setIdTag(Integer idTag) {
        this.idTag = idTag;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public long getPostCount() {
        return postCount;
    }

    public void setPostCount(long postCount) {
        this.postCount = postCount;
    }
    
}
