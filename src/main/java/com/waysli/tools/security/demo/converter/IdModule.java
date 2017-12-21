package com.waysli.tools.security.demo.converter;

import com.fasterxml.jackson.databind.module.SimpleModule;

import javax.validation.constraints.NotNull;

public class IdModule extends SimpleModule {

    /**
     * @param prop 属性名
     */
    public IdModule(@NotNull String prop){

        addDeserializer(LongId.class, new IdDeserializer(prop));
        addSerializer(LongId.class, new IdSerializer(prop));
    }
}
