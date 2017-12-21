package com.waysli.tools.security.demo.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import javax.validation.constraints.NotNull;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptor;

public class IdSerializer extends JsonSerializer<LongId> {

    private String prop;

    /**
     * @param prop 属性名
     */
    public IdSerializer(@NotNull String prop) {

        this.prop = prop;
    }

    @Override
    public void serialize(LongId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
//        try {
//            PropertyDescriptor pd = getPropertyDescriptor(value, prop);
//            if (pd == null || pd.getReadMethod() == null) {
                gen.writeString((value.getValue() ^ 0x2AAAAAAA) + "");
//                return;
//            }
//            Method m = pd.getReadMethod();
//            m.setAccessible(true);
//            gen.writeObject(m.invoke(value));
//        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//            throw new CommonException(e);
//        }
    }
}
