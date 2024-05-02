package com.people.apigerenciarpessoas.service.impl;

import com.people.apigerenciarpessoas.models.dto.request.PersonRequest;
import com.people.apigerenciarpessoas.models.dto.response.PersonResponse;
import com.people.apigerenciarpessoas.service.PersonService;

import java.util.List;

public class PersonServiceImpl implements PersonService {
    @Override
    public PersonResponse registerPerson(PersonRequest personRequest) {
        return null;
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
