package dev.brunocesar.imovelsimplificado.advert.services;

import com.google.gson.Gson;
import dev.brunocesar.imovelsimplificado.advert.config.properties.AwsConfigProperties;
import dev.brunocesar.imovelsimplificado.advert.controllers.requests.AdvertInterestRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class AwsSqsService {

    private final SqsClient sqsClient;
    private final AwsConfigProperties awsConfigProperties;

    public AwsSqsService(SqsClient sqsClient, AwsConfigProperties awsConfigProperties) {
        this.sqsClient = sqsClient;
        this.awsConfigProperties = awsConfigProperties;
    }

    public void sendToAdvertInterestEmailQueue(AdvertInterestRequest request) {
        var message = SendMessageRequest.builder()
                .queueUrl(awsConfigProperties.sqs().advertInterestedEmailQueue())
                .messageBody(new Gson().toJson(request))
                .build();
        sqsClient.sendMessage(message);
    }
}
