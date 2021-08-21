package com.egs.shop.model.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
public enum Gender {
    MALE(0),
    FEMALE(1);

    private final int value;

    @JsonCreator
    public static Gender fromValue(int value) {
        return Stream.of(Gender.values())
                .filter(g -> g.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("undefined value found for type " + value));
    }

    @JsonValue
    public int getValue() {
        return value;
    }
}
