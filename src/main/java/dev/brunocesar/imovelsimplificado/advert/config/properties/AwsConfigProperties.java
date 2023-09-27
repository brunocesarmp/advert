package dev.brunocesar.imovelsimplificado.advert.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws")
public record AwsConfigProperties(String accessKey, String secretAccess, Sqs sqs, S3 s3) {

    public record Sqs(String advertInterestedEmailQueue) {
    }

    public record S3(String advertImagesBucket) {
    }
}
