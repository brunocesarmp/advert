package dev.brunocesar.imovelsimplificado.advert.domains.enuns;

import dev.brunocesar.imovelsimplificado.advert.exceptions.StateNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum State {
    RJ,
    SP,
    MG;

    public static final List<String> STATES_NAMES;

    static {
        STATES_NAMES = Arrays.stream(State.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public static Optional<State> toEnum(String value) {
        return Arrays.stream(State.values())
                .filter(at -> at.name().equalsIgnoreCase(value))
                .findFirst();
    }
}
