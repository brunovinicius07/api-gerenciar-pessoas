package com.people.apigerenciarpessoas.service.impl;

import com.people.apigerenciarpessoas.exception.address.AddressIsPresentException;
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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final PersonService personService;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final PersonRepository personRepository;

    @Override
    @Transactional(readOnly = false)
    public AddressResponse registerAddress(AddressRequest addressRequest) {
        existingAddress(addressRequest.getPublicPlace(),
                addressRequest.getNumber(),
                addressRequest.getZipCode());

        Person person = personService.validatePerson(addressRequest.getIdPerson());
        personService.updateMainAddress(addressRequest.getIdPerson(), addressRequest.isMainAddress());

        Address address = addressMapper.toAddress(addressRequest);
        address.setPerson(person);

        return addressMapper.toAddressResponse(addressRepository.save(address));
    }

    @Override
    @Transactional(readOnly = true)
    public void existingAddress(String publicPlace, String number, String zipCode) {
        addressRepository.findAddressByPublicPlaceAndNumberAndZipCode(publicPlace, number, zipCode)
                .ifPresent(a -> {
                    throw  new AddressIsPresentException();
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponse> getAllAddress(Long idperson) {
        List<Address> addressList = addressRepository.findAllAddressByPersonIdPerson(idperson);

        return addressMapper.toListAddressResponse(addressList);
    }

    @Override
    @Transactional(readOnly = true)
    public AddressResponse getAddressById(Long idAddress) {
        return addressMapper.toAddressResponse(validateAddress(idAddress));
    }

    @Override
    @Transactional(readOnly = true)
    public Address validateAddress(Long cdAddress) {
        return addressRepository.findById(cdAddress).orElseThrow(AddressNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = false)
    public AddressResponse updateAddress(Long idAddress, AddressRequest addressRequest) {
        existingAddress(addressRequest.getPublicPlace(),
                addressRequest.getNumber(),
                addressRequest.getZipCode());

        Address address = validateAddress(idAddress);
        Person person = personRepository.findPeopleByIdPerson(addressRequest.getIdPerson());

        address.setPublicPlace(addressRequest.getPublicPlace());
        address.setZipCode(addressRequest.getZipCode());
        address.setNumber(addressRequest.getNumber());
        address.setCity(addressRequest.getCity());
        address.setPerson(person);

        personService.updateMainAddress(addressRequest.getIdPerson(), addressRequest.isMainAddress());

        address.setMainAddress(addressRequest.isMainAddress());

        return addressMapper.toAddressResponse(addressRepository.save(address));
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteAddress(Long idAddress) {
        Address address = validateAddress(idAddress);

        if (address != null){
            addressRepository.delete(address);
            return "Endereço deletedo com sucesso!";
        }
        return "Erro ao deletar endereço!";
    }
}
