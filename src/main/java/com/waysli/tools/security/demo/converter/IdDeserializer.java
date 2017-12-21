package com.waysli.tools.security.demo.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.IOException;

public class IdDeserializer extends JsonDeserializer<LongId> implements ContextualDeserializer {

    @Setter
    private Class<LongId> longCls;

    private String prop;

    /**
     * @param prop 属性名
     */
    public IdDeserializer(@NotNull String prop) {
        this.prop = prop;
    }

    @Override
    public LongId deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
        String text = parser.getText();
        return new LongId(Long.valueOf(text) ^ 0x2AAAAAAA);
    }

    @Override
    public JsonDeserializer createContextual(DeserializationContext ctx, BeanProperty property) throws JsonMappingException {
        Class rawCls = ctx.getContextualType().getRawClass();

        Class<LongId> longCls = (Class<LongId>) rawCls;
        IdDeserializer clone = new IdDeserializer(prop);
        clone.setLongCls(longCls);
        return clone;
    }
}
