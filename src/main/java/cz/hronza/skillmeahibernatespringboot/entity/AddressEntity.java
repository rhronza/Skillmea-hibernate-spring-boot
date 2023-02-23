package cz.hronza.skillmeahibernatespringboot.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "address" )
public class AddressEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "street",  length = 100)
    private String street;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "post_code",  length = 10)
    private String postCode;
    
    @OneToOne
    private PersonEntity person;

    @Column(name = "created")
    private Timestamp created;

    public AddressEntity() {
    }

    public AddressEntity( String street, String city, String postCode, PersonEntity personEntity) {
        this.id = null;
        this.street = street;
        this.city = city;
        this.postCode = postCode;
        this.person = personEntity;
        this.created = Timestamp.from(Instant.now());
    }

    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity personEntity) {
        this.person = personEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return Objects.equals(that.getId(), ((AddressEntity) o).getId());
    }


    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getStreet() != null ? getStreet().hashCode() : 0);
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        result = 31 * result + (getPostCode() != null ? getPostCode().hashCode() : 0);
        result = 31 * result + (getPerson() != null ? getPerson().hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddressEntity{");
        sb.append("id=").append(id);
        sb.append(", street='").append(street).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", postCode='").append(postCode).append('\'');
        sb.append(", person=").append(person);
        sb.append(", created=").append(created);
        sb.append('}');
        return sb.toString();
    }
}
