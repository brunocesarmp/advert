package dev.brunocesar.imovelsimplificado.advert.services;

import dev.brunocesar.imovelsimplificado.advert.controllers.requests.AdvertRequest;
import dev.brunocesar.imovelsimplificado.advert.controllers.responses.AdvertResponse;
import dev.brunocesar.imovelsimplificado.advert.domains.entity.Advert;
import dev.brunocesar.imovelsimplificado.advert.domains.entity.Advertise;
import dev.brunocesar.imovelsimplificado.advert.domains.enuns.AdvertType;
import dev.brunocesar.imovelsimplificado.advert.domains.enuns.State;
import dev.brunocesar.imovelsimplificado.advert.domains.repository.AdvertRepository;
import dev.brunocesar.imovelsimplificado.advert.exceptions.AdvertNotFoundException;
import dev.brunocesar.imovelsimplificado.advert.exceptions.ApplicationException;
import dev.brunocesar.imovelsimplificado.advert.gateway.AdvertiseHttpGateway;
import dev.brunocesar.imovelsimplificado.advert.gateway.response.AdvertiseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertService {

    private final AdvertRepository repository;
    private final AdvertiseHttpGateway advertiseHttpGateway;

    public AdvertService(AdvertRepository repository,
                         AdvertiseHttpGateway advertiseHttpGateway) {
        this.repository = repository;
        this.advertiseHttpGateway = advertiseHttpGateway;
    }

    @Transactional
    public AdvertResponse save(AdvertRequest request) {
        var advertiseResponse = advertiseHttpGateway.getAdvertiseByUuid(request.getAdvertiseUuid());
        var entity = convertToEntity(request);
        var advertise = new Advertise();
        updateAdvertiseInfo(advertise, advertiseResponse);
        entity.setAdvertise(advertise);
        repository.save(entity);
        return convertToResponse(entity);
    }

    private void updateAdvertiseInfo(Advertise advertise, AdvertiseResponse advertiseResponse) {
        advertise.setUuid(advertiseResponse.getUuid());
        advertise.setName(advertiseResponse.getFirstName());
        advertise.setEmail(advertiseResponse.getEmail());
        advertise.setPhone(advertiseResponse.getPhone());
    }

    public List<AdvertResponse> listByAdvertiseUuid(String advertiseUuid) {
        return repository.findAllByAdvertiseUuid(advertiseUuid)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public AdvertResponse get(String uuid) {
        var entity = findEntityByUuid(uuid);
        return convertToResponse(entity);
    }

    @Transactional
    public AdvertResponse update(String uuid, AdvertRequest request) {
        var entity = findEntityByUuid(uuid);
        if (!isAdvertiseOwnerOfAdvert(request, entity)) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST.value(), "Somente o dono do anúncio pode alterar o mesmo");
        }
        updateEntity(entity, request);
        repository.save(entity);
        return convertToResponse(entity);
    }

    private boolean isAdvertiseOwnerOfAdvert(AdvertRequest request, Advert entity) {
        return request.getAdvertiseUuid().equalsIgnoreCase(entity.getAdvertise().getUuid());
    }

    @Transactional
    public void delete(String uuid) {
        repository.findById(uuid)
                .ifPresent(repository::delete);
    }

    private Advert findEntityByUuid(String uuid) {
        return repository.findById(uuid)
                .orElseThrow(() -> new AdvertNotFoundException(uuid));
    }

    private Advert convertToEntity(AdvertRequest request) {
        var entity = new Advert();
        updateEntity(entity, request);
        return entity;
    }

    private void updateEntity(Advert entity, AdvertRequest request) {
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setType(AdvertType.toEnum(request.getType()));
        entity.setState(State.toEnum(request.getState()));
        entity.setValue(request.getValue());
    }

    private AdvertResponse convertToResponse(Advert entity) {
        var response = new AdvertResponse();
        response.setUuid(entity.getUuid());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setTitle(entity.getTitle());
        response.setDescription(entity.getDescription());
        response.setType(entity.getType());
        response.setState(entity.getState());
        response.setValue(entity.getValue());
        response.setImageLinks(entity.getImageLinks());

        var advertiseEntity = entity.getAdvertise();
        var advertiseResponse = new AdvertResponse.Advertise();
        advertiseResponse.setUuid(advertiseEntity.getUuid());
        advertiseResponse.setName(advertiseEntity.getName());
        advertiseResponse.setEmail(advertiseEntity.getEmail());
        advertiseResponse.setPhone(advertiseEntity.getPhone());
        response.setAdvertise(advertiseResponse);

        return response;
    }
}