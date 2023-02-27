package cz.hronza.skillmeahibernatespringboot.repository;

import cz.hronza.skillmeahibernatespringboot.entity.PersonEntity;
import cz.hronza.skillmeahibernatespringboot.enums.Gender;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    /**
     * načte všechny osoby včetně "fetchovaných asociací"
     * zadáním "left join fetch P.telephoneEntityList" se řeší problém s fetch type LAZY na asociaci
     * podle <a href=https://thorben-janssen.com/lazyinitializationexception/></a>
     *
     * @return seznam osob setříděny dle pohlaví a příjmení
     */
    @Query(value = "select distinct P from PersonEntity P " +
            "left join fetch P.telephoneEntities " +
            "left join fetch P.groupEntities " +
            "order by P.gender, P.name.sureName asc nulls last ")
    List<PersonEntity> findAllFetched();

    /**
     * je stejná jako findAllFetched, navíc má možnost stránkování - ale do selectu negeneruje do limit a offset, tj. vezme si všehcn výskyty a až nad nimi stránkuje
     *
     * @param pageable object for pagination
     * @return stránku seznamu osob setříděný dle pohlaví a příjmení
     */
    @Query(value = "select distinct P from PersonEntity P " +
            "left join fetch P.telephoneEntities " +
            "left join fetch P.groupEntities " +
            "order by P.gender, P.name.sureName asc nulls last ")
    List<PersonEntity> findAllFetchedPagination(Pageable pageable);

    @Query(value = "select count(P) from PersonEntity P ")
    Integer personsNumber();

    Integer countAllBy();

    List<PersonEntity> findByGenderIs(Gender gender, Pageable pageable);

}
