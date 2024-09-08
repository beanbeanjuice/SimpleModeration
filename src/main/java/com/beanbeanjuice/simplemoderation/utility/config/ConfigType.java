package com.beanbeanjuice.simplemoderation.utility.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConfigType {

    GENERAL ("config.yml"),
    MESSAGES ("messages.yml");

    private final String fileName;

}
