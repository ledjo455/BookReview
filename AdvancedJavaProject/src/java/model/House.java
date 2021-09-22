/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "house")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "House.findAll", query = "SELECT h FROM House h")
    , @NamedQuery(name = "House.findByName", query = "SELECT h FROM House h WHERE h.name = :name")
})
public class House implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "house_id")
    private Integer houseID;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name")
    private String name;
    
    @OneToMany(mappedBy = "house")
    private List<Book> books;

    public House() {
    }

    public House(String name) {
        this.name = name;
    }

    
    
    public House(Integer houseID, String name, List<Book> books) {
        this.houseID = houseID;
        this.name = name;
        this.books = books;
    }  
    
    
    public Integer getHouseID() {
        return houseID;
    }

    public void setHouseID(Integer houseID) {
        this.houseID = houseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @XmlTransient
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "House{" + "houseID=" + houseID + ", name=" + name + '}';
    }

    
    
}
