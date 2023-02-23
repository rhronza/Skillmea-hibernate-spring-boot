package cz.hronza.skillmeahibernatespringboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;


/**
 * Entita skupiny osob (PersonGropup) se vztahem M:N k entitě Person, asociace je navázána přes vazební tabulku
 */
@Entity
@Table(name = "person_group")
public class GroupEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "created", nullable = false)
    private Timestamp created;

    public Set<PersonEntity> getPersonEntityList() {
        return personEntityList;
    }

    public void setPersonEntityList(Set<PersonEntity> personEntityList) {
        this.personEntityList = personEntityList;
    }

    @ManyToMany
    @JoinTable(name = "person_telephone_connection_table",
            joinColumns = @JoinColumn(name = "group_id"), foreignKey = @ForeignKey(name = "fk_group_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "fk_person_id"))
    )
    private Set<PersonEntity> personEntityList;

    public void addPerson(PersonEntity person) {
        if (!getPersonEntityList().contains(person)) {
            getPersonEntityList().add(person);
        }
        if (!person.getGroupEntityList().contains(this)) {
            person.getGroupEntityList().add(this);
        }
    }

    public void removePerson(PersonEntity personEntity) {
        getPersonEntityList().remove(personEntity);
        personEntity.getGroupEntityList().remove(this);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        if (o == null || getClass() != o.getClass()) return false;

        GroupEntity that = (GroupEntity) o;

        return Objects.equals(getId(), that.getId());

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GroupEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("title='" + title + "'")
                .add("created=" + created)
                .add("personEntityList=" + personEntityList)
                .toString();
    }
}
