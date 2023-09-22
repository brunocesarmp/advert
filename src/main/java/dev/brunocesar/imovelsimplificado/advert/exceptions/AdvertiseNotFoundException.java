package dev.brunocesar.imovelsimplificado.advert.exceptions;

import org.springframework.http.HttpStatus;

public class AdvertiseNotFoundException extends ApplicationException {

    public AdvertiseNotFoundException(String uuid) {
        super(HttpStatus.BAD_REQUEST.value(), "Anúnciante com uuid [" + uuid + "] não encontrado");
    }
}
