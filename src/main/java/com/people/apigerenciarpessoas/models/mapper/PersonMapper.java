package com.people.apigerenciarpessoas.models.mapper;

import com.people.apigerenciarpessoas.models.dto.request.PersonRequest;
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

    @Mapping(target = "idsAddress", expression = "java(mapAddress(people.getAddresses()))")
    PersonResponse toPersonResponse(Person person);

    default List<Long> mapAddress(List<Address> addressList){
        if (addressList != null){
            return addressList.stream()
                    .map(Address::getIdAddress)
                    .collect(Collectors.toList());
        }else {
            return new ArrayList<>();
        }
    }
}
