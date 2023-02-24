package cz.hronza.skillmeahibernatespringboot.service;

import cz.hronza.skillmeahibernatespringboot.entity.PersonEntity;
import cz.hronza.skillmeahibernatespringboot.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public List<PersonEntity> findAll() {
        return personRepository.findAll();
    }
}
