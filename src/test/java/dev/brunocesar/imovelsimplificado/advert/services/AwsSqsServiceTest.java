package dev.brunocesar.imovelsimplificado.advert.services;

import dev.brunocesar.imovelsimplificado.advert.config.properties.AwsConfigProperties;
import dev.brunocesar.imovelsimplificado.advert.controllers.requests.AdvertInterestRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AwsSqsServiceTest {

    @InjectMocks
    private AwsSqsService service;

    @Mock
    private SqsClient sqsClient;

    @Mock
    private AwsConfigProperties awsConfigProperties;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSendToAdvertInterestQueueWithSuccess() {
        when(awsConfigProperties.sqs()).thenReturn(new AwsConfigProperties.Sqs("queue-mock"));
        when(sqsClient.sendMessage((SendMessageRequest) any())).thenReturn(null);

        service.sendToAdvertInterestEmailQueue(new AdvertInterestRequest());

        verify(sqsClient).sendMessage((SendMessageRequest) any());
    }
}