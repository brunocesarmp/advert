package dev.brunocesar.imovelsimplificado.advert.domains.entity;

import dev.brunocesar.imovelsimplificado.advert.domains.enuns.AdvertType;
import dev.brunocesar.imovelsimplificado.advert.domains.enuns.State;
import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.time.OffsetDateTime.now;
import static java.util.Objects.isNull;

@Data
@Entity
@Table(name = "tb_adverts")
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Embedded
    private Advertise advertise;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_image_links", joinColumns = @JoinColumn(name = "advert_uuid"))
    @Column(name = "image_link", nullable = false)
    private List<String> imageLinks = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AdvertType type;

    @PrePersist
    public void prePersist() {
        if (isNull(createdAt)) {
            createdAt = now();
        }
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = now();
    }

    public void addImageLink(String imageLink) {
        if (Objects.isNull(imageLink) || imageLink.isBlank()) {
            return;
        }
        imageLinks.add(imageLink);
    }

    public int totalImageLinkRegistered() {
        return imageLinks.size();
    }

}