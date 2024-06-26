package com.people.apigerenciarpessoas.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse {

    private Long idPerson;

    private String name;

    private LocalDate birthDate;

    private String cpf;

    private List<AddressResponse> address;
}
