package dev.brunocesar.imovelsimplificado.advert.utils;

import dev.brunocesar.imovelsimplificado.advert.controllers.requests.AdvertRequest;
import dev.brunocesar.imovelsimplificado.advert.domains.entity.Advert;
import dev.brunocesar.imovelsimplificado.advert.domains.entity.Advertise;
import dev.brunocesar.imovelsimplificado.advert.domains.enuns.AdvertType;
import dev.brunocesar.imovelsimplificado.advert.domains.enuns.State;
import dev.brunocesar.imovelsimplificado.advert.gateway.response.AdvertiseResponse;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public final class TestDataCreator {

    private TestDataCreator() {
    }

    public static AdvertiseResponse buildAdvertiseResponse() {
        var advertiseResponse = new AdvertiseResponse();
        advertiseResponse.setUuid("mock-uuid");
        return advertiseResponse;
    }

    public static Advert buildAdvert() {
        var advertise = new Advertise();
        advertise.setUuid("uuid-advertise");
        var advert = new Advert();
        advert.setUuid("uuid");
        advert.setAdvertise(advertise);
        return advert;
    }

    public static AdvertRequest buildAdvertRequest() {
        var request = new AdvertRequest();
        request.setTitle("test");
        request.setType(AdvertType.LEASE.name());
        request.setState(State.SP.name());
        return request;
    }

    public static MultipartFile buildMultiPartFile(String imageName) {
        return new MockMultipartFile("file", imageName, MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
    }
}
