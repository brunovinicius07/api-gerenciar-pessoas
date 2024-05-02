package com.people.apigerenciarpessoas.repository;

import com.people.apigerenciarpessoas.models.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findPeopleByIdPerson(Long idPerson);

    Optional<Person> findPersonByCpf(String cpf);
}
