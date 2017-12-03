package com.mikhail.pravilov.mit.Collections;

import com.mikhail.pravilov.mit.Function1.Function1;
import com.mikhail.pravilov.mit.Function2.Function2;
import com.mikhail.pravilov.mit.Predicate.Predicate;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class CollectionsTest {
    private List<Integer> integers = Arrays.asList(10, 20, 30, 40);

    private Function1<Integer, Integer> mul10 = (x) -> x * 10;
    private Function1<Integer, Integer> div10 = (x) -> x / 10;
    private Function1<Integer, Integer> add10 = (x) -> x + 10;
    private Function1<Integer, Integer> sub10 = (x) -> x - 10;

    private Predicate<Integer> equals20 = (x) -> x == 20;
    private Predicate<Integer> more20 = (x) -> x > 20;
    private Predicate<Integer> less20 = (x) -> x < 20;
    private Predicate<Integer> notEquals20 = (x) -> x != 20;

    private Function2<Integer, Integer, Integer> sum = (x, y) -> x + y;
    private Function2<Integer, Integer, Integer> sub = (x, y) -> x - y;
    private Function2<Integer, Integer, Integer> mul = (x, y) -> x * y;
    private Function2<Integer, Integer, Integer> div = (x, y) -> x / y;

    private Function2<Integer, Integer, Integer> firstArgument = (x, y) -> x;
    private Function2<Integer, Integer, Integer> secondArgument = (x, y) -> y;

    @Test
    public void map() throws Exception {
        List<Integer> expected;
        expected = Arrays.asList(100, 200, 300, 400);
        assertEquals(expected, Collections.map(mul10, integers));

        expected = Arrays.asList(1, 2, 3, 4);
        assertEquals(expected, Collections.map(div10, integers));

        expected = Arrays.asList(20, 30, 40, 50);
        assertEquals(expected, Collections.map(add10, integers));

        expected = Arrays.asList(0, 10, 20, 30);
        assertEquals(expected, Collections.map(sub10, integers));
    }

    @Test
    public void filter() throws Exception {
        List<Integer> expected;
        expected = Arrays.asList(10);
        assertEquals(expected, Collections.filter(less20, integers));

        expected = Arrays.asList(30, 40);
        assertEquals(expected, Collections.filter(more20, integers));

        expected = Arrays.asList(20);
        assertEquals(expected, Collections.filter(equals20, integers));

        expected = Arrays.asList(10, 30, 40);
        assertEquals(expected, Collections.filter(notEquals20, integers));
    }

    @Test
    public void takeWhile() throws Exception {
        List<Integer> expected;
        expected = Arrays.asList(10);
        assertEquals(expected, Collections.takeWhile(less20, integers));

        expected = Arrays.asList();
        assertEquals(expected, Collections.takeWhile(more20, integers));

        expected = Arrays.asList();
        assertEquals(expected, Collections.takeWhile(equals20, integers));

        expected = Arrays.asList(10);
        assertEquals(expected, Collections.takeWhile(notEquals20, integers));
    }

    @Test
    public void takeUnless() throws Exception {
        List<Integer> expected;
        expected = Arrays.asList();
        assertEquals(expected, Collections.takeUnless(less20, integers));

        expected = Arrays.asList(10, 20);
        assertEquals(expected, Collections.takeUnless(more20, integers));

        expected = Arrays.asList(10);
        assertEquals(expected, Collections.takeUnless(equals20, integers));

        expected = Arrays.asList();
        assertEquals(expected, Collections.takeUnless(notEquals20, integers));
    }

    @Test
    public void foldr() throws Exception {
        Integer expected;
        expected = 100;
        assertEquals(expected, Collections.foldr(sum, 0, integers));

        expected = 1;
        assertEquals(expected, Collections.foldr(div, 40 * 30 * 20 * 10, integers));

        expected = -100;
        assertEquals(expected, Collections.foldr(sub, 0, integers));

        expected = 40 * 30 * 20 * 10;
        assertEquals(expected, Collections.foldr(mul, 1, integers));

        expected = 1;
        assertEquals(expected, Collections.foldr(firstArgument, 1, integers));

        expected = 40;
        assertEquals(expected, Collections.foldr(secondArgument, 1, integers));
    }

    @Test
    public void foldl() throws Exception {
        Integer expected;
        expected = 100;
        assertEquals(expected, Collections.foldl(sum, 0, integers));

        expected = -20;
        assertEquals(expected, Collections.foldl(sub, 0, integers));

        expected = 40 * 30 * 20 * 10;
        assertEquals(expected, Collections.foldl(mul, 1, integers));

        expected = 10;
        assertEquals(expected, Collections.foldl(firstArgument, 1, integers));

        expected = 1;
        assertEquals(expected, Collections.foldl(secondArgument, 1, integers));
    }
}