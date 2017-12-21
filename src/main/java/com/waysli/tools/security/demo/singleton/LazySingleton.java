package com.waysli.tools.security.demo.singleton;

public class LazySingleton {
    private static LazySingleton singleton = null;

    private LazySingleton() {

    }

    public synchronized static LazySingleton getInstance() {
        if (singleton == null) {
            singleton = new LazySingleton();
        }
        return singleton;
    }
}
