package cz.hronza.skillmeahibernatespringboot.repository;

import cz.hronza.skillmeahibernatespringboot.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * repository pro PersonEntity
 */
@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    /**
     * metoda načte všechny výskyty z db tabulky
     * pokud bych se odkázal na reference groupList a telephoneList, tak skončí výjimkou LazyInitializationException,
     * protože groupList a telephoneList jsou fetch type LAZY,
     * dá se řešit přes query (zadáním left join fetch P.telephoneEntityList - viz následující metada)
     *
     * @return seznam všech osob
     */
    @Override
    List<PersonEntity> findAll();
}
