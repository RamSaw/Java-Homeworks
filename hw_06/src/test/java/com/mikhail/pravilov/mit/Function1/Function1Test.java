package com.mikhail.pravilov.mit.Function1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Function1Test {
    private Function1<Integer, Integer> mul10 = (x) -> x * 10;
    private Function1<Integer, Integer> div10 = (x) -> x / 10;
    private Function1<Integer, Integer> add10 = (x) -> x + 10;
    private Function1<Integer, Integer> sub10 = (x) -> x - 10;

    @Test
    public void apply() throws Exception {
        Integer expected;
        expected = 100;
        assertEquals(expected, mul10.apply(10));
        expected = 1;
        assertEquals(expected, div10.apply(10));
        expected = 0;
        assertEquals(expected, sub10.apply(10));
        expected = 20;
        assertEquals(expected, add10.apply(10));
    }

    @Test
    public void compose() throws Exception {
        Integer expected;
        expected = 90;
        assertEquals(expected, mul10.compose(sub10).apply(10));
        expected = 2;
        assertEquals(expected, add10.compose(div10).apply(10));
        expected = 10;
        assertEquals(expected, add10.compose(sub10).apply(10));
        expected = 10;
        assertEquals(expected, div10.compose(mul10).apply(10));
    }

}