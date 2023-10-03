package dev.brunocesar.imovelsimplificado.advert.controllers;

import dev.brunocesar.imovelsimplificado.advert.controllers.requests.AdvertInterestRequest;
import dev.brunocesar.imovelsimplificado.advert.services.AdvertService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("advert")
public class AdvertController {

    private final AdvertService advertService;

    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }

    @PostMapping("{advertUuid}/send-interest")
    public Map<String, Object> sendInterest(@PathVariable String advertUuid,
                                            @RequestBody @Valid AdvertInterestRequest request) {
        advertService.sendAdvertInterestEmail(advertUuid, request);
        return Map.of("message", "Interesse pelo anúncio recebido. Em breve o anunciante entrará em contato!");
    }

}
