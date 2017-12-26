package com.mikhail.pravilov.mit.sp;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public final class FirstPartTasks {

    private FirstPartTasks() {}

    // Список названий альбомов
    public static List<String> allNames(Stream<Album> albums) {
        return albums.map(Album::getName).collect(toList());
    }

    // Список названий альбомов, отсортированный лексикографически по названию
    public static List<String> allNamesSorted(Stream<Album> albums) {
        return albums.map(Album::getName).sorted().collect(toList());
    }

    // Список треков, отсортированный лексикографически по названию, включающий все треки альбомов из 'albums'
    public static List<String> allTracksSorted(Stream<Album> albums) {
        return albums.flatMap((album) -> album.getTracks().stream()).
                map(Track::getName).sorted().collect(toList());
    }

    // Список альбомов, в которых есть хотя бы один трек с рейтингом более 95, отсортированный по названию
    public static List<Album> sortedFavorites(Stream<Album> s) {
        return s.filter(album -> album.getTracks().stream().anyMatch(track -> track.getRating() > 95)).sorted(Comparator.comparing(Album::getName)).collect(toList());
    }

    // Сгруппировать альбомы по артистам
    public static Map<Artist, List<Album>> groupByArtist(Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Album::getArtist));
    }

    // Сгруппировать альбомы по артистам (в качестве значения вместо объекта 'Album' использовать его имя)
    public static Map<Artist, List<String>> groupByArtistMapName(Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Album::getArtist, mapping(Album::getName, toList())));
    }

    // Число повторяющихся альбомов в потоке
    public static long countAlbumDuplicates(Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(e -> e, Collectors.counting())).
                entrySet().stream().filter(albumLongEntry -> albumLongEntry.getValue() > 1).count();
    }

    // Альбом, в котором максимум рейтинга минимален
    // (если в альбоме нет ни одного трека, считать, что максимум рейтинга в нем --- 0)
    public static Optional<Album> minMaxRating(Stream<Album> albums) {
        return albums.min((album1, album2) -> {
            Track max1 = album1.getTracks().stream().max(Comparator.comparingInt(Track::getRating)).orElse(new Track("", 0));
            Track max2 = album2.getTracks().stream().max(Comparator.comparingInt(Track::getRating)).orElse(new Track("", 0));
            return max1.getRating() - max2.getRating();
        });
    }

    // Список альбомов, отсортированный по убыванию среднего рейтинга его треков (0, если треков нет)
    public static List<Album> sortByAverageRating(Stream<Album> albums) {
        return albums.sorted((album1, album2) -> {
            double average1 = album1.getTracks().stream().mapToInt(Track::getRating).average().orElse(0);
            double average2 = album2.getTracks().stream().mapToInt(Track::getRating).average().orElse(0);
            return Double.compare(average2, average1);
        }).collect(toList());
    }

    // Произведение всех чисел потока по модулю 'modulo'
    // (все числа от 0 до 10000)
    public static int moduloProduction(IntStream stream, int modulo) {
        return stream.reduce(1, (prev, n) -> (prev * n) % modulo);
    }

    // Вернуть строку, состояющую из конкатенаций переданного массива, и окруженную строками "<", ">"
    // см. тесты
    public static String joinTo(String... strings) {
        return "<" + Arrays.stream(strings).collect(Collectors.joining(", ")) + ">";
    }

    // Вернуть поток из объектов класса 'clazz'
    @SuppressWarnings("unchecked")
    public static <R> Stream<R> filterIsInstance(Stream<?> s, Class<R> clazz) {
        return (Stream<R>) s.filter(clazz::isInstance);
    }
}