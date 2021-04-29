package com.nutrymaco.tester.asserting;

public class AssertException extends RuntimeException {
    private final AssertResult assertResult;
    public AssertException(AssertResult<?> assertResult) {
        this.assertResult = assertResult;
    }

    public AssertResult getAssertResult() {
        return assertResult;
    }
}
