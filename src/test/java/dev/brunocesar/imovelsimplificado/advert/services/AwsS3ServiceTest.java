package dev.brunocesar.imovelsimplificado.advert.services;

import dev.brunocesar.imovelsimplificado.advert.config.properties.AwsConfigProperties;
import dev.brunocesar.imovelsimplificado.advert.exceptions.ApplicationException;
import dev.brunocesar.imovelsimplificado.advert.utils.TestDataCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import static dev.brunocesar.imovelsimplificado.advert.utils.TestDataCreator.buildMultiPartFile;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AwsS3ServiceTest {

    @InjectMocks
    private AwsS3Service service;

    @Mock
    private S3Client s3Client;

    @Mock
    private AwsConfigProperties awsConfigProperties;

    private AwsConfigProperties.S3 s3;

    private MultipartFile file;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        s3 = new AwsConfigProperties.S3("s3-bucket");
        file = buildMultiPartFile("image.jpg");
    }

    @Test
    public void shouldUploadImageWithSuccess() {
        when(awsConfigProperties.s3()).thenReturn(s3);
        when(s3Client.putObject((PutObjectRequest) any(), (RequestBody) any())).thenReturn(null);

        var result = service.uploadImage(file, "advert-uuid", 1);

        verify(s3Client).putObject((PutObjectRequest) any(), (RequestBody) any());
        assertNotNull(result);
        assertTrue(result.contains("s3.amazonaws"));
    }

    @Test
    public void shouldThrowApplicationExceptionOnUploadImageError() {
        when(awsConfigProperties.s3()).thenReturn(s3);
        when(s3Client.putObject((PutObjectRequest) any(), (RequestBody) any())).thenThrow(new RuntimeException());

        var exception = assertThrows(ApplicationException.class,
                () -> service.uploadImage(file, "advert-uuid", 1));

        verify(s3Client).putObject((PutObjectRequest) any(), (RequestBody) any());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getHttpStatus());
    }

}