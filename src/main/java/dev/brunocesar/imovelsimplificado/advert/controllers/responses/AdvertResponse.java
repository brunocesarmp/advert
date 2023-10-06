package dev.brunocesar.imovelsimplificado.advert.controllers.responses;

import dev.brunocesar.imovelsimplificado.advert.domains.enuns.AdvertType;
import dev.brunocesar.imovelsimplificado.advert.domains.enuns.State;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class AdvertResponse {

    private String uuid;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String title;
    private String description;
    private AdvertAdvertiseResponse advertise;
    private List<UploadImageResponse> imageLinks;
    private State state;
    private Double value;
    private AdvertType type;

    @Data
    public static class AdvertAdvertiseResponse {
        private String uuid;
        private String name;
        private String email;
        private String phone;
    }
}
