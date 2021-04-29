package com.nutrymaco.tester.asserting;

public record NotExpectedExceptionAssertResult(Throwable exception) implements AssertResult {
    @Override
    public Object expected() {
        return "no exception";
    }

    @Override
    public Object actual() {
        return exception;
    }
}
