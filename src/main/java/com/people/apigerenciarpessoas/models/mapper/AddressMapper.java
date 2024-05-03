package com.people.apigerenciarpessoas.models.mapper;

import com.people.apigerenciarpessoas.models.dto.request.AddressRequest;
import com.people.apigerenciarpessoas.models.dto.response.AddressResponse;
import com.people.apigerenciarpessoas.models.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(source = "idPerson", target = "person.idPerson")
    @Mapping(source = "state", target = "state")
    Address toAddress(AddressRequest addressRequest);

    @Mapping(source = "person.idPerson", target = "idPeople")
    AddressResponse toAddressResponse(Address address);

    List<AddressResponse> toListAddressResponse(List<Address> address);
}
