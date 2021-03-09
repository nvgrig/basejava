package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamApiTests {

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(3);
        integers.add(2);
        integers.add(3);
        integers = oddOrEven(integers);
        integers.forEach(System.out::print);
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce((v1, v2) -> ((v1 * 10) + v2)).getAsInt();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().reduce(Integer::sum).get();
        return integers.stream().filter((p) -> (isEven(sum) != isEven(p))).collect(Collectors.toList());
    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
    }
}


