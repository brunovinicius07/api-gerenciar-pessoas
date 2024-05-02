package com.people.apigerenciarpessoas.service;

import com.people.apigerenciarpessoas.models.dto.request.AddressRequest;
import com.people.apigerenciarpessoas.models.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {
    AddressResponse registerAddress(AddressRequest addressRequest);

    List<AddressResponse> getAllAddress(Long idperson);

    AddressResponse getAddressById(Long idAddress);

    AddressResponse updateAddress(Long idAddress, AddressRequest addressRequest);

    String deleteAddress(Long idAddress);
}
