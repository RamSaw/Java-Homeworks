package com.mikhail.pravilov.mit.Lazy;

import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LazyFactoryTest {
    @Test
    public void createAsynchronizedLazy() throws Exception {
        @SuppressWarnings("unchecked")
        Supplier<Integer> supplierMock = (Supplier<Integer>) mock(Supplier.class);
        Lazy<Integer> lazy = LazyFactory.createAsynchronizedLazy(supplierMock);

        when(supplierMock.get()).thenReturn(10);
        verify(supplierMock, times(0)).get();
        assertEquals(new Integer(10), lazy.get());
        verify(supplierMock, times(1)).get();

        for (int i = 0; i < 100; i++) {
            assertEquals(new Integer(10), lazy.get());
            verify(supplierMock, times(1)).get();
        }
    }

    @Test
    public void createSynchronizedLazy() throws Throwable {
        @SuppressWarnings("unchecked")
        Supplier<Integer> supplierMock = (Supplier<Integer>) mock(Supplier.class);
        Lazy<Integer> lazy = LazyFactory.createSynchronizedLazy(supplierMock);
        final Throwable[] exceptionOccurredInOtherThread = new Throwable[1];
        final boolean[] exceptionOccurredInOtherThreadFlag = {false};

        when(supplierMock.get()).thenReturn(10);
        verify(supplierMock, times(0)).get();

        Runnable runGet100Times = () -> {
            for (int i = 0; i < 100; i++) {
                // Catch is needed because throwing exception in other thread doesn't fail a test
                try {
                    assertEquals(new Integer(10), lazy.get());
                    // Number of invocations will be checked after joining
                }
                catch (final Throwable t) {
                    exceptionOccurredInOtherThreadFlag[0] = true;
                    exceptionOccurredInOtherThread[0] = t;
                    throw t;
                }
            }
        };
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(runGet100Times);
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        assertEquals(new Integer(10), lazy.get());
        verify(supplierMock, times(1)).get();
        if (exceptionOccurredInOtherThreadFlag[0]) {
            throw exceptionOccurredInOtherThread[0];
        }
    }

}