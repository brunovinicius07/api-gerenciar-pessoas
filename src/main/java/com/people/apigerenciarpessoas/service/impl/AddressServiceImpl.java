package com.people.apigerenciarpessoas.service.impl;

import com.people.apigerenciarpessoas.models.dto.request.AddressRequest;
import com.people.apigerenciarpessoas.models.dto.response.AddressResponse;
import com.people.apigerenciarpessoas.service.AddressService;

import java.util.List;

public class AddressServiceImpl implements AddressService {
    @Override
    public AddressResponse registerAddress(AddressRequest addressRequest) {
        return null;
    }

    @Override
    public List<AddressResponse> getAllAddress(Long idperson) {
        return null;
    }

    @Override
    public AddressResponse getAddressById(Long idAddress) {
        return null;
    }

    @Override
    public AddressResponse updateAddress(Long idAddress, AddressRequest addressRequest) {
        return null;
    }

    @Override
    public String deleteAddress(Long idAddress) {
        return null;
    }
}
