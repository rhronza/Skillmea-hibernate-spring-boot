package cz.hronza.skillmeahibernatespringboot.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.util.StringJoiner;

/**
 * tato třída představuje sloupce v dababázi, v entitě se na ně odkazuji jednou referencí
 * <p>
 * ALTER TABLE person RENAME COLUMN name TO first_name;
 * ALTER TABLE person ADD column sure_name  varchar(256) default null;
 * ALTER TABLE person ADD column title_before  varchar(256) default null;
 * ALTER TABLE person ADD column title_after  varchar(256) default null;
 */

@Embeddable
public class Name implements Serializable {

    @Serial
    private static final long serialVersionUID = -3075628722705460161L;

    @Column(name = "title_before")
    private String titleBeforeName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "sure_name")
    private String sureName;

    @Column(name = "title_after")
    private String titleAfterName;

    public Name() {
    }

    public Name(String titleBeforeName, String firstName, String sureName, String titleAfterName) {
        this.titleBeforeName = titleBeforeName;
        this.firstName = firstName;
        this.sureName = sureName;
        this.titleAfterName = titleAfterName;
    }

    public String getTitleBeforeName() {
        return titleBeforeName;
    }

    public void setTitleBeforeName(String titleBeforeName) {
        this.titleBeforeName = titleBeforeName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSureName() {
        return sureName;
    }

    public void setSureName(String sureName) {
        this.sureName = sureName;
    }

    public String getTitleAfterName() {
        return titleAfterName;
    }

    public void setTitleAfterName(String titleAfterName) {
        this.titleAfterName = titleAfterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name name)) return false;

        if (getTitleBeforeName() != null ? !getTitleBeforeName().equals(name.getTitleBeforeName()) : name.getTitleBeforeName() != null)
            return false;
        if (getFirstName() != null ? !getFirstName().equals(name.getFirstName()) : name.getFirstName() != null)
            return false;
        if (getSureName() != null ? !getSureName().equals(name.getSureName()) : name.getSureName() != null)
            return false;
        return getTitleAfterName() != null ? getTitleAfterName().equals(name.getTitleAfterName()) : name.getTitleAfterName() == null;
    }

    @Override
    public int hashCode() {
        int result = getTitleBeforeName() != null ? getTitleBeforeName().hashCode() : 0;
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getSureName() != null ? getSureName().hashCode() : 0);
        result = 31 * result + (getTitleAfterName() != null ? getTitleAfterName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Name.class.getSimpleName() + "[", "]")
                .add("titleBeforeName='" + titleBeforeName + "'")
                .add("firstName='" + firstName + "'")
                .add("sureName='" + sureName + "'")
                .add("titleAfterName='" + titleAfterName + "'")
                .toString();
    }
}
