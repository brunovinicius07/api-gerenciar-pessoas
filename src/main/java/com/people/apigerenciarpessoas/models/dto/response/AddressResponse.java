package com.people.apigerenciarpessoas.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {

    private Long idAddress;

    private String publicPlace;

    private String zipCode;

    private String number;

    private String city;

    private boolean mainAddress;

    private Long idPeople;
}
