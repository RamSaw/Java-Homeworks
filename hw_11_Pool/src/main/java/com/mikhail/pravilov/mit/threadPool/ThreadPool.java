package com.mikhail.pravilov.mit.threadPool;

import com.mikhail.pravilov.mit.lightFuture.LightExecutionException;
import com.mikhail.pravilov.mit.lightFuture.LightFuture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Class that realizes thread pool on {@link LightFuture} tasks.
 * @param <R> type of calculated value.
 */
public class ThreadPool<R> {
    /**
     * Threads in thread pool.
     */
    private ArrayList<Thread> threads = new ArrayList<>();
    /**
     * Tasks that are not executed (evaluated) yet.
     */
    final private ArrayList<LightFutureForThreadPool> tasks = new ArrayList<>();

    /**
     * Constructor that create pool of numberOfThreads threads.
     * @param numberOfThreads to create.
     */
    public ThreadPool(int numberOfThreads) {
        Runnable runTask = () -> {
            while (!Thread.interrupted())  {
                LightFutureForThreadPool task = eraseFirstTask();
                if (task != null) {
                    task.execute();
                }
            }
        };
        for (int i = 0; i < numberOfThreads; i++) {
            threads.add(new Thread(runTask));
            threads.get(threads.size() - 1).start();
        }
    }

    /**
     * Creates task ({@link LightFutureForThreadPool} instance) from given supplier and adds it to pool.
     * @param supplier computation to execute.
     * @return created task casted to {@link LightFuture} (cuts off execute method).
     */
    @NotNull
    public LightFuture<R> addTask(@NotNull Supplier<R> supplier) {
        LightFutureForThreadPool task = new LightFutureForThreadPool(supplier);

        synchronized (tasks) {
            tasks.add(task);
        }

        return task;
    }

    /**
     * Calls interrupt() on each thread from {@link ThreadPool#threads} queue. After that all threads will finish in a while.
     */
    public void shutDown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    /**
     * Thread-safely erases first task from {@link ThreadPool#threads} queue.
     * @return {@link LightFutureForThreadPool} if first element exists, otherwise null.
     */
    @Nullable
    private LightFutureForThreadPool eraseFirstTask() {
        LightFutureForThreadPool task = null;
        if (!tasks.isEmpty()) {
            synchronized (tasks) {
                if (!tasks.isEmpty()) {
                    task = tasks.get(0);
                    tasks.remove(0);
                }
            }
        }
        return task;
    }

    /**
     * Getter for array list of running threads in thread pool.
     * @return {@link ThreadPool#threads} - threads of thread pool.
     */
    @NotNull
    public ArrayList<Thread> getThreads() {
        return threads;
    }

    /**
     * Class implements {@link LightFuture} interface.
     * It is specified for {@link ThreadPool} needs and adds {@link LightFutureForThreadPool#execute()} method to start computation.
     * {@link LightFutureForThreadPool#get()} waits for {@link LightFutureForThreadPool#execute()} to evaluate and then returns it.
     */
    private class LightFutureForThreadPool implements LightFuture<R> {
        /**
         * {@link Supplier} that is needed to run computation from it.
         */
        @NotNull
        private Supplier<R> supplier;
        /**
         * Result of computation.
         */
        @Nullable
        private R result;
        /**
         * Null if no exception occurred during computation of {@link LightFutureForThreadPool#supplier}, otherwise it is generated and remembered.
         */
        @Nullable
        private LightExecutionException exception = null;
        /**
         * Flag to indicate if computation is calculated
         */
        private boolean isCalculated = false; // volatile ? (because of isReady())

        /**
         * Constructs task of {@link LightFutureForThreadPool} type that will compute given supplier.
         * @param supplier to compute, this param will be saved to {@link LightFutureForThreadPool#supplier}.
         */
        private LightFutureForThreadPool(@NotNull Supplier<R> supplier) {
            this.supplier = supplier;
        }

        /**
         * Starts thread-safe computation from {@link LightFutureForThreadPool#supplier}.
         * If exception occurred it remembers it in {@link LightFutureForThreadPool#exception} field.
         * Notifies all threads which are waiting for specific class instance that computation is done.
         */
        private void execute() {
            if (!isCalculated) {
                synchronized (this) {
                    if (!isCalculated) {
                        try {
                            result = supplier.get();
                        }
                        catch (Throwable t) {
                            exception = new LightExecutionException(t);
                        }
                        isCalculated = true;
                        notifyAll();
                    }
                }
            }
        }

        @Override
        public boolean isReady() {
            return isCalculated;
        }

        @Override
        @Nullable
        public R get() throws LightExecutionException {
            try {
                synchronized (this) {
                    while (!isReady()) {
                            wait();
                    }
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
            if (exception != null) {
                throw exception;
            }
            return result;
        }

        @NotNull
        @Override
        public LightFuture<R> thenApply(@NotNull Function<? super R, ? extends R> valueOfNextSupplier) {
            return addTask(() -> {
                try {
                    return valueOfNextSupplier.apply(get());
                } catch (LightExecutionException e) {
                    System.err.println("Task cannot be executed. Cause: " + e.getLocalizedMessage());
                }

                return null;
            });
        }
    }
}
