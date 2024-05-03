package com.people.apigerenciarpessoas.service;

import com.people.apigerenciarpessoas.models.dto.request.PersonRequest;
import com.people.apigerenciarpessoas.models.dto.response.PersonResponse;
import com.people.apigerenciarpessoas.models.entity.Person;

import java.util.List;

public interface PersonService {
    PersonResponse registerPerson(PersonRequest personRequest);

    void existingPerson(String cpf);

    List<PersonResponse> getAllPeople();

    PersonResponse getPersonById(Long idPerson);

    Person validatePerson(Long idPerson);

    PersonResponse updatePerson(Long idPerson, PersonRequest personRequest);

    String deletePeople(Long idPerson);
}
