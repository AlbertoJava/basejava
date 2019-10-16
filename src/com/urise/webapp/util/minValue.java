package com.urise.webapp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class minValue {
    public static void main(String[] args) {
        List<Integer> values = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            values.add((int) (Math.random() * 10));
         }
        printStream(values.stream(), "Source data");
        System.out.println("Sum of elements =" + values.stream().reduce((x1, x2) -> x1 + x2).get());
        System.out.println("Min number = " + minValue(values.toArray(new Integer[9])));
        printStream(oddOrEven(values).stream(), "List without streams");
        printStream(oddOrEvenOptional(values).stream(), "List created using stream line!");

    }

    private static void printStream(Stream stream, String title) {
        System.out.println("------" + title + "------");
        stream.forEach(System.out::println);
        System.out.println("-------------------------");
    }

    public static int minValue(Integer[] values) {
        AtomicInteger x = new AtomicInteger();
        return (int) Arrays.stream(values)
                .distinct()
                .sorted(Collections.reverseOrder())
                .map((Function<Integer, Object>) elem -> elem * (int) Math.pow(10, x.getAndIncrement()))
                .reduce((z, y) -> (Integer) z + (Integer) y).orElse(0);

    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> evenList = new ArrayList<>();
        List<Integer> oddList = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < integers.size(); i++) {
            int elem = integers.get(i);
            sum = +elem;
            if (elem % 2 == 0) {
                oddList.add(elem);
            } else {
                evenList.add(elem);
            }
        }
        return sum % 2 == 0 ? oddList : evenList;
    }

    static List<Integer> oddOrEvenOptional(List<Integer> integers) {

        return integers.stream()
                .collect(Collectors.partitioningBy((x) -> x % 2 == 0))
                .getOrDefault(integers.stream().reduce((x1, x2) -> x1 + x2).get() % 2 != 0, new ArrayList<>());
    }

}
