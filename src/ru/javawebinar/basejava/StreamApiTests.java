package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamApiTests {

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));
        List<Integer> integers = Arrays.asList(1, 2, 3, 3, 2, 3);
        integers = oddOrEven(integers);
        integers.forEach(System.out::print);
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (v1, v2) -> v1 * 10 + v2);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        boolean sumIsEven = isEven(integers.stream().reduce(0, Integer::sum));
        return integers.stream().filter(p -> sumIsEven != isEven(p)).collect(Collectors.toList());
    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
    }
}


