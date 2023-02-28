package cz.hronza.skillmeahibernatespringboot;

import cz.hronza.skillmeahibernatespringboot.entity.Name;
import cz.hronza.skillmeahibernatespringboot.entity.PersonEntity;
import cz.hronza.skillmeahibernatespringboot.enums.Gender;
import cz.hronza.skillmeahibernatespringboot.service.CommonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ServicesTest {

    public static final long ID1 = 222L;
    public static final String EXCLUDED_FIELD_1 = "id";
    public static final String EXCLUDED_FIELD_2 = "created";
    public static final String FIRST_NAME = "Roman";
    public static final String SURE_NAME = "hronza";
    @Autowired
    private CommonService commonService;

    public static final long ID2 = 1111L;

    @Test
    void runClassMethodByReflection() throws InvocationTargetException, IllegalAccessException {

        PersonEntity personEntitySource = new PersonEntity(new Name("Ing", FIRST_NAME, SURE_NAME, "eee"), Gender.MAN);
        personEntitySource.setCreated(Timestamp.valueOf(LocalDateTime.parse("2021-06-05T14:00")));
        personEntitySource.setId(ID1);

        PersonEntity personEntityTarget = new PersonEntity();
        personEntityTarget.setId(ID2);
        personEntityTarget.setCreated(Timestamp.valueOf(LocalDateTime.parse("2020-06-05T12:00")));

        personEntityTarget = commonService
                .updateInstanceByAnotherInstanceTheSameClass(
                        personEntitySource,
                        personEntityTarget,
                        List.of(EXCLUDED_FIELD_1, EXCLUDED_FIELD_2));

        assertEquals(ID2, personEntityTarget.getId());
        assertEquals(FIRST_NAME, personEntityTarget.getName().getFirstName());
        assertEquals(SURE_NAME, personEntityTarget.getName().getSureName());
    }
}
