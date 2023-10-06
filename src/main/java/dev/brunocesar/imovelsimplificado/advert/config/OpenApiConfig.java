package dev.brunocesar.imovelsimplificado.advert.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

    private static final String INFO_TITLE = "Advert System";
    private static final String INFO_DESCRIPTION = "Sistema responsável pelos Anúncios de Imóveis";

    @Value("${server.url:}")
    private String serverUrl;

    @Value("${server.servlet.context-path:}")
    private String serverContextPath;

    @Bean
    public OpenApiCustomizer customOpenApiCustomizer() {
        var applicationUrl = serverUrl + serverContextPath;
        return openApi -> {
            openApi.setInfo(getInfo());
            openApi.setServers(List.of(getServer(applicationUrl)));
        };
    }

    private Server getServer(String applicationUrl) {
        var prdServer = new Server();
        prdServer.setUrl(applicationUrl);
        return prdServer;
    }

    private Info getInfo() {
        var info = new Info();
        info.setTitle(INFO_TITLE);
        info.setDescription(INFO_DESCRIPTION);
        return info;
    }
}