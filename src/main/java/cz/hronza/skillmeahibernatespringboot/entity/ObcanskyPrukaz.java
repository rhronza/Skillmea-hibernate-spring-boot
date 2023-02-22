package cz.hronza.skillmeahibernatespringboot.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Tato řída extenduje entitu Person, má anotaci @MappedSuperclass a nepotřebuje konstruktor, pouze settery a gettery
 * nemusí být uvedena v persistence.xml
 * do db přidá se:
 * ALTER TABLE person ADD column cislo_op varchar(100) default null;
 * Více o mapování dědičnosti v Hibernate na <a href=https://www.baeldung.com/hibernate-inheritance></a>
 */
@MappedSuperclass
public class ObcanskyPrukaz {

    @Column(name = "cislo_op")
    private String cisloOp;

    public String getCisloOp() {
        return cisloOp;
    }

    public void setCisloOp(String cisloOp) {
        this.cisloOp = cisloOp;
    }
}
