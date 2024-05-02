package com.people.apigerenciarpessoas.controller;

import com.people.apigerenciarpessoas.models.dto.request.AddressRequest;
import com.people.apigerenciarpessoas.models.dto.response.AddressResponse;
import com.people.apigerenciarpessoas.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Operation(summary = "Registrar um novo endereço", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Registro do endereço realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Não autorizado a acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Not Found: Recurso não encontrado"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Dados de requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Erro interno do servidor"),
    })
    @PostMapping
    public ResponseEntity<AddressResponse> registerAddress(@RequestBody @Valid AddressRequest addressRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.registerAddress(addressRequest));
    }

    @Operation(summary = "Buscar todos os endereços da pessoa por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK:Busca realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Parametros inválidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized:Não autorizado acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Not Found: Recurso não encontrado"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Dados de requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Erro interno do servidor"),
    })
    @GetMapping("/list-address/{idperson}")
    public ResponseEntity<List<AddressResponse>> getAllAddress(@PathVariable Long idperson) {
        return ResponseEntity.ok(addressService.getAllAddress(idperson));
    }

    @Operation(summary = "Buscar um endereço por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Busca realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Parametros inválidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Não autorizado acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Not Found: Recurso não encontrado"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Dados de requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Erro interno do servidor"),
    })
    @GetMapping("/{idAddress}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long idAddress) {
        return ResponseEntity.ok(addressService.getAddressById(idAddress));
    }

    @Operation(summary = "Editar um endereço por id", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Atualização realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Parametros inválidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Não autorizado acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Not Found: Recurso não encontrado"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Dados de requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Erro interno do servidor"),
    })
    @PutMapping("/{idAddress}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long idAddress,
                                                            @RequestBody @Valid AddressRequest addressRequest) {
        return ResponseEntity.ok(addressService.updateAddress(idAddress, addressRequest));
    }

    @Operation(summary = "Deletar um endereço por id", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Parametros inválidos"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Não autorizado acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Not Found: Recurso não encontrado"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity: Dados de requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Erro interno do servidor"),
    })
    @DeleteMapping("/{idAddress}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long idAddress) {
        return ResponseEntity.ok(addressService.deleteAddress(idAddress));
    }
}
