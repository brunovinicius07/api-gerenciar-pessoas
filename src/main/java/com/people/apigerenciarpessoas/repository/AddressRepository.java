package com.people.apigerenciarpessoas.repository;

import com.people.apigerenciarpessoas.models.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findAddressByPublicPlaceAndNumberAndZipCode(String publicPlace, String number, String zipCode);

    List<Address> findAllAddressByPersonIdPerson(Long idPerson);
}
