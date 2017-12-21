package com.waysli.tools.security.demo.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.io.Serializable;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class IdMixAnnotationFormatterFactory implements AnnotationFormatterFactory<IdMixer> {
    private final Set<Class<?>> fieldTypes;
    private final IdFormatter formatter;

    public IdMixAnnotationFormatterFactory() {
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(LongId.class);
        this.fieldTypes = set;
        this.formatter = new IdFormatter();
    }

    @Override
    public Set<Class<?>> getFieldTypes() {
        return fieldTypes;
    }

    @Override
    public Printer<?> getPrinter(IdMixer idMixer, Class<?> aClass) {
        return formatter;
    }

    @Override
    public Parser<?> getParser(IdMixer idMixer, Class<?> aClass) {
        return formatter;
    }

    private class IdFormatter implements Formatter<LongId>, Serializable {
        private static final long serialVersionUID = 1818656464607971661L;

        @Override
        public String print(LongId value, Locale locale) {
            if(value == null) {
                return "0";
            }
            return "qwe" + value.toString();
        }

        @Override
        public LongId parse(String value, Locale locale) throws ParseException {
            if(StringUtils.isNotBlank(value)){
                value = value.substring(3);
            }
            return LongId.valueOf(value);
        }
    }
}
