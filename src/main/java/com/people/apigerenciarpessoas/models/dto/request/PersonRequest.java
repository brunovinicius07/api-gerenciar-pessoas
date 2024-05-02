package com.people.apigerenciarpessoas.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequest {

    private String name;

    private LocalDate birthDate;

    private String cpf;
}
