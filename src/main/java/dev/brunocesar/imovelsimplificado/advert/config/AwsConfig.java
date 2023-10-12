package dev.brunocesar.imovelsimplificado.advert.config;

import dev.brunocesar.imovelsimplificado.advert.config.properties.AwsConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AwsConfig {

    private final AwsConfigProperties awsConfigProperties;

    public AwsConfig(AwsConfigProperties awsConfigProperties) {
        this.awsConfigProperties = awsConfigProperties;
    }

    @Bean
    public AwsCredentialsProvider awsCredentialsProviderLive() {
        var credentials = AwsBasicCredentials.create(awsConfigProperties.accessKey(), awsConfigProperties.secretAccess());
        return StaticCredentialsProvider.create(credentials);
    }

    @Bean
    public S3Client s3Client(AwsCredentialsProvider awsCredentialsProvider) {
        return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(awsCredentialsProvider)
                .build();
    }

    @Bean
    public SqsClient sqsClient(AwsCredentialsProvider awsCredentialsProvider) {
        return SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(awsCredentialsProvider)
                .build();
    }
}
