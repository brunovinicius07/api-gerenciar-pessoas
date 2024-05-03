package com.people.apigerenciarpessoas.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_person")
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPerson;

    @Size(max = 100)
    @NotBlank
    private String name;

    @Past
    @NotNull
    private LocalDate birthDate;

    @Size(max = 11 , min = 11 , message = "Digite apenas numeros, não utilize caracteres!")
    @NotNull
    private String cpf;

    @OneToMany(mappedBy = "person")
    private List<Address> addresses = new ArrayList<>();
}
