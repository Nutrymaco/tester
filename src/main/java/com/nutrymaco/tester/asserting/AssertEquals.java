package com.nutrymaco.tester.asserting;

import java.util.Objects;

public class AssertEquals {

    public static <T> Expecter<T> actual(T actual) {
        return new EqualsExpecter<>(actual);
    }

    public static class EqualsExpecter<T> implements Expecter<T> {
        private final T actual;

        public EqualsExpecter(T actual) {
            this.actual = actual;
        }

        public void expect(T expected) {
            if (!Objects.equals(expected, actual)) {
                throw new AssertException(
                        new EqualsAssertResult<>(expected, actual)
                );
            }

        }
    }

    public static record EqualsAssertResult<T>(T expected, T actual)
    implements AssertResult<T>{}
}
