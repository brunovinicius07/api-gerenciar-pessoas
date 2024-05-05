package com.people.apigerenciarpessoas.impl;

import com.people.apigerenciarpessoas.models.dto.request.PersonRequest;
import com.people.apigerenciarpessoas.models.dto.response.AddressResponse;
import com.people.apigerenciarpessoas.models.dto.response.PersonResponse;
import com.people.apigerenciarpessoas.models.entity.Address;
import com.people.apigerenciarpessoas.models.entity.Person;
import com.people.apigerenciarpessoas.models.mapper.PersonMapper;
import com.people.apigerenciarpessoas.repository.PersonRepository;
import com.people.apigerenciarpessoas.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class PersonServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "João";
    public static final LocalDate BIRTH_DATE = LocalDate.of(1997, 7, 15);
    public static final String CPF = "11122233344";
    public static final List<Address> ADDRESSES = new ArrayList<>();
    public static final List<AddressResponse> ADDRESS_RESPONSES = new ArrayList<>();
    public static final int INDEX = 0;

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private PersonMapper personMapper;

    private PersonResponse personResponse;
    private PersonRequest personRequest;
    private Optional<Person> optionalPerson;
    private Person person;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPeople();
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(personMapper.toPersonResponse(Mockito.any())).thenReturn(personResponse);
        when(personRepository.save(Mockito.any())).thenReturn(person);

        personService.existingPerson(personRequest.getCpf());
        PersonResponse response = personService.registerPerson(personRequest);

        assertNotNull(response);
        assertEquals(PersonResponse.class, response.getClass());
        assertEquals(ID, response.getIdPerson());
        assertEquals(NAME, response.getName());
        assertEquals(BIRTH_DATE, response.getBirthDate());
        assertEquals(CPF, response.getCpf());
    }

    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException() {
        when(personRepository.findById(Mockito.anyLong())).thenReturn(optionalPerson);
        when(personMapper.toPersonResponse(Mockito.any())).thenReturn(personResponse);

        try {
            optionalPerson.get().setIdPerson(2L);
            personService.registerPerson(personRequest);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfPeople() {
        when(personRepository.findAll()).thenReturn(List.of(person));
        when(personMapper.toListPersonResponse(Mockito.any())).thenReturn(List.of(personResponse));

        List<PersonResponse> response = personService.getAllPeople();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(PersonResponse.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getIdPerson());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(BIRTH_DATE, response.get(INDEX).getBirthDate());
        assertEquals(CPF, response.get(INDEX).getCpf());
    }

    @Test
    void whenValidateListPeopleSuccess() {
        List<Person> peopleList = new ArrayList<>();
        peopleList.add(person);
        when(personRepository.findAll()).thenReturn(peopleList);

        when(personMapper.toListPersonResponse(Mockito.any())).thenReturn(List.of(personResponse));
        List<PersonResponse> response = personService.getAllPeople();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(ID, response.get(0).getIdPerson());
        assertEquals(NAME, response.get(0).getName());
        assertEquals(BIRTH_DATE, response.get(0).getBirthDate());

        verify(personRepository, times(1)).findAll();
    }

    @Test
    void whenValidateListPeopleThenReturnAnListOfPeople() {
        when(personRepository.findAll()).thenReturn(Collections.emptyList());

        try {
            personService.getAllPeople();
        } catch (Exception ex) {
            assertEquals("Pessoa não localizada", ex.getMessage());
        }
    }

    @Test
    void whenFindByIdThenReturnAnPeopleInstance() {
        when(personRepository.findById(Mockito.anyLong())).thenReturn(optionalPerson);
        when(personMapper.toPersonResponse(Mockito.any())).thenReturn(personResponse);

        PersonResponse response = personService.getPersonById(ID);

        assertNotNull(response);
        assertEquals(PersonResponse.class, response.getClass());
        assertEquals(ID, response.getIdPerson());
        assertEquals(NAME, response.getName());
        assertEquals(BIRTH_DATE, response.getBirthDate());
        assertEquals(CPF, response.getCpf());
    }

    @Test
    void whenFindByIdThenRuntimeException() {
        String errorMessage = String.format("Pessoa com id %d não cadastrado!", ID);
        when(personRepository.findById(Mockito.anyLong())).thenThrow(new RuntimeException(errorMessage));

        try {
            personService.getPersonById(ID);
        } catch (Exception ex) {
            assertEquals(RuntimeException.class, ex.getClass());
            assertEquals(errorMessage, ex.getMessage());
        }
    }

    @Test
    void whenValidatePeopleWithSuccess() {
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(person));
        Person response = personService.validatePerson(ID);

        assertNotNull(response);
        assertEquals(ID, response.getIdPerson());
        assertEquals(NAME, response.getName());
        assertEquals(BIRTH_DATE, response.getBirthDate());
        assertEquals(ADDRESSES, response.getAddresses());

        verify(personRepository, times(1)).findById(ID);
    }

    @Test
    void whenValidatePeopleThenRuntimeException() {
        when(personRepository.findById(ID)).thenReturn(optionalPerson);

        try {
            personService.validatePerson(ID);
        } catch (Exception ex) {
            assertEquals("Pessoa com id " + ID + " não cadastrado!", ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(personRepository.findById(ID)).thenReturn(optionalPerson);
        when(personRepository.save(Mockito.any())).thenReturn(person);
        when(personMapper.toPersonResponse(person)).thenReturn(personResponse);


        PersonResponse response = personService.updatePerson(personResponse.getIdPerson(), personRequest);

        assertNotNull(response);
        assertEquals(PersonResponse.class, response.getClass());
        assertEquals(ID, response.getIdPerson());
        assertEquals(NAME, response.getName());
        assertEquals(BIRTH_DATE, response.getBirthDate());
        assertEquals(CPF, response.getCpf());
    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        String errorMessage = String.format("Pessoa com id %d não cadastrado!", ID);
        when(personRepository.findById(Mockito.anyLong())).thenReturn(optionalPerson);
        when(personMapper.toPersonResponse(Mockito.any())).thenReturn(personResponse);

        try {
            optionalPerson.get().setIdPerson(2L);
            personService.registerPerson(personRequest);
        } catch (Exception ex) {
            assertEquals(RuntimeException.class, ex.getMessage());
            assertEquals(errorMessage, ex.getMessage());
        }
    }

    @Test
    void whenDeleteWithSuccess() {
        when(personRepository.findById(ID)).thenReturn(optionalPerson);
        String result = personService.deletePeople(ID);
        assertEquals("Pessoa com id " + ID + " excluído com sucesso!", result);
        verify(personRepository, times(1)).delete(person);
    }

    @Test
    void whenDeleteWithAlertException() {
        when(personRepository.findById(anyLong())).thenThrow(new RuntimeException("Pessoa com id "
                + ID
                + " não cadastrada!"));
        try {
            personService.deletePeople(ID);
        } catch (Exception ex) {
            assertEquals("Pessoa com id " + ID + " não cadastrada!", ex.getMessage());
        }
    }

    @Test
    void whenUpdateMainAddressSuccess() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));
        Address mainAddress = new Address();
        mainAddress.setMainAddress(true);
        person.getAddresses().add(mainAddress);

        Person pesponse = personService.validatePerson(ID);

        assertNotNull(pesponse);

        assertEquals(ID, pesponse.getIdPerson());
        assertEquals(NAME, pesponse.getName());
        assertEquals(BIRTH_DATE, pesponse.getBirthDate());
        assertEquals(ADDRESSES, pesponse.getAddresses());

        verify(personRepository, times(1)).findById(ID);
    }

    private void startPeople() {
        person = new Person(ID, NAME, BIRTH_DATE,CPF , ADDRESSES);
        personResponse = new PersonResponse(ID, NAME, BIRTH_DATE,CPF , ADDRESS_RESPONSES);
        personRequest = new PersonRequest(NAME, BIRTH_DATE,CPF);
        optionalPerson = Optional.of(new Person(ID, NAME, BIRTH_DATE,CPF ,ADDRESSES));
    }
}
