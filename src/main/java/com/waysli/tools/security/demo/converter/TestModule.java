package com.waysli.tools.security.demo.converter;


import lombok.Data;

@Data
public class TestModule {
    private LongId id1;
    @IdMixer
    private LongId id2;
}
