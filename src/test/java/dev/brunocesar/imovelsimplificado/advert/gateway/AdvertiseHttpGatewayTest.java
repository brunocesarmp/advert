package dev.brunocesar.imovelsimplificado.advert.gateway;

import dev.brunocesar.imovelsimplificado.advert.config.properties.AdvertiseConfigProperties;
import dev.brunocesar.imovelsimplificado.advert.exceptions.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import static dev.brunocesar.imovelsimplificado.advert.utils.TestDataCreator.buildAdvertiseResponse;
import static org.junit.jupiter.api.Assertions.*;

class AdvertiseHttpGatewayTest extends AbstractBaseGatewayTest {

    private AdvertiseHttpGateway gateway;

    @BeforeEach
    public void beforeEach() {
        var restTemplate = new RestTemplate();
        var configProperties = new AdvertiseConfigProperties(getMockWebServerHost());
        gateway = new AdvertiseHttpGateway(restTemplate, configProperties);
    }

    @Test
    public void shouldReturnApplicationExceptionWhenInternalServerError() {
        mockBackEnd.enqueue(buildMockResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        var exception = assertThrows(ApplicationException.class,
                () -> gateway.getAdvertise("bearerToken"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getHttpStatus());
    }

    @Test
    public void shouldReturnUnauthorizedExceptionWhenUnauthorizedError() {
        mockBackEnd.enqueue(buildMockResponse(HttpStatus.UNAUTHORIZED.value()));
        var exception = assertThrows(ApplicationException.class,
                () -> gateway.getAdvertise("bearerToken"));

        assertEquals(HttpStatus.UNAUTHORIZED.value(), exception.getHttpStatus());
    }

    @Test
    public void shouldReturnAdvertiseWithSuccess() throws Exception {
        var advertiseResponse = buildAdvertiseResponse();
        mockBackEnd.enqueue(buildMockResponse(HttpStatus.OK.value(), advertiseResponse));

        var result = gateway.getAdvertise("bearerToken");

        assertNotNull(result);
        assertEquals(advertiseResponse.getUuid(), result.getUuid());
    }
}