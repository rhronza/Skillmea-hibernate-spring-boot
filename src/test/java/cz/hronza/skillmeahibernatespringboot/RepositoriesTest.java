package cz.hronza.skillmeahibernatespringboot;

import cz.hronza.skillmeahibernatespringboot.entity.AddressEntity;
import cz.hronza.skillmeahibernatespringboot.entity.GroupEntity;
import cz.hronza.skillmeahibernatespringboot.entity.Name;
import cz.hronza.skillmeahibernatespringboot.entity.PersonEntity;
import cz.hronza.skillmeahibernatespringboot.entity.TelephoneEntity;
import cz.hronza.skillmeahibernatespringboot.enums.Gender;
import cz.hronza.skillmeahibernatespringboot.repository.AddressRepository;
import cz.hronza.skillmeahibernatespringboot.repository.GroupRepository;
import cz.hronza.skillmeahibernatespringboot.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class RepositoriesTest {

    public static final String TITLE_BEFORE_NAME = "Ing";
    public static final String FIRST_NAME = "Karel";
    public static final String SURE_NAME = "Šťáva";
    public static final String TITLE_AFTER_NAME = "eee";
    public static final Gender GENDER = Gender.MAN;
    public static final String IDENTITY_CARD_NUMBER = "TT 526378";
    public static final long EXISTING_GROUP_ID = 1L;
    public static final long NON_EXIST_GROUP_ID = 99999999L;
    public static final String TITLE_GROUP_01 = "group01";
    public static final String TITLE_GROUP_02 = "group02";
    public static final String TITLE_GROUP_03 = "group03";
    public static final String TELEPHONE_NUMBER_1 = "666 999 885";
    public static final String TELEPHONE_NUMBER_2 = "414 552 985";
    public static final String TELEPHONE_NUMBER_3 = "556 333 963";
    public static final String TELEPHONE_NUMBER_4 = "999 666 777";
    public static final String STREET = "Lodžská 333";
    public static final String CITY = "Praha 81";
    public static final String POST_CODE = "181 25";
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void findAllTest() {

        List<PersonEntity> persons = personRepository.findAll();
        assertEquals(7, persons.size());

        assertTelephones(persons);

        assertGroups(persons);

        assertAddress(persons);
    }


    @Test
    void personPageableTest() {
        List<PersonEntity> allUnsorted = personRepository.findAll();

        /* tato reference generuje v selectu limit i offset! */
        Page<PersonEntity> personPaginationSortedNoSql = personRepository
                .findAll(
                        PageRequest.of(1, 3,
                                Sort.by(Sort.Direction.ASC, "gender").and(Sort.by(Sort.Direction.ASC, "name.sureName"))
                        )
                );

        /* tato reference generuje v selectu limit i offset! */
        List<PersonEntity> personByGenderIs = personRepository.findByGenderIs(Gender.MAN, PageRequest.of(0, 4, Sort.Direction.DESC, "name.sureName"));

        /* tyto reference NEGENERUJÍ v selectu limit a offset! */
        List<PersonEntity> page01 = personRepository.findAllFetchedPagination(PageRequest.of(0, 3));
        List<PersonEntity> page02 = personRepository.findAllFetchedPagination(PageRequest.of(1, 3));
        List<PersonEntity> page03 = personRepository.findAllFetchedPagination(PageRequest.of(2, 3));
        List<PersonEntity> page04 = personRepository.findAllFetchedPagination(PageRequest.of(3, 3));

        assertEquals(7, allUnsorted.size());
        assertEquals(3, personPaginationSortedNoSql.get().toList().size());
        assertEquals(4, personByGenderIs.size());

        assertEquals(3, page01.size());
        assertEquals(3, page02.size());
        assertEquals(1, page03.size());
        assertEquals(0, page04.size());
    }

    @Test
    void countTest() {
        Integer number = personRepository.personsNumber();
        assertEquals(7, number);

        Integer countAllBy = personRepository.countAllBy();
        assertEquals(7, countAllBy);
    }

    private void assertAddress(List<PersonEntity> persons) {
        //asssert address:
        AddressEntity addressEntity = persons.get(1).getAddressEntity();
        assertEquals(STREET, addressEntity.getStreet());
        assertEquals(CITY, addressEntity.getCity());
        assertEquals(POST_CODE, addressEntity.getPostCode());
        AddressEntity addressEntity1 = addressRepository.selectAll().get(0);
        assertThat(addressEntity, hasToString(addressEntity1.toString()));
        assertThat(addressEntity, sameInstance(addressEntity1));
        assertThat(addressEntity, instanceOf(AddressEntity.class));
    }

    private void assertGroups(List<PersonEntity> persons) {
        // assert groups:
        List<GroupEntity> groups = groupRepository.findAll();
        assertEquals(3, groups.size());

        //assert groups for person:
        Set<GroupEntity> groupEntities = persons.get(0).getGroupEntities();
        assertEquals(1, groupEntities.size());
        groupEntities.stream().filter(e -> e.equals(groupRepository.getReferenceById(1L))).findAny().ifPresent(group -> assertEquals(TITLE_GROUP_01, group.getTitle()));

        groupEntities = persons.get(1).getGroupEntities();
        assertEquals(0, groupEntities.size());

        groupEntities = persons.get(2).getGroupEntities();
        assertEquals(3, groupEntities.size());
        groupEntities.stream().filter(e -> e.equals(groupRepository.getReferenceById(1L))).findAny().ifPresent(group -> assertEquals(TITLE_GROUP_01, group.getTitle()));
        groupEntities.stream().filter(e -> e.equals(groupRepository.getReferenceById(2L))).findAny().ifPresent(group -> assertEquals(TITLE_GROUP_02, group.getTitle()));
        groupEntities.stream().filter(e -> e.equals(groupRepository.getReferenceById(3L))).findAny().ifPresent(group -> assertEquals(TITLE_GROUP_03, group.getTitle()));

        groupEntities = persons.get(4).getGroupEntities();
        assertEquals(2, groupEntities.size());
        groupEntities.stream().filter(e -> e.equals(groupRepository.getReferenceById(2L))).findAny().ifPresent(group -> assertEquals(TITLE_GROUP_02, group.getTitle()));
        groupEntities.stream().filter(e -> e.equals(groupRepository.getReferenceById(3L))).findAny().ifPresent(group -> assertEquals(TITLE_GROUP_03, group.getTitle()));

        Optional<GroupEntity> groupEntity = groupRepository.findById(EXISTING_GROUP_ID);
        assertTrue(groupEntity.isPresent());
        assertEquals(1L, groupEntity.get().getId());

        Optional<GroupEntity> groupEntityNotExisting = groupRepository.findById(NON_EXIST_GROUP_ID);
        assertFalse(groupEntityNotExisting.isPresent());
    }

    private void assertTelephones(List<PersonEntity> persons) {
        //assert telephones:
        assertEquals(2, persons.get(0).getTelephoneEntities().size());

        PersonEntity person = persons.get(0);
        person.getTelephoneEntities().stream().filter(e -> e.getNumber().equals(TELEPHONE_NUMBER_1)).findAny().ifPresent(telephone -> assertEquals(TELEPHONE_NUMBER_1, telephone.getNumber()));
        person.getTelephoneEntities().stream().filter(e -> e.getNumber().equals(TELEPHONE_NUMBER_2)).findAny().ifPresent(telephone -> assertEquals(TELEPHONE_NUMBER_2, telephone.getNumber()));

        person = persons.get(5);
        assertEquals(0, person.getTelephoneEntities().size());

        person = persons.get(6);
        assertEquals(1, person.getTelephoneEntities().size());
        person.getTelephoneEntities().stream().filter(e -> e.getNumber().equals(TELEPHONE_NUMBER_3)).findAny().ifPresent(telephone -> assertEquals(TELEPHONE_NUMBER_3, telephone.getNumber()));
    }

    @BeforeEach
    public void initPersonTable() {
        GroupEntity group01 = groupRepository.save(new GroupEntity(TITLE_GROUP_01));
        GroupEntity group02 = groupRepository.save(new GroupEntity(TITLE_GROUP_02));
        GroupEntity group03 = groupRepository.save(new GroupEntity(TITLE_GROUP_03));

        PersonEntity person01 = new PersonEntity(new Name("Ing", "Roman", "Hronza", "bla"), Gender.MAN);
        person01.setCisloOp("UU 235689");
        person01.addTelephone(new TelephoneEntity(TELEPHONE_NUMBER_1));
        person01.addTelephone(new TelephoneEntity(TELEPHONE_NUMBER_2));
        person01.addPersonGroup(group03);
        personRepository.save(person01);

        PersonEntity person02 = new PersonEntity(new Name("Ing", "Monika", "Hronzová", "ddd"), Gender.WOMAN);
        person02.setCisloOp("FF 748596");
        person02.addTelephone(new TelephoneEntity("688 852 975"));
        //přidání adresy k osobě:
        person02.setAddressEntity(new AddressEntity("Mazurská 525", "Praha 8", "181 00"));
        person02 = personRepository.save(person02);
        //update adresy:
        person02.setAddressEntity(new AddressEntity(STREET, CITY, POST_CODE));
        personRepository.save(person02);
        personRepository.flush();


        PersonEntity person03 = new PersonEntity(new Name(TITLE_BEFORE_NAME, FIRST_NAME, SURE_NAME, TITLE_AFTER_NAME), GENDER);
        person03.setCisloOp(IDENTITY_CARD_NUMBER);
        person03.addTelephone(new TelephoneEntity(TELEPHONE_NUMBER_4));
        person03.addPersonGroup(group01);
        person03.addPersonGroup(group02);
        person03.addPersonGroup(group03);
        personRepository.save(person03);

        PersonEntity person04 = new PersonEntity(new Name("Ing", "Hana", "Šťávová", "fff"), Gender.WOMAN);
        person04.setCisloOp("AA 748596");
        person04.addTelephone(new TelephoneEntity("725 896 425"));
        person04.addPersonGroup(group01);
        personRepository.save(person04);

        PersonEntity person05 = new PersonEntity(new Name("Dr", "Gabriel", "Geibelhauser", "Csc"), Gender.MAN);
        person05.setCisloOp("DD 4158974");
        person05.addTelephone(new TelephoneEntity("725 333 666"));
        person05.addPersonGroup(group02);
        person05.addPersonGroup(group03);
        personRepository.save(person05);

        PersonEntity person06 = new PersonEntity(new Name("MuDr", "Gabriela", "Osvaldová", "sdzp"), Gender.WOMAN);
        person06.setCisloOp("LL 859641");
        personRepository.save(person06);

        PersonEntity person07 = new PersonEntity(new Name("Mgr", "Petr", "Šťastný", "bdzb"), Gender.MAN);
        person07.setCisloOp("HH 968574");
        person07.addTelephone(new TelephoneEntity(TELEPHONE_NUMBER_3));
        personRepository.save(person07);
    }
}
