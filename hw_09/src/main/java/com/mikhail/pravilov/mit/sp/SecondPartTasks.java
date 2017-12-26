package com.mikhail.pravilov.mit.sp;

import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public final class SecondPartTasks {

    private SecondPartTasks() {}

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(List<String> paths, CharSequence sequence) {
        return paths.stream().flatMap(path -> {
            try {
                return Files.lines(Paths.get(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(s -> s.contains(sequence)).collect(toList());
    }

    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        Integer numOfShots = 10000000;
        return (double) Stream.generate(() -> {
            Random random = new Random();
            double x = random.nextDouble(), y = random.nextDouble();
            return Math.sqrt(x * x + y * y);
        }).limit(numOfShots).filter(dist -> dist <= 1).count() / numOfShots;
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(Map<String, List<String>> compositions) throws NullPointerException {
        return compositions.entrySet().stream().
                max(Comparator.comparingInt(e -> e.getValue().stream().mapToInt(String::length).sum())).
                orElse(new AbstractMap.SimpleEntry<>("", Collections.emptyList())).getKey();
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(List<Map<String, Integer>> orders) {
        return orders.stream().flatMap(m -> m.entrySet().stream()).collect(groupingBy(Map.Entry::getKey, summingInt(Map.Entry::getValue)));
    }
}
