package com.waysli.tools.security.demo.singleton;

public class HolderSingleton {
    private static class SingletonHolder {
        private static HolderSingleton singleton = new HolderSingleton();

        private SingletonHolder() {

        }
    }

    private HolderSingleton() {

    }

    public static HolderSingleton getInstance() {
        return SingletonHolder.singleton;
    }
}
