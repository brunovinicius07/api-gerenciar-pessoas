package com.people.apigerenciarpessoas.service;

import com.people.apigerenciarpessoas.models.dto.request.PersonRequest;
import com.people.apigerenciarpessoas.models.dto.response.PersonResponse;

import java.util.List;

public interface PersonService {
    PersonResponse registerPerson(PersonRequest personRequest);

    List<PersonResponse> getAllPeople();

    PersonResponse getPersonById(Long idPerson);

    PersonResponse updatePerson(Long idPerson, PersonRequest personRequest);

    String deletePeople(Long idPerson);
}
