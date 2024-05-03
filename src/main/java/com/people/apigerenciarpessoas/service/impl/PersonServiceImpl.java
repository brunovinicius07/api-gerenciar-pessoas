package com.people.apigerenciarpessoas.service.impl;

import com.people.apigerenciarpessoas.exception.person.PersonPresentException;
import com.people.apigerenciarpessoas.models.dto.request.PersonRequest;
import com.people.apigerenciarpessoas.models.dto.response.PersonResponse;
import com.people.apigerenciarpessoas.models.mapper.PersonMapper;
import com.people.apigerenciarpessoas.repository.PersonRepository;
import com.people.apigerenciarpessoas.service.PersonService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class PersonServiceImpl implements PersonService {

    public final PersonRepository personRepository;

    public final PersonMapper personMapper;

    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    @Override
    @Transactional(readOnly = false)
    public PersonResponse registerPerson(PersonRequest personRequest) {
        existingPerson(personRequest.getCpf());
        return personMapper.toPersonResponse(personRepository.save(personMapper.toPerson(personRequest)));
    }

    public void existingPerson(String cpf){
        personRepository.findPersonByCpf(cpf).ifPresent(people -> {
            throw new PersonPresentException();
        });
    }

    @Override
    public List<PersonResponse> getAllPeople() {
        return null;
    }

    @Override
    public PersonResponse getPersonById(Long idPerson) {
        return null;
    }

    @Override
    public PersonResponse updatePerson(Long idPerson, PersonRequest personRequest) {
        return null;
    }

    @Override
    public String deletePeople(Long idPerson) {
        return null;
    }
}
