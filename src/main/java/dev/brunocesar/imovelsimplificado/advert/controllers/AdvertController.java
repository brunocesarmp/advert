package dev.brunocesar.imovelsimplificado.advert.controllers;

import dev.brunocesar.imovelsimplificado.advert.controllers.documentation.IAdvertController;
import dev.brunocesar.imovelsimplificado.advert.controllers.requests.AdvertInterestRequest;
import dev.brunocesar.imovelsimplificado.advert.controllers.responses.AdvertResponse;
import dev.brunocesar.imovelsimplificado.advert.controllers.responses.SendInterestResponse;
import dev.brunocesar.imovelsimplificado.advert.services.AdvertService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("advert")
public class AdvertController implements IAdvertController {

    private final AdvertService advertService;

    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }

    @Override
    @GetMapping("lease")
    public Page<AdvertResponse> listLeases(@RequestParam(value = "state", required = false) String state,
                                           @RequestParam(value = "pag", defaultValue = "0") int pag,
                                           @RequestParam(value = "ord", defaultValue = "value") String ord,
                                           @RequestParam(value = "dir", defaultValue = "ASC") String dir) {
        var pageRequest = PageRequest.of(pag, 10, Sort.Direction.valueOf(dir), ord);
        return advertService.listLeases(state, pageRequest);
    }

    @Override
    @GetMapping("sale")
    public Page<AdvertResponse> listSales(@RequestParam(value = "state", required = false) String state,
                                          @RequestParam(value = "pag", defaultValue = "0") int pag,
                                          @RequestParam(value = "ord", defaultValue = "value") String ord,
                                          @RequestParam(value = "dir", defaultValue = "ASC") String dir) {
        var pageRequest = PageRequest.of(pag, 10, Sort.Direction.valueOf(dir), ord);
        return advertService.listSales(state, pageRequest);
    }

    @Override
    @PostMapping("{advertUuid}/send-interest")
    public SendInterestResponse sendInterest(@PathVariable String advertUuid,
                                             @RequestBody @Valid AdvertInterestRequest request) {
        advertService.sendAdvertInterestEmail(advertUuid, request);
        return new SendInterestResponse("Interesse pelo anúncio recebido. Em breve o anunciante entrará em contato! Isso é uma simulação.");
    }
}
