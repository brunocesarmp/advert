package dev.brunocesar.imovelsimplificado.advert.domains.enuns;

import dev.brunocesar.imovelsimplificado.advert.exceptions.AdvertTypeNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AdvertType {
    SALE,
    LEASE;

    private static final List<String> ADVERT_TYPE_NAMES;

    static {
        ADVERT_TYPE_NAMES = Arrays.stream(AdvertType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public static AdvertType toEnum(String value) {
        return Arrays.stream(AdvertType.values())
                .filter(at -> at.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new AdvertTypeNotFoundException(value, ADVERT_TYPE_NAMES));
    }
}
