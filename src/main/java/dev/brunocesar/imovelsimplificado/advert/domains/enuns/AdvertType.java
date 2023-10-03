package dev.brunocesar.imovelsimplificado.advert.domains.enuns;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum AdvertType {
    SALE,
    LEASE;

    public static final List<String> ADVERT_TYPE_NAMES;

    static {
        ADVERT_TYPE_NAMES = Arrays.stream(AdvertType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public static Optional<AdvertType> toEnum(String value) {
        return Arrays.stream(AdvertType.values())
                .filter(at -> at.name().equalsIgnoreCase(value))
                .findFirst();
    }
}
