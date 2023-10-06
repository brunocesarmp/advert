package dev.brunocesar.imovelsimplificado.advert.controllers;

import dev.brunocesar.imovelsimplificado.advert.controllers.requests.AdvertRequest;
import dev.brunocesar.imovelsimplificado.advert.controllers.responses.AdvertResponse;
import dev.brunocesar.imovelsimplificado.advert.services.AdvertService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static dev.brunocesar.imovelsimplificado.advert.utils.TestDataCreator.buildAdvertRequest;
import static dev.brunocesar.imovelsimplificado.advert.utils.TestDataCreator.buildAdvertResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortalAdvertControllerTest extends AbstractBaseControllerTest {

    @Mock
    private AdvertService service;

    private String advertiseToken;

    @Override
    public Object getController() {
        return new PortalAdvertController(service);
    }

    @Override
    protected void beforeEach() {
        super.beforeEach();
        advertiseToken = "mock-token";
    }

    @Test
    public void shouldSaveWithSuccess() {
        var advertRequest = buildAdvertRequest();
        when(service.save(any(), any())).thenReturn(buildAdvertResponse());
        super.webTestClient.post()
                .uri("/portal/advert")
                .bodyValue(advertRequest)
                .header("Authorization", advertiseToken)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(AdvertRequest.class);
    }

    @Test
    public void listByAdvertiseWithSuccess() {
        when(service.listByAdvertise(advertiseToken)).thenReturn(List.of(buildAdvertResponse()));
        super.webTestClient.get()
                .uri("/portal/advert")
                .header("Authorization", advertiseToken)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(AdvertResponse.class);
    }

    @Test
    public void getWithSuccess() {
        var advertUuid = "mock-uuid";
        when(service.get(advertUuid, advertiseToken)).thenReturn(buildAdvertResponse());
        super.webTestClient.get()
                .uri("/portal/advert/" + advertUuid)
                .header("Authorization", advertiseToken)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AdvertResponse.class);
    }

    @Test
    public void shouldUpdateWithSuccess() {
        var advertRequest = buildAdvertRequest();
        var advertUuid = "mock-uuid";
        when(service.update(eq(advertUuid), any(), any())).thenReturn(buildAdvertResponse());
        super.webTestClient.put()
                .uri("/portal/advert/" + advertUuid)
                .bodyValue(advertRequest)
                .header("Authorization", advertiseToken)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AdvertRequest.class);
    }

    @Test
    public void shouldDeleteWithSuccess() {
        var advertUuid = "mock-uuid";
        super.webTestClient.delete()
                .uri("/portal/advert/" + advertUuid)
                .header("Authorization", advertiseToken)
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}