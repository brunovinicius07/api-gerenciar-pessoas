package com.people.apigerenciarpessoas.impl;

import com.people.apigerenciarpessoas.exception.address.AddressNotFoundException;
import com.people.apigerenciarpessoas.models.dto.request.AddressRequest;
import com.people.apigerenciarpessoas.models.dto.response.AddressResponse;
import com.people.apigerenciarpessoas.models.entity.Address;
import com.people.apigerenciarpessoas.models.entity.Person;
import com.people.apigerenciarpessoas.models.mapper.AddressMapper;
import com.people.apigerenciarpessoas.repository.AddressRepository;
import com.people.apigerenciarpessoas.repository.PersonRepository;
import com.people.apigerenciarpessoas.service.AddressService;
import com.people.apigerenciarpessoas.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddressServiceImplTest {

    public static final long ID_PERSON = 1L;
    public static final String NAME = "João";
    public static final LocalDate DATE = LocalDate.of(1998, 7, 15);
    public static final String CPF = "111.222.333-44";
    public static final List<Address> ADDRESSES = new ArrayList<>();
    public static final Person PERSON = (new Person(ID_PERSON, NAME, DATE, CPF , ADDRESSES));

    public static final long ID_ADDRESS = 1L;
    public static final String PUBLIC_PLACE = "Rua 1";
    public static final String ZIP_CODE = "55818-000";
    private static final String NUMBER = "22-A";
    public static final String CITY = "Recife";
    public static final String STATE = "Pernambuco";
    public static final boolean MAIN_ADDRESS = true;
    public static final int INDEX = 0;

    static {
        Address address = new Address(ID_ADDRESS, PUBLIC_PLACE, ZIP_CODE, NUMBER, CITY, STATE, MAIN_ADDRESS, PERSON);
        ADDRESSES.add(address);
    }

    @Autowired
    private AddressService addressService;

    @Autowired
    private PersonService peopleService;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private AddressMapper addressMapper;

    @MockBean
    private PersonRepository personRepository;

    private Address address;
    private AddressResponse addressResponseDto;
    private AddressRequest addressRequestDto;
    private Optional<Address> optionalAddress;

    private Person person;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPerson();
    }

    @Test
    void whenCreateThenReturnSuccess() {


        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(person));
        when(addressMapper.toAddress(Mockito.any())).thenReturn(address);
        when(addressRepository.save(Mockito.any())).thenReturn(address);
        when(addressMapper.toAddressResponse(Mockito.any())).thenReturn(addressResponseDto);

        AddressResponse response = addressService.registerAddress(addressRequestDto);

        assertNotNull(response);
        assertEquals(AddressResponse.class, response.getClass());
        assertEquals(ID_ADDRESS, response.getIdAddress());

        assertEquals(PUBLIC_PLACE, response.getPublicPlace());
        assertEquals(ZIP_CODE, response.getZipCode());
        assertEquals(NUMBER, response.getNumber());
        assertEquals(CITY, response.getCity());
        assertEquals(STATE, response.getState());
        assertEquals(MAIN_ADDRESS, response.isMainAddress());
        assertEquals(ID_PERSON, response.getIdPeople());
    }

    @Test
    void whenFindAllThenReturnAnListOfAddress() {
        when(addressRepository.findAllAddressByPersonIdPerson(ID_PERSON)).thenReturn(List.of(address));

        when(addressMapper.toListAddressResponse(List.of(address))).thenReturn(List.of(addressResponseDto));

        List<AddressResponse> response = addressService.getAllAddress(person.getIdPerson());

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(AddressResponse.class, response.get(INDEX).getClass());

        assertEquals(ID_ADDRESS, response.get(INDEX).getIdAddress());
        assertEquals(PUBLIC_PLACE, response.get(INDEX).getPublicPlace());
        assertEquals(ZIP_CODE, response.get(INDEX).getZipCode());
        assertEquals(NUMBER, response.get(INDEX).getNumber());
        assertEquals(CITY, response.get(INDEX).getCity());
        assertEquals(STATE, response.get(INDEX).getState());
        assertEquals(MAIN_ADDRESS, response.get(INDEX).isMainAddress());
        assertEquals(ID_PERSON, response.get(INDEX).getIdPeople());
    }


    @Test
    void whenValidateListAddressSuccess() {
        when(addressRepository.findAllAddressByPersonIdPerson(person.getIdPerson())).thenReturn(List.of(address));
        when(addressMapper.toListAddressResponse(ADDRESSES)).thenReturn(List.of(addressResponseDto));

        List<Address> response = addressService.validateListAddress(ID_PERSON);

        assertEquals(ID_ADDRESS, response.get(INDEX).getIdAddress());
        assertEquals(PUBLIC_PLACE, response.get(INDEX).getPublicPlace());
        assertEquals(ZIP_CODE, response.get(INDEX).getZipCode());
        assertEquals(NUMBER, response.get(INDEX).getNumber());
        assertEquals(CITY, response.get(INDEX).getCity());
        assertEquals(STATE, response.get(INDEX).getState());
        assertEquals(MAIN_ADDRESS, response.get(INDEX).isMainAddress());
        assertEquals(PERSON, response.get(INDEX).getPerson());

        verify(addressRepository, times(1)).findAllAddressByPersonIdPerson(ID_PERSON);
    }

    @Test
    void whenValidateListThenReturnAnListOfAddress() {
        when(addressRepository.findAllAddressByPersonIdPerson(ID_PERSON)).thenReturn(List.of(address));

        try {
            addressService.getAllAddress(ID_PERSON);
        } catch (Exception ex) {
            assertEquals("Nenhum endereço encontrado!", ex.getMessage());
        }
    }

    @Test
    void whenFindByIdThenReturnAnAddressInstance() {
        when(addressRepository.findById(Mockito.anyLong())).thenReturn(optionalAddress);
        when(addressMapper.toAddressResponse(Mockito.any())).thenReturn(addressResponseDto);

        AddressResponse response = addressService.getAddressById(ID_ADDRESS);

        assertNotNull(response);

        assertEquals(ID_ADDRESS, response.getIdAddress());

        assertEquals(PUBLIC_PLACE, response.getPublicPlace());
        assertEquals(ZIP_CODE, response.getZipCode());
        assertEquals(NUMBER, response.getNumber());
        assertEquals(CITY, response.getCity());
        assertEquals(STATE, response.getState());
        assertEquals(MAIN_ADDRESS, response.isMainAddress());
        assertEquals(ID_PERSON, response.getIdPeople());

        verify(addressRepository, times(1)).findById(ID_ADDRESS);

    }

    @Test
    void whenFindByIdThenRuntimeException() {
        when(personRepository.findById(Mockito.anyLong())).thenThrow(new RuntimeException("Endereço com id "
                + ID_ADDRESS
                + " não cadastrado!"));

        try {
            addressService.getAddressById(ID_ADDRESS);
        } catch (Exception ex) {
            assertEquals(AddressNotFoundException.class, ex.getClass());
            assertEquals("Endereço não localizado!", ex.getMessage());
        }
    }

    @Test
    void whenValidatePeopleWithSuccess() {
        when(addressRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(address));
        Address response = addressService.validateAddress(ID_ADDRESS);

        assertNotNull(response);
        assertEquals(ID_ADDRESS, response.getIdAddress());

        assertEquals(PUBLIC_PLACE, response.getPublicPlace());
        assertEquals(ZIP_CODE, response.getZipCode());
        assertEquals(NUMBER, response.getNumber());
        assertEquals(CITY, response.getCity());
        assertEquals(MAIN_ADDRESS, response.isMainAddress());
        assertEquals(PERSON, response.getPerson());

        verify(addressRepository, times(1)).findById(ID_ADDRESS);
    }

    @Test
    void whenValidateAddressThenRuntimeException() {
        when(addressRepository.findById(ID_ADDRESS)).thenReturn(optionalAddress);

        try {
            addressService.validateAddress(ID_ADDRESS);
        } catch (Exception ex) {
            assertEquals("Endereço com id " + ID_ADDRESS + " não cadastrado!", ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(person));
        when(addressRepository.findById(ID_ADDRESS)).thenReturn(optionalAddress);
        when(addressRepository.save(Mockito.any())).thenReturn(address);
        when(addressMapper.toAddressResponse(address)).thenReturn(addressResponseDto);

        AddressResponse response = addressService.updateAddress(addressResponseDto.getIdAddress(),
                new AddressRequest(PUBLIC_PLACE, ZIP_CODE,
                        NUMBER, CITY, STATE, MAIN_ADDRESS,
                        ID_PERSON));

        assertNotNull(response);
        assertEquals(AddressResponse.class, response.getClass());
        assertEquals(ID_ADDRESS, response.getIdAddress());
        assertEquals(PUBLIC_PLACE, response.getPublicPlace());
        assertEquals(ZIP_CODE, response.getZipCode());
        assertEquals(NUMBER, response.getNumber());
        assertEquals(CITY, response.getCity());
        assertEquals(STATE, response.getState());
        assertEquals(MAIN_ADDRESS, response.isMainAddress());
        assertEquals(ID_PERSON, response.getIdPeople());
    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(person));
        when(addressRepository.findById(Mockito.anyLong())).thenReturn(optionalAddress);
        when(addressMapper.toAddressResponse(Mockito.any())).thenReturn(addressResponseDto);

        try {
            optionalAddress.get().setIdAddress(2L);
        } catch (Exception ex) {
            assertEquals(RuntimeException.class, ex.getMessage());
            assertEquals("Endereço com id " + ID_ADDRESS + " não cadastrado!", ex.getMessage());
        }
    }

    @Test
    void whenDeleteWithSuccess() {
        when(addressRepository.findById(ID_ADDRESS)).thenReturn(optionalAddress);
        String result = addressService.deleteAddress(ID_ADDRESS);
        assertEquals("Endereço com o ID " + ID_ADDRESS + " excluído com sucesso!", result);
        verify(addressRepository, times(1)).delete(address);
    }

    @Test
    void whenDeleteWithAlertException() {
        when(addressRepository.findById(anyLong())).thenThrow(new RuntimeException("Endereço com id "
                + ID_ADDRESS
                + " não cadastrado!"));

        try {
            addressService.deleteAddress(ID_ADDRESS);
        } catch (Exception ex) {
            assertEquals("Endereço com id " + ID_ADDRESS + " não cadastrado!", ex.getMessage());
        }
    }

    private void startPerson() {
        person = new Person(ID_PERSON, NAME, DATE, CPF, ADDRESSES);
        address = new Address(ID_ADDRESS, PUBLIC_PLACE, ZIP_CODE, NUMBER, CITY,STATE, MAIN_ADDRESS, person);
        addressResponseDto = new AddressResponse(ID_ADDRESS, PUBLIC_PLACE, ZIP_CODE, NUMBER, CITY, STATE,
                MAIN_ADDRESS, ID_PERSON);
        addressRequestDto = new AddressRequest(PUBLIC_PLACE, ZIP_CODE, NUMBER, CITY,STATE, MAIN_ADDRESS, ID_PERSON);
        optionalAddress = Optional.of(new Address(ID_ADDRESS, PUBLIC_PLACE, ZIP_CODE, NUMBER, CITY, STATE,
                MAIN_ADDRESS, person));
    }
}
