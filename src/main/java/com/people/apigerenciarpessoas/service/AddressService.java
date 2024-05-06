package com.people.apigerenciarpessoas.service;

import com.people.apigerenciarpessoas.models.dto.request.AddressRequest;
import com.people.apigerenciarpessoas.models.dto.response.AddressResponse;
import com.people.apigerenciarpessoas.models.entity.Address;

import java.util.List;

public interface AddressService {
    AddressResponse registerAddress(AddressRequest addressRequest);

    void existingAddress(String publicPlace, String number, String zipCode);

    List<AddressResponse> getAllAddress(Long idperson);

    List<Address> validateListAddress(Long idPeople);

    AddressResponse getAddressById(Long idAddress);

    Address validateAddress(Long cdAddress);

    AddressResponse updateAddress(Long idAddress, AddressRequest addressRequest);

    String deleteAddress(Long idAddress);
}
