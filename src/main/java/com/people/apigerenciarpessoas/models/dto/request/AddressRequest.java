package com.people.apigerenciarpessoas.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

    private String publicPlace;

    private String zipCode;

    private String number;

    private String city;

    private boolean mainAddress;

    private Long idPeople;
}
