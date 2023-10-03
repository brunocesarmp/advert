package dev.brunocesar.imovelsimplificado.advert.gateway;

import dev.brunocesar.imovelsimplificado.advert.config.properties.AdvertiseConfigProperties;
import dev.brunocesar.imovelsimplificado.advert.exceptions.ApplicationException;
import dev.brunocesar.imovelsimplificado.advert.gateway.response.AdvertiseResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

    public AdvertiseResponse getAdvertise(String bearerToken) {
        try {
            var url = host.concat(GET_ADVERTISE_URI).concat("/me");

            var headers = new HttpHeaders();
            headers.set("Authorization", bearerToken);

            var httpEntity = new HttpEntity<>(headers);

            return restTemplate.exchange(url, HttpMethod.GET, httpEntity, AdvertiseResponse.class).getBody();
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new ApplicationException(HttpStatus.UNAUTHORIZED.value(), "Acesso negado. Você deve estar autenticado no sistema para acessar a URL solicitada.", ex);
        } catch (Exception ex) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno, contate o administrador.", ex);
        }
    }
}
