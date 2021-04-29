package com.nutrymaco.tester.executing;

import com.nutrymaco.tester.annotations.*;
import com.nutrymaco.tester.asserting.AssertException;
import com.nutrymaco.tester.asserting.AssertResult;
import com.nutrymaco.tester.asserting.NotExpectedExceptionAssertResult;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;


public class TestExecutor {

    private TestExecutor() {

    }

    public static TestExecutor of() {
        return new TestExecutor();
    }

    public <T> void execute(T ... instances) {
        for (T instance : instances) {
            System.out.println("Executing - " + instance.getClass().getSimpleName());
            execute(instance);
        }
    }

    public <T> void execute(T instance) {
        Arrays.stream(instance.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(BeforeAll.class))
                .findFirst()
                .ifPresent(method -> {
                    try {
                        method.setAccessible(true);
                        method.invoke(instance);
                        System.out.println("beforeAll executed");
                    } catch (AssertException | IllegalAccessException | InvocationTargetException ignored) {
                    }
                });


        Arrays.stream(instance.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Test.class))
                .sorted((method1, method2) -> {
                    int orderNumber1 = method1.getAnnotation(Test.class).order();
                    int orderNumber2 = method2.getAnnotation(Test.class).order();
                    return Integer.compare(orderNumber1, orderNumber2);
                })
                .forEach(method -> {
                    long time;
                    try {
                        method.setAccessible(true);
                        long start = System.currentTimeMillis();
                        method.invoke(instance);
                        time = System.currentTimeMillis() - start;
                    } catch (AssertException assertException) {
                        ResultManager.addAssertErrorResult(assertException.getAssertResult(), method);
                        return;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        ResultManager.addAssertErrorResult(new NotExpectedExceptionAssertResult(e.getCause()), method);
                        e.getCause().printStackTrace();
                        return;
                    }
                    ResultManager.addAssertSuccessResult(method.getName(), time);
                });

        Arrays.stream(instance.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AfterAll.class))
                .findFirst()
                .ifPresent(method -> {
                    try {
                        method.setAccessible(true);
                        method.invoke(instance);
                        System.out.println("after all executed");
                    } catch (AssertException | IllegalAccessException | InvocationTargetException ignored) {
                    }
                });

        ResultManager.onComplete();
    }
}
