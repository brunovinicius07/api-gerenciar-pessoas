package com.people.apigerenciarpessoas.service.impl;

import com.people.apigerenciarpessoas.exception.person.PersonNotFoundException;
import com.people.apigerenciarpessoas.exception.person.PersonPresentException;
import com.people.apigerenciarpessoas.models.dto.request.PersonRequest;
import com.people.apigerenciarpessoas.models.dto.response.PersonResponse;
import com.people.apigerenciarpessoas.models.entity.Person;
import com.people.apigerenciarpessoas.models.mapper.PersonMapper;
import com.people.apigerenciarpessoas.repository.PersonRepository;
import com.people.apigerenciarpessoas.service.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
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


    @Override
    @Transactional(readOnly = true)
    public void existingPerson(String cpf){
        personRepository.findPersonByCpf(cpf).ifPresent(people -> {
            throw new PersonPresentException();
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonResponse> getAllPeople() {
        return personMapper.toListPersonResponse(personRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public PersonResponse getPersonById(Long idPerson) {
        return personMapper.toPersonResponse(validatePerson(idPerson));
    }

    @Override
    @Transactional(readOnly = true)
    public Person validatePerson(Long idPerson) {
        return personRepository.findById(idPerson).orElseThrow(PersonNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = false)
    public PersonResponse updatePerson(Long idPerson, PersonRequest personRequest) {
        existingPerson(personRequest.getCpf());
        Person person = validatePerson(idPerson);

        person.setName(personRequest.getName());
        person.setBirthDate(personRequest.getBirthDate());
        person.setCpf(personRequest.getCpf());

        return personMapper.toPersonResponse(personRepository.save(person));
    }

    @Override
    @Transactional(readOnly = false)
    public String deletePeople(Long idPerson) {
        Person person = validatePerson(idPerson);

        if (person != null){
            personRepository.delete(person);
            return "Pessoa com id " + idPerson + " deletada com sucesso!";
        }
        return "Erro ao deletar!";
    }
}
