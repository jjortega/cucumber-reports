package com.github.mkolisnyk.cucumber.runner.assertion;

import org.junit.Assert;

public class LazyAssert extends Assert {

    public LazyAssert() {
        // TODO Auto-generated constructor stub
    }
    public static void fail(String message) {
        throw new LazyAssertionError(message);
    }
}
