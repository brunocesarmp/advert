package dev.brunocesar.imovelsimplificado.advert.controllers;

import dev.brunocesar.imovelsimplificado.advert.controllers.requests.AdvertRequest;
import dev.brunocesar.imovelsimplificado.advert.controllers.responses.AdvertResponse;
import dev.brunocesar.imovelsimplificado.advert.services.AdvertService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("portal/advert")
public class PortalAdvertController {

    private final AdvertService service;

    public PortalAdvertController(AdvertService service) {
        this.service = service;
    }

    @PostMapping
    public AdvertResponse save(@RequestBody @Valid AdvertRequest request) {
        return service.save(request);
    }

    @GetMapping("advertise/{advertiseUuid}")
    public List<AdvertResponse> listByAdvertiseUuid(@PathVariable String advertiseUuid) {
        return service.listByAdvertiseUuid(advertiseUuid);
    }

    @GetMapping("{uuid}")
    public AdvertResponse get(@PathVariable String uuid) {
        return service.get(uuid);
    }

    @PutMapping("{uuid}")
    public AdvertResponse update(@PathVariable String uuid,
                                 @RequestBody @Valid AdvertRequest request) {
        return service.update(uuid, request);
    }

    @DeleteMapping("{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String uuid) {
        service.delete(uuid);
    }
}
