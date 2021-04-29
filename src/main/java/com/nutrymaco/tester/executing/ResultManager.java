package com.nutrymaco.tester.executing;

import com.nutrymaco.tester.asserting.AssertResult;
import com.nutrymaco.tester.printing.TerminalColorPrinter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ResultManager {

    private static int completed = 0;
    private static int count = 0;
    private static Map<String, Long> times = new HashMap<>();

    public static void addAssertErrorResult(AssertResult<?> assertResult, Method method) {
        count++;
        TerminalColorPrinter.printAssertResult(assertResult, method);
    }

    public static void addAssertSuccessResult(String methodName, long time) {
        completed++;
        count++;
        times.put(methodName, time);
    }

    public static void onComplete() {
        TerminalColorPrinter.printResults(count, completed, times);
    }
}
