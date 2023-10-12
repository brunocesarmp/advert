package dev.brunocesar.imovelsimplificado.advert.services;

import dev.brunocesar.imovelsimplificado.advert.config.properties.AwsConfigProperties;
import dev.brunocesar.imovelsimplificado.advert.exceptions.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Service
public class AwsS3Service {

    private static final String CONTENT_TYPE = "image/jpg";

    private final S3Client s3Client;
    private final AwsConfigProperties awsConfigProperties;

    public AwsS3Service(S3Client s3Client, AwsConfigProperties awsConfigProperties) {
        this.s3Client = s3Client;
        this.awsConfigProperties = awsConfigProperties;
    }

    public String uploadImage(MultipartFile file, String advertUuid, int imageNumber) {
        try {
            var key = String.format("%s/image-%s.jpg", advertUuid, imageNumber);
            var request = PutObjectRequest.builder()
                    .bucket(awsConfigProperties.s3().advertImagesBucket())
                    .key(key)
                    .contentType(CONTENT_TYPE)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
            return String.format("https://%s.s3.amazonaws.com/%s", awsConfigProperties.s3().advertImagesBucket(), key);
        } catch (Exception e) {
            log.error("Erro em subir imagem ao S3. Error message: [{}]", e.getMessage(), e);
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro em fazer o upload da imagem");
        }
    }
}
