package dev.brunocesar.imovelsimplificado.advert.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public abstract class AbstractBaseGatewayTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    protected static MockWebServer mockBackEnd;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    protected String getMockWebServerHost() {
        return format("http://localhost:%s", mockBackEnd.getPort());
    }

    protected MockResponse buildMockResponse(int responseCode) {
        return new MockResponse().setResponseCode(responseCode);
    }

    protected <T> MockResponse buildMockResponse(int responseCode, T responseBody) throws Exception {
        return new MockResponse()
                .setBody(MAPPER.writeValueAsString(responseBody))
                .addHeader("Content-Type", APPLICATION_JSON)
                .setResponseCode(responseCode);
    }
}