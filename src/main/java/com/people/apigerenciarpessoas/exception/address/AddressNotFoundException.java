package com.people.apigerenciarpessoas.exception.address;

import com.people.apigerenciarpessoas.configuration.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class AddressNotFoundException extends AlertException {
    private static final String DEFAULT_MESSAGE = "Endereço não localizado!";
    public AddressNotFoundException() {
        super("404", DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);

    }
}
