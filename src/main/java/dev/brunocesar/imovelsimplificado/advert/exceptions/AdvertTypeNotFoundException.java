package dev.brunocesar.imovelsimplificado.advert.exceptions;

import org.springframework.http.HttpStatus;

import java.util.List;

public class AdvertTypeNotFoundException extends ApplicationException {

    public AdvertTypeNotFoundException(String value, List<String> allowedTypes) {
        super(HttpStatus.BAD_REQUEST.value(), "Tipo de Anúncio [" + value + "] não suportado. Tipos suportados: " + allowedTypes);
    }
}
