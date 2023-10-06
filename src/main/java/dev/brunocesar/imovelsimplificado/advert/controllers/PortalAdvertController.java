package dev.brunocesar.imovelsimplificado.advert.controllers;

import dev.brunocesar.imovelsimplificado.advert.controllers.documentation.IPortalAdvertController;
import dev.brunocesar.imovelsimplificado.advert.controllers.requests.AdvertRequest;
import dev.brunocesar.imovelsimplificado.advert.controllers.responses.AdvertResponse;
import dev.brunocesar.imovelsimplificado.advert.controllers.responses.UploadImageResponse;
import dev.brunocesar.imovelsimplificado.advert.services.AdvertService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("portal/advert")
public class PortalAdvertController implements IPortalAdvertController {

    private final AdvertService service;

    public PortalAdvertController(AdvertService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdvertResponse save(@RequestBody @Valid AdvertRequest request,
                               @RequestHeader("Authorization") String advertiseToken) {
        return service.save(request, advertiseToken);
    }

    @Override
    @GetMapping
    public List<AdvertResponse> listByAdvertise(@RequestHeader("Authorization") String advertiseToken) {
        return service.listByAdvertise(advertiseToken);
    }

    @Override
    @GetMapping("{uuid}")
    public AdvertResponse get(@PathVariable String uuid,
                              @RequestHeader("Authorization") String advertiseToken) {
        return service.get(uuid, advertiseToken);
    }

    @Override
    @PutMapping("{uuid}")
    public AdvertResponse update(@PathVariable String uuid,
                                 @RequestBody @Valid AdvertRequest request,
                                 @RequestHeader("Authorization") String advertiseToken) {
        return service.update(uuid, request, advertiseToken);
    }

    @Override
    @DeleteMapping("{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String uuid,
                       @RequestHeader("Authorization") String advertiseToken) {
        service.delete(uuid, advertiseToken);
    }

    @Override
    @PostMapping("{uuid}/image/upload")
    public UploadImageResponse uploadImage(@PathVariable("uuid") String advertUuid,
                                           @RequestHeader("Authorization") String advertiseToken,
                                           @RequestParam("file") MultipartFile file) {
        var imageLink = service.uploadImage(advertUuid, advertiseToken, file);
        return new UploadImageResponse(imageLink);
    }
}