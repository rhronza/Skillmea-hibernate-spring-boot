package cz.hronza.skillmeahibernatespringboot.entity;


import cz.hronza.skillmeahibernatespringboot.converter.GenderConverter;
import cz.hronza.skillmeahibernatespringboot.enums.Gender;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Entity pro demontraci konverze atributu mezi hodnotu v databázi a v javě použitím konvertoru {@link GenderConverter }
 * <a href="https://skillmea.cz/student/online-kurzy/java-persistence-jpa-a-hibernate-zaklady/kapitoly/15-konvertovanie-medzi-roznymi-typmi-attributeconverter-67030068-9169-4eee-9baa-667a4ab340fa">...</a>
 * <p>
 * {@code @Enumerated(EnumType.ORDINAL);}
 * {@code @Enumerated(EnumType.STRING}
 * sql příkaz pro vyvoření v databázi:
 * create table Person (id int not null auto_increment, name varchar(256) default null, gender varchar(1) default null, primary key (id));
 * insert into person(name, gender) values("Monika", "W");
 * select * from person;
 * ALTER TABLE person ADD column created TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
 */
@Entity
@Table(name = "person", schema = "skillmea")
public class PersonEntity extends ObcanskyPrukaz {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Embedded
    private Name name;

    @Column(name = "gender", length = 1)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @OneToMany(mappedBy = "person", targetEntity = TelephoneEntity.class, cascade = CascadeType.ALL /*, orphanRemoval = true*/, fetch = FetchType.LAZY)
    // k nastavení cascade a orphanremoval: https://stackoverflow.com/questions/18813341/what-is-the-difference-between-cascadetype-remove-and-orphanremoval-in-jpa
    private Set<TelephoneEntity> telephoneEntityList = new HashSet<>();

    public void addTelephone(TelephoneEntity telephoneEntity) {
        this.telephoneEntityList.add(telephoneEntity);
        telephoneEntity.setPerson(this);
    }

    public void removeTelephone(TelephoneEntity telephoneEntity) {
        this.telephoneEntityList.remove(telephoneEntity);
        telephoneEntity.setPerson(null);
    }

    @ManyToMany(mappedBy = "personEntityList", fetch = FetchType.LAZY)
    Set<GroupEntity> groupEntityList = new HashSet<>();

    public void addPersonGroup(GroupEntity groupEntity) {
        if (!getGroupEntityList().contains(groupEntity)) {
            getGroupEntityList().add(groupEntity);
        }
        if (!groupEntity.getPersonEntityList().contains(this)) {
            groupEntity.getPersonEntityList().add(this);
        }
    }

    public void removePersonGroup(GroupEntity groupEntity) {
        getGroupEntityList().remove(groupEntity);
        groupEntity.getPersonEntityList().remove(this);
    }


    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL /*, orphanRemoval = true*/, fetch = FetchType.LAZY)
    private AddressEntity addressEntity;

    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

    public Set<GroupEntity> getGroupEntityList() {
        return groupEntityList;
    }

    public void setGroupEntityList(Set<GroupEntity> groupEntityList) {
        this.groupEntityList = groupEntityList;
    }


    @Basic
    @Column(name = "created")
    private Timestamp created;

    public PersonEntity() {
    }

    public PersonEntity(Name name, Gender gender) {
        this.name = name;
        this.gender = gender;
        this.created = Timestamp.from(Instant.now());
    }

    public Set<TelephoneEntity> getTelephoneEntityList() {
        return telephoneEntityList;
    }

    public void setTelephoneEntityList(Set<TelephoneEntity> telephoneEntityList) {
        this.telephoneEntityList = telephoneEntityList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonEntity that)) return false;

        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getGender() != null ? getGender().hashCode() : 0);
        result = 31 * result + (getCreated() != null ? getCreated().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String telephones = telephoneEntityList != null ? telephoneEntityList.toString() : "null";
        String groups = groupEntityList != null ? groupEntityList.stream().map(GroupEntity::getId).toList().toString() : "null";
        return new StringJoiner(", ", PersonEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("gender=" + gender)
                .add("name=" + name)
                .add("telephoneEntityList=" + telephones)
                .add("personGroupEntityList of id's =" + groups)
                .add("address id=" + (addressEntity != null ? addressEntity.getId().toString() : "null"))
                .add("created=" + created)
                .toString();
    }
}
