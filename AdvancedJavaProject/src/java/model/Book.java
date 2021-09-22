/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "book")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT m FROM Book m")
    , @NamedQuery(name = "Book.findByBookID", query = "SELECT m FROM Book m WHERE m.bookID = :bookID")
    , @NamedQuery(name = "Book.findByTitle", query = "SELECT m FROM Book m WHERE m.title = :title")
    , @NamedQuery(name = "Book.findByReleaseDate", query = "SELECT m FROM Book m WHERE m.releaseDate = :releaseDate")
    , @NamedQuery(name = "Book.findByGenre", query = "SELECT m FROM Book m WHERE m.genre = :genre")
    , @NamedQuery(name = "Book.findByRating", query = "SELECT m FROM Book m WHERE m.rating = :rating")
    , @NamedQuery(name = "Book.findByThumbnail", query = "SELECT m FROM Book m WHERE m.thumbnail = :thumbnail")})
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "book_id")
    private Integer bookID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "synopsis")
    private String synopsis;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "genre")
    private String genre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "rating")
    private String rating;
    @Size(max = 256)
    @Column(name = "thumbnail")
    private String thumbnail;
    
    @ManyToMany(mappedBy = "bookList")
    @XmlTransient
    private List<Author> authorList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    @XmlTransient
    private List<Review> reviewList;
    
    @JoinColumn(name = "house_id", referencedColumnName = "house_id", updatable = false)
    @ManyToOne(optional = false)
    @XmlTransient
    private House house;

    public Book() {}

    public Book(Integer bookID) {
        this.bookID = bookID;
    }

    public Book(Integer bookID, String title, Date releaseDate, String synopsis, String genre, String rating, String thumbnail, List<Author> authorList, List<Review> reviewList, House house) {
        this.bookID = bookID;
        this.title = title;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
        this.genre = genre;
        this.rating = rating;
        this.thumbnail = thumbnail;
        this.authorList = authorList;
        this.reviewList = reviewList;
        this.house = house;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }


    
    public Integer getBookID() {
        return bookID;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @XmlTransient
    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    @XmlTransient
    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public String getAuthor(){
        System.out.println(authorList);
        if(authorList == null || authorList.size() == 0){
            return "none";
        }
        
        return authorList.get(0).getName();
        
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookID != null ? bookID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.bookID == null && other.bookID != null) || (this.bookID != null && !this.bookID.equals(other.bookID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Book[ bookID=" + bookID + " ]";
    }
    
}
