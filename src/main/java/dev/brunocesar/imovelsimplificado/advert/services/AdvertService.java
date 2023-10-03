package dev.brunocesar.imovelsimplificado.advert.services;

import dev.brunocesar.imovelsimplificado.advert.controllers.requests.AdvertInterestRequest;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AdvertService {

    private static final int TOTAL_ADVERT_IMAGES_ALLOWED = 5;

    private final AdvertRepository repository;
    private final AdvertiseHttpGateway advertiseHttpGateway;
    private final AwsS3Service awsS3Service;
    private final AwsSqsService awsSqsService;

    public AdvertService(AdvertRepository repository,
                         AdvertiseHttpGateway advertiseHttpGateway,
                         AwsS3Service awsS3Service, AwsSqsService awsSqsService) {
        this.repository = repository;
        this.advertiseHttpGateway = advertiseHttpGateway;
        this.awsS3Service = awsS3Service;
        this.awsSqsService = awsSqsService;
    }

    @Transactional(rollbackFor = Exception.class)
    public AdvertResponse save(AdvertRequest request, String advertiseToken) {
        var advertiseResponse = advertiseHttpGateway.getAdvertise(advertiseToken);
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

    public List<AdvertResponse> listByAdvertise(String advertiseToken) {
        var advertise = advertiseHttpGateway.getAdvertise(advertiseToken);
        return repository.findAllByAdvertiseUuid(advertise.getUuid())
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public AdvertResponse get(String uuid, String advertiseToken) {
        var advertise = advertiseHttpGateway.getAdvertise(advertiseToken);
        var entity = findAdvertByUuid(uuid);
        if (advertiseNotIsOwnerOfAdvert(advertise, entity)) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST.value(), "Somente o dono do anúncio pode buscar o mesmo pelo endpoint /portal");
        }
        return convertToResponse(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public AdvertResponse update(String uuid, AdvertRequest request, String advertiseToken) {
        var advertise = advertiseHttpGateway.getAdvertise(advertiseToken);
        var entity = findAdvertByUuid(uuid);
        if (advertiseNotIsOwnerOfAdvert(advertise, entity)) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST.value(), "Somente o dono do anúncio pode alterar o mesmo");
        }
        updateEntity(entity, request);
        repository.save(entity);
        return convertToResponse(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public String uploadImage(String advertUuid, String advertiseToken, MultipartFile file) {
        var advertise = advertiseHttpGateway.getAdvertise(advertiseToken);
        var entity = findAdvertByUuid(advertUuid);
        if (advertiseNotIsOwnerOfAdvert(advertise, entity)) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST.value(), "Somente o dono do anúncio fazer upload de imagens do mesmo");
        }
        validateUpload(file, entity);
        var nextImageNumber = entity.totalImageLinkRegistered() + 1;
        var url = awsS3Service.uploadImage(file, advertUuid, nextImageNumber);
        entity.addImageLink(url);
        return (url);
    }

    private void validateUpload(MultipartFile file, Advert advert) {
        if (Objects.isNull(file.getOriginalFilename())
                && !(file.getOriginalFilename().endsWith(".jpg")
                || file.getOriginalFilename().endsWith(".jpeg")
                || file.getOriginalFilename().endsWith(".png"))) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST.value(), "Arquivo para upload precisa ser .jpg, .jpeg ou .png");
        }
        if (advert.totalImageLinkRegistered() >= TOTAL_ADVERT_IMAGES_ALLOWED) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST.value(), "Limite de 5 imagens por anúncio excedido");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(String uuid, String advertiseToken) {
        var advertise = advertiseHttpGateway.getAdvertise(advertiseToken);
        var entity = findAdvertByUuid(uuid);
        if (advertiseNotIsOwnerOfAdvert(advertise, entity)) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST.value(), "Somente o dono do anúncio fazer deletar o mesmo");
        }
        repository.delete(entity);
    }

    public void sendAdvertInterestEmail(String advertUuid, AdvertInterestRequest request) {
        findAdvertByUuid(advertUuid);
        awsSqsService.sendToAdvertInterestEmailQueue(request);
    }

    private boolean advertiseNotIsOwnerOfAdvert(AdvertiseResponse advertise, Advert entity) {
        return !advertise.getUuid().equalsIgnoreCase(entity.getAdvertise().getUuid());
    }

    private Advert findAdvertByUuid(String uuid) {
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