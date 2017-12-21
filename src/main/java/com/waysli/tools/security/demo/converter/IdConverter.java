package com.waysli.tools.security.demo.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class IdConverter implements Converter<String, LongId> {

        @Override
        public LongId convert(String source) {
            if (StringUtils.isBlank(source) || source.length() <= 3) {
                return null;
            }

            return new LongId(Long.valueOf(source) ^ 0x2AAAAAAA);
        }
}
