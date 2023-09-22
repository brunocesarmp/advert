package dev.brunocesar.imovelsimplificado.advert.domains.repository;

import dev.brunocesar.imovelsimplificado.advert.domains.entity.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, String> {

    List<Advert> findAllByAdvertiseUuid(String advertiseUuid);

}
