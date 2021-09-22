/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "review")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r")
    , @NamedQuery(name = "Review.findByRating", query = "SELECT r FROM Review r WHERE r.rating = :rating")
    , @NamedQuery(name = "Review.findByEmail", query = "SELECT r FROM Review r WHERE r.reviewPK.email = :email")
    , @NamedQuery(name = "Review.findByBookID", query = "SELECT r FROM Review r WHERE r.reviewPK.bookID = :bookID")})
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReviewPK reviewPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rating")
    private int rating;
    @Lob
    @Size(max = 65535)
    @Column(name = "comment")
    private String comment;
    
    @JoinColumn(name = "book_id", referencedColumnName = "book_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @XmlTransient
    private Book book;
    
    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Review() {
    }

    public Review(ReviewPK reviewPK) {
        this.reviewPK = reviewPK;
    }

    public Review(ReviewPK reviewPK, int rating) {
        this.reviewPK = reviewPK;
        this.rating = rating;
    }

    public Review(String email, int bookID) {
        this.reviewPK = new ReviewPK(email, bookID);
    }

    public ReviewPK getReviewPK() {
        return reviewPK;
    }

    public void setReviewPK(ReviewPK reviewPK) {
        this.reviewPK = reviewPK;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
        hash += (reviewPK != null ? reviewPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Review)) {
            return false;
        }
        Review other = (Review) object;
        if ((this.reviewPK == null && other.reviewPK != null) || (this.reviewPK != null && !this.reviewPK.equals(other.reviewPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Review[ reviewPK=" + reviewPK + " ]";
    }
    
}
