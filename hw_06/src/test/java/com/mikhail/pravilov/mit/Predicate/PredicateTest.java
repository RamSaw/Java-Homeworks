package com.mikhail.pravilov.mit.Predicate;

import org.junit.Test;

import static org.junit.Assert.*;

public class PredicateTest {
    private Predicate<Integer> equals10 = (x) -> x == 10;
    private Predicate<Integer> more10 = (x) -> x > 10;
    private Predicate<Integer> less10 = (x) -> x < 10;
    private Predicate<Integer> notEquals10 = (x) -> x != 10;

    private Predicate<Integer> equals20 = (x) -> x == 20;
    private Predicate<Integer> more20 = (x) -> x > 20;
    private Predicate<Integer> less20 = (x) -> x < 20;
    private Predicate<Integer> notEquals20 = (x) -> x != 20;

    @Test
    public void or() throws Exception {
        assertTrue(equals10.or(more10).apply(10));
        assertTrue(less10.or(more10).apply(-10));
        assertTrue(notEquals10.or(equals10).apply(100));
        assertTrue(notEquals10.or(equals10).apply(10));

        assertFalse(less10.or(more10).apply(10));
        assertFalse(equals10.or(more10).apply(9));
        assertFalse(more10.or(more10).apply(10));
        assertFalse(notEquals10.or(more10).apply(10));
    }

    @Test
    public void and() throws Exception {
        assertTrue(more10.and(more10).apply(11));
        assertTrue(more20.and(more10).apply(21));
        assertTrue(more10.and(less20).apply(15));
        assertTrue(notEquals10.and(more10).apply(12));

        assertFalse(equals10.and(more10).apply(10));
        assertFalse(less10.and(more10).apply(-10));
        assertFalse(notEquals10.and(equals10).apply(100));
        assertFalse(notEquals10.and(equals10).apply(10));
    }

    @Test
    public void not() throws Exception {
        assertTrue(more10.not().apply(10));
        assertFalse(more20.not().apply(21));

        assertTrue(less10.not().apply(15));
        assertFalse(less20.not().apply(15));

        assertTrue(equals10.not().apply(15));
        assertFalse(equals20.not().apply(20));

        assertFalse(notEquals10.not().apply(15));
        assertTrue(notEquals20.not().apply(20));
    }

}