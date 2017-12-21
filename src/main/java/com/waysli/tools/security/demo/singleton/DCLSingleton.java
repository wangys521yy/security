package com.waysli.tools.security.demo.singleton;

public class DCLSingleton {
    private static volatile DCLSingleton singleton = null;

    private DCLSingleton() {

    }

    public static DCLSingleton getInstance() {
        if (singleton == null) {
            synchronized (DCLSingleton.class) {
                if (singleton == null) {
                    singleton = new DCLSingleton();
                }
            }
        }
        return singleton;
    }
}
