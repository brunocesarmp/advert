package dev.brunocesar.imovelsimplificado.advert.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public class StateNotFoundException extends ApplicationException {

    public StateNotFoundException(String value, List<String> states) {
        super(HttpStatus.BAD_REQUEST.value(), "Estado [" + value + "] não suportado. Estados suportados: " + states);
    }
}
