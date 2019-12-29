package com.laurynas.kacinauskas.revolut.util;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ClassInjector {

    private static final Injector injector = Guice.createInjector();

    private ClassInjector() {
    }

    public static <T> T getInstance(Class<T> clazz) {
        return injector.getInstance(clazz);
    }

}
