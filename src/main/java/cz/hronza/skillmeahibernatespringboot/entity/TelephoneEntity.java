package cz.hronza.skillmeahibernatespringboot.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Entita Telefon má vzhah N : 1 k entitě Osoba (jedna osoba má více tel.čísel)
 * <p>
 * založení tabulky v db:
 * create table Telephone (
 * id int not null auto_increment,
 * number varchar(40) default null,
 * person_id int(19) default null,
 * created timestamp default current_timestamp,
 * primary key (id)
 * );
 * <p>
 * přidání constrain k tabulkám Telephone a Person:
 * alter table telephone add constraint fk_telefhone_person_id foreign key (person_id) references person(id);
 * <p>
 * výpis všech constrains pro tabulkiu Telephone:
 * SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_NAME='Telephone';
 * <p>
 * <a href= https://skillmea.cz/student/online-kurzy/java-persistence-jpa-a-hibernate-zaklady/kapitoly/20-manytoone-asociacia-ba179d4f-c512-40f0-b18b-84c281405d6a></a>
 */

@Entity
@Table(name = "telephone", schema = "skillmea")
public class TelephoneEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "number", length = 40)
    private String number;

    @ManyToOne(targetEntity = PersonEntity.class)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    // name je atribute (foreign key) z této entity který představuje FK, referencedColumnName je atribut (primary key) z asociované entity!
    // @JoinColumn(foreignKey = @ForeignKey(name = "fk_telefhone_person_id")) // toto je nepoviné
    private PersonEntity person;

    @Basic
    @Column(name = "created")
    private Timestamp created;

    public TelephoneEntity() {
    }

    public TelephoneEntity(String number) {
        this.number = number;
        this.created = Timestamp.from(Instant.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    /**
     * <a href=https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/><a/>
     * @param o input object
     * @return isEquals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TelephoneEntity that = (TelephoneEntity) o;

        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (person != null ? person.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TelephoneEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("number='" + number + "'")
//                .add("person=" + person)
                .add("created=" + created)
                .toString();
    }
}
