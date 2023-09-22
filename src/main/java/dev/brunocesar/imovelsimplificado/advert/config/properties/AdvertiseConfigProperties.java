package dev.brunocesar.imovelsimplificado.advert.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "advertise")
public record AdvertiseConfigProperties(String host) {
}
