package com.mikhail.pravilov.mit.threadPool;

import com.mikhail.pravilov.mit.lightFuture.LightExecutionException;
import com.mikhail.pravilov.mit.lightFuture.LightFuture;
import org.junit.Test;

import java.util.ArrayList;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class ThreadPoolTest {
    @Test
    public void commonUsage() throws Exception {
        ThreadPool<Integer> pool = new ThreadPool<>(5);
        LightFuture<Integer> task = pool.addTask(() -> 2 * 2);
        assertEquals(new Integer(4), task.get());
        LightFuture<Integer> task1 = pool.addTask(() -> 2 * 3);
        LightFuture<Integer> task2 = task1.thenApply((i) -> i + 1);
        LightFuture<Integer> task3 = task1.thenApply((i) -> i + 2);
        assertEquals(new Integer(6), task1.get());
        assertEquals(new Integer(7), task2.get());
        assertEquals(new Integer(8), task3.get());
    }

    @Test
    public void isReady() throws Exception {
        ThreadPool<Integer> pool = new ThreadPool<>(5);
        ArrayList<LightFuture<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            tasks.add(pool.addTask(() -> 10 + 10));
        }
        while (!allTasksIsReady(tasks)) {
            Thread.sleep(100);
        }
        for (LightFuture<Integer> task : tasks) {
            assertTrue(task.isReady());
            assertEquals(new Integer(20), task.get());
        }
    }

    @Test(expected = LightExecutionException.class)
    public void lightExecutionExceptionThrows() throws Exception {
        ThreadPool<Integer> pool = new ThreadPool<>(1);
        LightFuture<Integer> task = pool.addTask(() -> {
            throw new RuntimeException();
        });
        task.get();
    }

    @Test
    public void startsNThreads() throws Exception {
        ThreadPool<Integer> pool = new ThreadPool<>(5);
        for (Thread thread : pool.getThreads()) {
            assertTrue(thread.isAlive());
        }
    }

    @Test
    public void shutDown() throws Exception {
        ThreadPool<Integer> pool = new ThreadPool<>(5);
        for (int i = 0; i < 100; i++) {
            pool.addTask(() -> 10 + 10);
        }
        pool.shutDown();
        Thread.sleep(1000);
        for (Thread thread : pool.getThreads()) {
            assertFalse(thread.isAlive());
        }
    }

    @Test
    public void taskThenApplyStartsOnlyAfter() throws Exception {
        ThreadPool<Integer> pool = new ThreadPool<>(5);
        for (int i = 0; i < 100; i++) {
            pool.addTask(() -> 10 + 10);
        }
        LightFuture<Integer> task = pool.addTask(() -> 2 * 2);
        LightFuture<Integer> nextTask = task.thenApply((i) -> i + 10);
        while (!task.isReady()) {
            assertFalse(nextTask.isReady());
        }
    }

    @Test
    public void taskThenApplySeveralTimes() throws Exception {
        ThreadPool<Integer> pool = new ThreadPool<>(5);
        ArrayList<LightFuture<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            tasks.add(pool.addTask(() -> 10 + 10));
        }

        LightFuture<Integer> task = pool.addTask(() -> 2 * 2);
        tasks.add(task);
        LightFuture<Integer> nextTask = task.thenApply((i) -> i + 10);
        tasks.add(nextTask);
        nextTask = nextTask.thenApply((i) -> i * 5);
        tasks.add(nextTask);
        nextTask = nextTask.thenApply((i) -> i / 7);
        tasks.add(nextTask);

        for (int i = 0; i < 100; i++) {
            pool.addTask(() -> 10 * 10);
        }

        while (!allTasksIsReady(tasks)) {
            Thread.sleep(100);
        }
        assertEquals(new Integer(4), task.get());
        assertEquals(new Integer(10), nextTask.get());
    }

    private boolean allTasksIsReady(ArrayList<LightFuture<Integer>> tasks) {
        for (LightFuture<Integer> task : tasks) {
            if (!task.isReady()) {
                return false;
            }
        }

        return true;
    }
}