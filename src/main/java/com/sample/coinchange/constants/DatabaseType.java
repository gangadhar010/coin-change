package com.sample.coinchange.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DatabaseType {
    H2("H2"),
    IN_MEMORY("IN_MEMORY");

    private final String database;
}
