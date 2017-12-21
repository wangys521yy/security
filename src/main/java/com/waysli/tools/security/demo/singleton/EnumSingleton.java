package com.waysli.tools.security.demo.singleton;

public enum EnumSingleton {
    SINGLETON("This is the easiest singleton pattern, but not the most readable.");

    String comment = null;
    EnumSingleton(String s) {
        this.comment = s;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + comment;
    }

    public static EnumSingleton getInstance() {
        return SINGLETON;
    }

    public static void main(String[] args) {
        System.out.println(EnumSingleton.getInstance());
    }
}
