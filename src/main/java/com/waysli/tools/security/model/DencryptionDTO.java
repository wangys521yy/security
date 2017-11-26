package com.waysli.tools.security.model;

/**
 * 封装加解密所使用的key和要加解密的文本Text
 *
 * @author waysli
 * @create 2017-10-18 下午2:24
 **/
public class DencryptionDTO {
    private String key;
    private String text;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
