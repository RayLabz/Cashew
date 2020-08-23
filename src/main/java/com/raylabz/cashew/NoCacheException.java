package com.raylabz.cashew;

public class NoCacheException extends Exception {

    NoCacheException(final Class<?> aClass) {
        super("No cache found for class '" + aClass.getSimpleName() + "'. Use Cashew.registerClass() to create a new cache for this class.");
    }

    NoCacheException() {
        super("No main cache found");
    }

}
