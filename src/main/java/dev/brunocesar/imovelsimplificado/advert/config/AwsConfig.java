package dev.brunocesar.imovelsimplificado.advert.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AwsConfig {

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {
        return DefaultCredentialsProvider.create();
    }

    @Bean
    public S3Client s3AsyncClient(AwsCredentialsProvider awsCredentialsProvider) {
        return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(awsCredentialsProvider)
                .build();
    }

    @Bean
    public SqsClient sqsAsyncClient(AwsCredentialsProvider awsCredentialsProvider) {
        return SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(awsCredentialsProvider)
                .build();
    }
}
