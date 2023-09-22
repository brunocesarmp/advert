package dev.brunocesar.imovelsimplificado.advert.exceptions;

import org.springframework.http.HttpStatus;

public class AdvertNotFoundException extends ApplicationException {

    public AdvertNotFoundException(String uuid) {
        super(HttpStatus.NOT_FOUND.value(), "Anúncio com uuid [" + uuid + "] não encontrado");
    }
}
