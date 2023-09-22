package dev.brunocesar.imovelsimplificado.advert.domains.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Advertise {

    @Column(nullable = false, name = "advertise_uuid")
    private String uuid;

    @Column(nullable = false, name = "advertise_name")
    private String name;

    @Column(nullable = false, name = "advertise_email")
    private String email;

    @Column(nullable = false, name = "advertise_phone")
    private String phone;
}
