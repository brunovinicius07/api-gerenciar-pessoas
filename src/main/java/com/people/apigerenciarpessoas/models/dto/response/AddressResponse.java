package com.people.apigerenciarpessoas.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {

    private Long idAddress;

    private String publicPlace;

    private String zipCode;

    private String number;

    private String city;

    private String state;

    private boolean mainAddress;

    private Long idPeople;
}
