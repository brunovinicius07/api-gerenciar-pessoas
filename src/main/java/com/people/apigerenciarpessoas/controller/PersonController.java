package com.people.apigerenciarpessoas.controller;

import com.people.apigerenciarpessoas.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Operation(summary = "Registrar uma nova pessoa", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Registro da pessoa realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Não autorizado a acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Not Found: Recurso não encontrado"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Dados de requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Erro interno do servidor"),
    })
    @PostMapping
    public ResponseEntity<PersonResponse> registerPerson(@RequestBody @Valid PersonRequest personRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.registerPerson(personRequest));
    }

    @Operation(summary = "Buscar todos os registros das pessoas", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK:Busca realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Parametros inválidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized:Não autorizado acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Not Found: Recurso não encontrado"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Dados de requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Erro interno do servidor"),
    })
    @GetMapping
    public ResponseEntity<List<PersonResponse>> getAllPeople() {
        return ResponseEntity.ok(personService.getAllPeople());
    }

    @Operation(summary = "Buscar o registro de uma pessoa por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Busca realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Parametros inválidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Não autorizado acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Not Found: Recurso não encontrado"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Dados de requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Erro interno do servidor"),
    })
    @GetMapping("/{idPerson}")
    public ResponseEntity<PersonResponse> getPeopleById(@PathVariable Long idPerson) {
        return ResponseEntity.ok(personService.getPersonById(idPerson));
    }

    @Operation(summary = "Editar os dados de uma pessoa por id", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Atualização realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Parametros inválidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Não autorizado acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Not Found: Recurso não encontrado"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Dados de requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Erro interno do servidor"),
    })
    @PutMapping("/{idPerson}")
    public ResponseEntity<PersonResponse> updatePerson(@PathVariable Long idPerson, @RequestBody
    @Valid PersonRequest personRequest) {
        return ResponseEntity.ok(personService.updatePerson(idPerson, personRequest));
    }

    @Operation(summary = "Deletar o registro da pessoa por id", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Parametros inválidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Não autorizado acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Not Found: Recurso não encontrado"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Dados de requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Erro interno do servidor"),
    })
    @DeleteMapping("/{idPerson}")
    public ResponseEntity<String> deletePeople(@PathVariable Long idPerson) {
        return ResponseEntity.ok(idPerson.deletePeople(idPerson));
    }
}
