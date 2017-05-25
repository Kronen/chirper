package jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alberto G. Lagos
 */
@Entity
@Table(name = "private")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Private.findAll", query = "SELECT p FROM Private p")
    , @NamedQuery(name = "Private.findById", query = "SELECT p FROM Private p WHERE p.id = :id")
    , @NamedQuery(name = "Private.findByText", query = "SELECT p FROM Private p WHERE p.text = :text")
    , @NamedQuery(name = "Private.findByMarkedRead", query = "SELECT p FROM Private p WHERE p.markedRead = :markedRead")})
public class Private implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "text")
    private String text;
    @Basic(optional = false)
    @Column(name = "marked_read")
    private boolean markedRead;
    @JoinColumn(name = "sender", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Profile sender;
    @JoinColumn(name = "receiver", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Profile receiver;

    public Private() {
    }

    public Private(Integer id) {
        this.id = id;
    }

    public Private(Integer id, String text, boolean markedRead) {
        this.id = id;
        this.text = text;
        this.markedRead = markedRead;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getMarkedRead() {
        return markedRead;
    }

    public void setMarkedRead(boolean markedRead) {
        this.markedRead = markedRead;
    }

    public Profile getSender() {
        return sender;
    }

    public void setSender(Profile sender) {
        this.sender = sender;
    }

    public Profile getReceiver() {
        return receiver;
    }

    public void setReceiver(Profile receiver) {
        this.receiver = receiver;
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
        if (!(object instanceof Private)) {
            return false;
        }
        Private other = (Private) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Private[ id=" + id + " ]";
    }
    
}
