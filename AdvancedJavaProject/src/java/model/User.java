
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
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
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT m FROM User m")
    , @NamedQuery(name = "User.findByName", query = "SELECT m FROM User m WHERE m.name = :name")
    , @NamedQuery(name = "User.findBySurname", query = "SELECT m FROM User m WHERE m.surname = :surname")
    , @NamedQuery(name = "User.findByEmail", query = "SELECT m FROM User m WHERE m.email = :email")
    , @NamedQuery(name = "User.findByCredential", query = "SELECT m FROM User m WHERE m.credential = :credential") 
    , @NamedQuery(name = "User.findByEmailAndPassword", query = "SELECT m FROM User m WHERE m.email = :email and m.password = :password")
    , @NamedQuery(name = "User.Update", query = "UPDATE User m SET m.surname= :surname, m.name= :name, m.email= :email, m.password= :password WHERE m.email= :oldEmail")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT m FROM User m WHERE m.password = :password")})
public class User implements Serializable {
    
    public static enum EnumCredentials{
        ADMIN, STANDARD
    }

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "surname")
    private String surname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "email")
    private String email;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "password")
    @XmlTransient
    private String password;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @XmlTransient
    private List<Review> reviewList;
    
    @NotNull
    @Basic(optional = false)
    @Enumerated
    @Column(name = "credential")
    private EnumCredentials credential;
    
    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public User(String name, String surname, String email, String password, List<Review> reviewList, EnumCredentials credential) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.reviewList = reviewList;
        this.credential = credential;
    }

    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EnumCredentials getCredential() {
        return credential;
    }

    public void setCredential(EnumCredentials credential) {
        this.credential = credential;
    }

    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.User[ email=" + email + " ]";
    }
    
}
