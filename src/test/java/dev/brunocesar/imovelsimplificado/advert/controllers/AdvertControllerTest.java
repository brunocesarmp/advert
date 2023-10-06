package dev.brunocesar.imovelsimplificado.advert.controllers;

import dev.brunocesar.imovelsimplificado.advert.controllers.responses.AdvertResponse;
import dev.brunocesar.imovelsimplificado.advert.controllers.responses.SendInterestResponse;
import dev.brunocesar.imovelsimplificado.advert.services.AdvertService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static dev.brunocesar.imovelsimplificado.advert.utils.TestDataCreator.buildAdvertInterestRequest;
import static dev.brunocesar.imovelsimplificado.advert.utils.TestDataCreator.buildAdvertResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdvertControllerTest extends AbstractBaseControllerTest {

    @Mock
    private AdvertService service;

    @Override
    public Object getController() {
        return new AdvertController(service);
    }

    @Test
    public void shouldListLeasesWithSuccess() {
        when(service.listLeases(any(), any())).thenReturn(new PageImpl<>(List.of(buildAdvertResponse())));
        super.webTestClient.get()
                .uri("/advert/lease")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AdvertResponse.class);
    }

    @Test
    public void shouldListSalesWithSuccess() {
        when(service.listSales(any(), any())).thenReturn(new PageImpl<>(List.of(buildAdvertResponse())));
        super.webTestClient.get()
                .uri("/advert/sale")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AdvertResponse.class);
    }

    @Test
    public void sendInterestWithSuccess() {
        var advertUuid = "mock-uuid";
        var request = buildAdvertInterestRequest();
        super.webTestClient.post()
                .uri("/advert/" + advertUuid + "/send-interest")
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(SendInterestResponse.class);
    }
}