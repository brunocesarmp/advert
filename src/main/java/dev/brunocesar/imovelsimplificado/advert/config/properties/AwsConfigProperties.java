package dev.brunocesar.imovelsimplificado.advert.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws")
public record AwsConfigProperties(String accessKey, String secretKey, Sqs sqs) {

    public record Sqs(String advertInterestedEmailQueue) {
    }
}
