package com.mikhail.pravilov.mit.Function2;

import com.mikhail.pravilov.mit.Function1.Function1;
import org.junit.Test;

import static org.junit.Assert.*;

public class Function2Test {
    private Function2<Integer, Integer, Integer> sum = (x, y) -> x + y;
    private Function2<Integer, Integer, Integer> sub = (x, y) -> x - y;
    private Function2<Integer, Integer, Integer> mul = (x, y) -> x * y;
    private Function2<Integer, Integer, Integer> div = (x, y) -> x / y;

    private Function1<Integer, Integer> mul10 = (x) -> x * 10;
    private Function1<Integer, Integer> div10 = (x) -> x / 10;
    private Function1<Integer, Integer> add10 = (x) -> x + 10;
    private Function1<Integer, Integer> sub10 = (x) -> x - 10;

    @Test
    public void apply() throws Exception {
        Integer expected;
        expected = 20;
        assertEquals(expected, mul.apply(10, 2));
        expected = 5;
        assertEquals(expected, div.apply(10, 2));
        expected = 0;
        assertEquals(expected, sub.apply(10, 10));
        expected = 20;
        assertEquals(expected, sum.apply(10, 10));
    }

    @Test
    public void compose() throws Exception {
        Integer expected;
        expected = 10;
        assertEquals(expected, mul.compose(div10).apply(10, 10));
        expected = 200;
        assertEquals(expected, sum.compose(mul10).apply(10, 10));
        expected = -10;
        assertEquals(expected, div.compose(sub10).apply(10, 20));
        expected = 500;
        assertEquals(expected, sub.compose(mul10).apply(100, 50));
    }

    @Test
    public void bind1() throws Exception {
        Integer expected;
        expected = 100;
        assertEquals(expected, mul.bind1(10).apply(10));
        expected = 1;
        assertEquals(expected, div.bind1(10).apply(10));
        expected = 0;
        assertEquals(expected, sub.bind1(10).apply(10));
        expected = 20;
        assertEquals(expected, sum.bind1(10).apply(10));
    }

    @Test
    public void bind2() throws Exception {
        Integer expected;
        expected = 40;
        assertEquals(expected, mul.bind2(10).apply(4));
        expected = 1;
        assertEquals(expected, div.bind2(10).apply(10));
        expected = -6;
        assertEquals(expected, sub.bind2(10).apply(4));
        expected = 14;
        assertEquals(expected, sum.bind2(10).apply(4));
    }

    @Test
    public void curry() throws Exception {
        Integer expected;
        expected = 20;
        assertEquals(expected, mul.curry(2).apply(10));
        expected = 5;
        assertEquals(expected, div.curry(2).apply(10));
        expected = 0;
        assertEquals(expected, sub.curry(10).apply(10));
        expected = 20;
        assertEquals(expected, sum.curry(10).apply(10));
    }

}