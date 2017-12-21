package com.waysli.tools.security.demo.converter;

import java.lang.annotation.Native;

public class LongId extends Number implements Comparable<LongId> {
    private long value;

    public LongId() {
        value = 0;
    }

    @Override
    public int intValue() {
        return (int)value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return (float)value;
    }

    @Override
    public double doubleValue() {
        return (double)value;
    }

    public LongId(long val) {
        value = val;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new Long(value).toString();
    }

    public static LongId valueOf(String val) {
        return new LongId(Long.valueOf(val));
    }

    @Override
    public int compareTo(LongId anotherLong) {
        return (value < anotherLong.value) ? -1 : ((value == anotherLong.value) ? 0 : 1);
    }

    @Native
    private static final long serialVersionUID = 4290774380558185355L;
}
