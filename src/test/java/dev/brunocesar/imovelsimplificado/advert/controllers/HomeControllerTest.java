package dev.brunocesar.imovelsimplificado.advert.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeControllerTest extends AbstractBaseControllerTest {

    @Override
    public Object getController() {
        return new HomeController();
    }

    @Test
    public void shouldReturnHomeMessage() {
        var result = super.webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class);

        result.consumeWith(r ->
                assertEquals(HomeController.HOME_MESSAGE, r.getResponseBody())
        );
    }
}