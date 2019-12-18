package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStreamApi {

    public static void main(String[] args) {

        int[] ints = {6, 5, 1, 7, 2, 6, 1};
        System.out.println(minValue(ints));
        List<Integer> list = Arrays.stream(ints).boxed().collect(Collectors.toList());
        oddOrEven(list).forEach(i -> System.out.print(i + " "));
    }

    private static int minValue(int[] values) {

        return Arrays.stream(values).boxed().filter(x -> (x > 0 && x < 10)).distinct()
                .sorted()
                .reduce((acc, x) -> acc * 10 + x).get();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream().collect(Collectors.partitioningBy(i -> i % 2 == 0));

        return map.get(map.get(false).size() % 2 != 0);
    }
}
/*
реализовать метод через стрим int minValue(int[] values).
Метод принимает массив цифр от 1 до 9, надо выбрать уникальные и вернуть минимально возможное число,
составленное из этих уникальных цифр. Не использовать преобразование в строку и обратно.
Например {1,2,3,3,2,3} вернет 123, а {9,8} вернет 89
Сложный способ:
Arrays.stream(values).boxed().filter(x -> (x > 0 && x < 10)).distinct()
               .sorted()
               .reduce((acc, x) -> acc*10 + x).get();
              .sorted(Comparator.reverseOrder())
              .collect(Collectors.toList());
              return  IntStream.range(0, integers.size()).boxed().collect(Collectors.toMap(i -> i, integers::get))
                .entrySet()
                .stream()
                .map(e -> (int) Math.pow(10, e.getKey()) * e.getValue()).reduce(0, Integer::sum);

реализовать метод List<Integer> oddOrEven(List<Integer> integers) если сумма всех чисел нечетная -
удалить все нечетные, если четная - удалить все четные. Сложность алгоритма должна быть O(N).
Optional - решение в один стрим.
 */