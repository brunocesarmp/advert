package dev.brunocesar.imovelsimplificado.advert.controllers;

import dev.brunocesar.imovelsimplificado.advert.controllers.requests.AdvertInterestRequest;
import dev.brunocesar.imovelsimplificado.advert.domains.entity.Advert;
import dev.brunocesar.imovelsimplificado.advert.services.AdvertService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("advert")
public class AdvertController {

    private final AdvertService advertService;

    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }

    @GetMapping("lease")
    public Page<Advert> listLeases(@RequestParam(value = "state", required = false) String state,
                                   @RequestParam(value = "pag", defaultValue = "0") int pag,
                                   @RequestParam(value = "ord", defaultValue = "value") String ord,
                                   @RequestParam(value = "dir", defaultValue = "ASC") String dir) {
        var pageRequest = PageRequest.of(pag, 10, Sort.Direction.valueOf(dir), ord);
        return advertService.listLeases(state, pageRequest);
    }

    @GetMapping("sale")
    public Page<Advert> listSales(@RequestParam(value = "state", required = false) String state,
                                  @RequestParam(value = "pag", defaultValue = "0") int pag,
                                  @RequestParam(value = "ord", defaultValue = "value") String ord,
                                  @RequestParam(value = "dir", defaultValue = "ASC") String dir) {
        var pageRequest = PageRequest.of(pag, 10, Sort.Direction.valueOf(dir), ord);
        return advertService.listSales(state, pageRequest);
    }

    @PostMapping("{advertUuid}/send-interest")
    public Map<String, Object> sendInterest(@PathVariable String advertUuid,
                                            @RequestBody @Valid AdvertInterestRequest request) {
        advertService.sendAdvertInterestEmail(advertUuid, request);
        return Map.of("message", "Interesse pelo anúncio recebido. Em breve o anunciante entrará em contato!");
    }
}
