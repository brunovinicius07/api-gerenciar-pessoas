package com.people.apigerenciarpessoas.models.mapper;

import com.people.apigerenciarpessoas.models.dto.request.PersonRequest;
import com.people.apigerenciarpessoas.models.dto.response.AddressResponse;
import com.people.apigerenciarpessoas.models.dto.response.PersonResponse;
import com.people.apigerenciarpessoas.models.entity.Address;
import com.people.apigerenciarpessoas.models.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PersonMapper {


    Person toPerson(PersonRequest personRequest);

    @Mapping(target = "address", expression = "java(mapAddress(person.getAddresses()))")
    PersonResponse toPersonResponse(Person person);

    default List<AddressResponse> mapAddress(List<Address> addressList){
        List<AddressResponse> addressResponses = new ArrayList<>();

        if (addressList != null){
            for (Address address : addressList){
                if (address != null){
                    addressResponses.add(
                            AddressResponse.builder()
                                    .idAddress(address.getIdAddress())
                                    .city(address.getCity())
                                    .zipCode(address.getZipCode())
                                    .mainAddress(address.isMainAddress())
                                    .number(address.getNumber())
                                    .publicPlace(address.getPublicPlace())
                                    .state(address.getState()).build());
                }
            }
        }
        return addressResponses;
    }

    List<PersonResponse> toListPersonResponse(List<Person> people);
}
