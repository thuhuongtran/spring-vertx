package com.vtcc.demo.core.extension.functional;

@FunctionalInterface
public interface SupplierThrowable<T> {

    T get() throws Exception;
}
