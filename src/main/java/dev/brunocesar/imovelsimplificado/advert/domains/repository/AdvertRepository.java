package dev.brunocesar.imovelsimplificado.advert.domains.repository;

import dev.brunocesar.imovelsimplificado.advert.domains.entity.Advert;
import dev.brunocesar.imovelsimplificado.advert.domains.enuns.AdvertType;
import dev.brunocesar.imovelsimplificado.advert.domains.enuns.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, String> {

    List<Advert> findAllByAdvertiseUuid(String advertiseUuid);

    Page<Advert> findAllByType(AdvertType type, PageRequest pageRequest);

    Page<Advert> findAllByStateAndType(State state, AdvertType type, PageRequest pageRequest);
}
