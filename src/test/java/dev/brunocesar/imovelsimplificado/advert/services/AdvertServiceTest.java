package dev.brunocesar.imovelsimplificado.advert.services;

import dev.brunocesar.imovelsimplificado.advert.controllers.requests.AdvertInterestRequest;
import dev.brunocesar.imovelsimplificado.advert.controllers.requests.AdvertRequest;
import dev.brunocesar.imovelsimplificado.advert.domains.entity.Advert;
import dev.brunocesar.imovelsimplificado.advert.domains.repository.AdvertRepository;
import dev.brunocesar.imovelsimplificado.advert.exceptions.AdvertNotFoundException;
import dev.brunocesar.imovelsimplificado.advert.exceptions.AdvertTypeNotFoundException;
import dev.brunocesar.imovelsimplificado.advert.exceptions.ApplicationException;
import dev.brunocesar.imovelsimplificado.advert.exceptions.StateNotFoundException;
import dev.brunocesar.imovelsimplificado.advert.gateway.AdvertiseHttpGateway;
import dev.brunocesar.imovelsimplificado.advert.gateway.response.AdvertiseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static dev.brunocesar.imovelsimplificado.advert.utils.TestDataCreator.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdvertServiceTest {

    @InjectMocks
    private AdvertService service;

    @Mock
    private AdvertRepository repository;

    @Mock
    private AdvertiseHttpGateway advertiseHttpGateway;

    @Mock
    private AwsS3Service awsS3Service;

    @Mock
    private AwsSqsService awsSqsService;

    private String advertiseToken;

    private AdvertiseResponse advertiseResponse;

    private Advert advert;

    private AdvertRequest advertRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        advertiseToken = "token";
        advertiseResponse = buildAdvertiseResponse();
        advert = buildAdvert();
        advertRequest = buildAdvertRequest();
    }

    @Test
    public void shouldSaveAdvertWithSuccess() {
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.save(any())).thenReturn(advert);

        var result = service.save(advertRequest, advertiseToken);

        verify(advertiseHttpGateway).getAdvertise(advertiseToken);
        verify(repository).save(any());

        assertNotNull(result);
    }

    @Test
    public void shouldThrowAdvertTypeNotFoundExceptionWhenTypeIsInvalid() {
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.save(any())).thenReturn(advert);

        advertRequest.setType(null);

        assertThrows(AdvertTypeNotFoundException.class,
                () -> service.save(advertRequest, advertiseToken));

        verify(advertiseHttpGateway).getAdvertise(advertiseToken);
        verify(repository, never()).save(any());
    }

    @Test
    public void shouldThrowStateNotFoundExceptionWhenStateIsInvalid() {
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.save(any())).thenReturn(advert);

        advertRequest.setState(null);

        assertThrows(StateNotFoundException.class,
                () -> service.save(advertRequest, advertiseToken));

        verify(advertiseHttpGateway).getAdvertise(advertiseToken);
        verify(repository, never()).save(any());
    }

    @Test
    public void shouldThrowAdvertNotFoundExceptionWhenAdvertNotExist() {
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.findById(advert.getUuid())).thenReturn(Optional.empty());

        assertThrows(AdvertNotFoundException.class,
                () -> service.get(advert.getUuid(), advertiseToken));

        verify(advertiseHttpGateway).getAdvertise(advertiseToken);
        verify(repository).findById(advert.getUuid());
    }

    @Test
    public void shouldReturnListOfAdvertByAdvertiseToken() {
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.findAllByAdvertiseUuid(advertiseResponse.getUuid())).thenReturn(List.of(advert));

        var result = service.listByAdvertise(advertiseToken);

        verify(advertiseHttpGateway).getAdvertise(advertiseToken);
        verify(repository).findAllByAdvertiseUuid(advertiseResponse.getUuid());
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void shouldThrowApplicationExceptionWhenGetAdvertByNotOwner() {
        advert.getAdvertise().setUuid(advertiseResponse.getUuid() + "not-the-same");
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.findById(advert.getUuid())).thenReturn(Optional.of(advert));

        assertThrows(ApplicationException.class,
                () -> service.get(advert.getUuid(), advertiseToken));

        verify(advertiseHttpGateway).getAdvertise(advertiseToken);
        verify(repository).findById(advert.getUuid());
    }

    @Test
    public void shouldReturnAdvertWithSuccess() {
        advert.getAdvertise().setUuid(advertiseResponse.getUuid());
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.findById(advert.getUuid())).thenReturn(Optional.of(advert));

        var result = service.get(advert.getUuid(), advertiseToken);

        verify(advertiseHttpGateway).getAdvertise(advertiseToken);
        verify(repository).findById(advert.getUuid());
        assertNotNull(result);
    }

    @Test
    public void shouldThrowApplicationExceptionWhenUpdateAdvertByNotOwner() {
        advert.getAdvertise().setUuid(advertiseResponse.getUuid() + "not-the-same");
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.findById(advert.getUuid())).thenReturn(Optional.of(advert));

        assertThrows(ApplicationException.class,
                () -> service.update(advert.getUuid(), advertRequest, advertiseToken));

        verify(advertiseHttpGateway).getAdvertise(advertiseToken);
        verify(repository).findById(advert.getUuid());
    }

    @Test
    public void shouldUpdateAdvertWithSuccess() {
        advert.getAdvertise().setUuid(advertiseResponse.getUuid());
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.findById(advert.getUuid())).thenReturn(Optional.of(advert));
        when(repository.save(any())).thenReturn(advert);

        var result = service.update(advert.getUuid(), advertRequest, advertiseToken);

        verify(advertiseHttpGateway).getAdvertise(advertiseToken);
        verify(repository).save(any());

        assertNotNull(result);
    }

    @Test
    public void shouldUploadJpgImageWithSuccess() {
        var file = buildMultiPartFile("image.jpg");

        advert.getAdvertise().setUuid(advertiseResponse.getUuid());
        var nextImageNumber = advert.totalImageLinkRegistered() + 1;
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.findById(advert.getUuid())).thenReturn(Optional.of(advert));
        when(awsS3Service.uploadImage(eq(file), eq(advert.getUuid()), eq(nextImageNumber))).thenReturn("url");

        var result = service.uploadImage(advert.getUuid(), advertiseToken, file);

        verify(awsS3Service).uploadImage(eq(file), eq(advert.getUuid()), eq(nextImageNumber));
        assertNotNull(result);
    }

    @Test
    public void shouldUploadJpegImageWithSuccess() {
        var file = buildMultiPartFile("image.jpeg");

        advert.getAdvertise().setUuid(advertiseResponse.getUuid());
        var nextImageNumber = advert.totalImageLinkRegistered() + 1;
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.findById(advert.getUuid())).thenReturn(Optional.of(advert));
        when(awsS3Service.uploadImage(eq(file), eq(advert.getUuid()), eq(nextImageNumber))).thenReturn("url");

        var result = service.uploadImage(advert.getUuid(), advertiseToken, file);

        verify(awsS3Service).uploadImage(eq(file), eq(advert.getUuid()), eq(nextImageNumber));
        assertNotNull(result);
    }

    @Test
    public void shouldUploadPngImageWithSuccess() {
        var file = buildMultiPartFile("image.png");

        advert.getAdvertise().setUuid(advertiseResponse.getUuid());
        var nextImageNumber = advert.totalImageLinkRegistered() + 1;
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.findById(advert.getUuid())).thenReturn(Optional.of(advert));
        when(awsS3Service.uploadImage(eq(file), eq(advert.getUuid()), eq(nextImageNumber))).thenReturn("url");

        var result = service.uploadImage(advert.getUuid(), advertiseToken, file);

        verify(awsS3Service).uploadImage(eq(file), eq(advert.getUuid()), eq(nextImageNumber));
        assertNotNull(result);
    }

    @Test
    public void shouldThrowApplicationExceptionWhenIsMoreThen4ImagesRegistered() {
        var file = buildMultiPartFile("image.jpg");

        advert.getAdvertise().setUuid(advertiseResponse.getUuid());

        advert.addImageLink("1");
        advert.addImageLink("2");
        advert.addImageLink("3");
        advert.addImageLink("4");

        var nextImageNumber = advert.totalImageLinkRegistered() + 1;
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.findById(advert.getUuid())).thenReturn(Optional.of(advert));

        var exception = assertThrows(ApplicationException.class,
                () -> service.uploadImage(advert.getUuid(), advertiseToken, file));

        verify(awsS3Service, never()).uploadImage(eq(file), eq(advert.getUuid()), eq(nextImageNumber));
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getHttpStatus());
        assertEquals("Limite de 4 imagens por anúncio excedido", exception.getMessage());
    }

    @Test
    public void shouldThrowApplicationExceptionOnUploadImageWhenAdvertiseIsNotTheOwnerOfAdvert() {
        var file = buildMultiPartFile("image.jpg");
        advert.getAdvertise().setUuid(advertiseResponse.getUuid() + "not-the-same");

        var nextImageNumber = advert.totalImageLinkRegistered() + 1;
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.findById(advert.getUuid())).thenReturn(Optional.of(advert));

        var exception = assertThrows(ApplicationException.class,
                () -> service.uploadImage(advert.getUuid(), advertiseToken, file));

        verify(awsS3Service, never()).uploadImage(eq(file), eq(advert.getUuid()), eq(nextImageNumber));
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getHttpStatus());
    }

    @Test
    public void shouldThrowApplicationExceptionWhenImageExtensionIsNotValid() {
        var file = buildMultiPartFile(".txt");

        advert.getAdvertise().setUuid(advertiseResponse.getUuid());
        var nextImageNumber = advert.totalImageLinkRegistered() + 1;
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.findById(advert.getUuid())).thenReturn(Optional.of(advert));

        var exception = assertThrows(ApplicationException.class,
                () -> service.uploadImage(advert.getUuid(), advertiseToken, file));

        verify(awsS3Service, never()).uploadImage(eq(file), eq(advert.getUuid()), eq(nextImageNumber));
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getHttpStatus());
    }

    @Test
    public void shouldThrowApplicationExceptionWhenImageNameIsNull() {
        var file = buildMultiPartFile(null);

        advert.getAdvertise().setUuid(advertiseResponse.getUuid());
        var nextImageNumber = advert.totalImageLinkRegistered() + 1;
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.findById(advert.getUuid())).thenReturn(Optional.of(advert));

        var exception = assertThrows(ApplicationException.class,
                () -> service.uploadImage(advert.getUuid(), advertiseToken, file));

        verify(awsS3Service, never()).uploadImage(eq(file), eq(advert.getUuid()), eq(nextImageNumber));
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getHttpStatus());
    }

    @Test
    public void shouldThrowApplicationExceptionWhenDeleteAdvertByNotOwner() {
        advert.getAdvertise().setUuid(advertiseResponse.getUuid() + "not-the-same");
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.findById(advert.getUuid())).thenReturn(Optional.of(advert));

        assertThrows(ApplicationException.class,
                () -> service.delete(advert.getUuid(), advertiseToken));

        verify(advertiseHttpGateway).getAdvertise(advertiseToken);
        verify(repository).findById(advert.getUuid());
        verify(repository, never()).delete(advert);
    }

    @Test
    public void shouldDeleteAdvertWithSuccess() {
        advert.getAdvertise().setUuid(advertiseResponse.getUuid());
        when(advertiseHttpGateway.getAdvertise(advertiseToken)).thenReturn(advertiseResponse);
        when(repository.findById(advert.getUuid())).thenReturn(Optional.of(advert));

        service.delete(advert.getUuid(), advertiseToken);

        verify(advertiseHttpGateway).getAdvertise(advertiseToken);
        verify(repository).findById(advert.getUuid());
        verify(repository).delete(advert);
    }

    @Test
    public void shouldSendAdvertInterestEmailWithSuccess() {
        var advertInterestRequest = new AdvertInterestRequest();
        when(repository.findById(advert.getUuid())).thenReturn(Optional.of(advert));

        service.sendAdvertInterestEmail(advert.getUuid(), advertInterestRequest);

        verify(awsSqsService).sendToAdvertInterestEmailQueue(advertInterestRequest);
    }
}