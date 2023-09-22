package dev.brunocesar.imovelsimplificado.advert.gateway;

import dev.brunocesar.imovelsimplificado.advert.config.properties.AdvertiseConfigProperties;
import dev.brunocesar.imovelsimplificado.advert.exceptions.AdvertiseNotFoundException;
import dev.brunocesar.imovelsimplificado.advert.exceptions.ApplicationException;
import dev.brunocesar.imovelsimplificado.advert.gateway.response.AdvertiseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AdvertiseHttpGateway {

    private static final String GET_ADVERTISE_URI = "/advertise";

    private final RestTemplate restTemplate;
    private final String host;

    public AdvertiseHttpGateway(RestTemplate restTemplate,
                                AdvertiseConfigProperties advertiseConfigProperties) {
        this.restTemplate = restTemplate;
        this.host = advertiseConfigProperties.host();
    }

    public AdvertiseResponse getAdvertiseByUuid(String advertiseUuid) {
        try {
            var uri = host.concat(GET_ADVERTISE_URI).concat("/" + advertiseUuid);
            return restTemplate.getForObject(uri, AdvertiseResponse.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new AdvertiseNotFoundException(advertiseUuid);
        } catch (Exception ex) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno, contate o administrador");
        }
    }
}
