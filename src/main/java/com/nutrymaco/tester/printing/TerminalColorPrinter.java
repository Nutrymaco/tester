package com.nutrymaco.tester.printing;

import com.nutrymaco.tester.asserting.AssertResult;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class TerminalColorPrinter {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    public static void printResults(int testCount, int testCompleted, Map<String, Long> times) {
        if (testCount == testCompleted) {
            System.out.printf("%s%d/%d TEST COMPLETED%s%n\n", ANSI_GREEN, testCompleted, testCount, ANSI_RESET);
        } else {
            System.out.printf("%s%d/%d TEST COMPLETED%s\n",ANSI_RED, testCompleted, testCount, ANSI_RESET);
        }

        times.entrySet().stream()
                .map(entry -> ANSI_PURPLE + entry.getKey() + " : " + ((double)entry.getValue() / 1000) + "s" + ANSI_RESET)
                .forEach(System.out::println);
    }

    public static void printAssertResult(AssertResult<?> assertResult, Method method) {
        System.out.println(ANSI_RED +
                "method : " + method.getName() + "\n" +
                "expected : " + assertResult.expected() + "\n" +
                "actual   : " + assertResult.actual() + "\n" +
                ANSI_RESET
                );
    }
}
