package net.servzero.helper;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Schedulers {
    private static ExecutorService pool = Executors.newCachedThreadPool();
    private static ScheduledExecutorService scheduledPool = Executors.newSingleThreadScheduledExecutor();

    public static void setMaxScheduleThreads(int threads) {
        scheduledPool.shutdown();
        scheduledPool = Executors.newScheduledThreadPool(threads);
    }

    public static void shutdown() {
        pool.shutdown();
        scheduledPool.shutdown();
    }

    private static List<Scheduler> schedulers;

    /* Runnables */

    public static void runAsync(Runnable r) {
        pool.submit(r);
    }

    public static void runLaterAsync(Runnable r, long millis) {
        scheduledPool.schedule(r, millis, TimeUnit.MILLISECONDS);
    }

    /* Consumers */

    public static <T> void consumeAsync(Consumer<T> c, T param) {
        pool.submit(() -> c.accept(param));
    }

    public static <T> void consumeLaterAsync(Consumer<T> c, T param, long millis) {
        scheduledPool.schedule(() -> c.accept(param), millis, TimeUnit.MILLISECONDS);
    }

    /* Suppliers */

    public static <T> Future<T> getAsync(Supplier<T> s) {
        return pool.submit(s::get);
    }

    public static <T> Future<T> getLaterAsync(Supplier<T> s, long millis) {
        return scheduledPool.schedule(s::get, millis, TimeUnit.MILLISECONDS);
    }

    /* Functions */
    public static <T, R> Future<R> executeAsync(Function<T, R> f, T param) {
        return pool.submit(() -> f.apply(param));
    }

    public static <T, R> Future<R> executeLaterAsync(Function<T, R> f, T param, long millis) {
        return scheduledPool.schedule(() -> f.apply(param), millis, TimeUnit.MILLISECONDS);
    }

    /* Repeating */

    public static ScheduledFuture<?> repeat(Runnable r, long startDelayMillis, long repeatDelayMillis) {
        return scheduledPool.scheduleAtFixedRate(r, startDelayMillis, repeatDelayMillis, TimeUnit.MILLISECONDS);
    }

    /* Factory Methods */

    public static RunningScheduler getScheduler(Runnable r) {
        return new RunningScheduler(r);
    }

    public static <T> ConsumingScheduler<T> getScheduler(Consumer<T> c) {
        return new ConsumingScheduler<>(c);
    }

    public static <T> SupplyingScheduler<T> getScheduler(Supplier<T> s) {
        return new SupplyingScheduler<>(s);
    }

    public static <T, R> ExecutingScheduler<T, R> getScheduler(Function<T, R> function) {
        return new ExecutingScheduler<>(function);
    }

    public static abstract class Scheduler {
        private int id;

        protected Scheduler() {
            this.id = schedulers.size();
            schedulers.add(this);
        }

        public int getId() {
            return id;
        }
    }

    public static class RunningScheduler extends Scheduler {
        private Runnable runnable;

        private RunningScheduler(Runnable runnable) {
            super();
            this.runnable = runnable;
        }

        public void run() {
            runnable.run();
        }
    }

    public static class ConsumingScheduler<T> extends Scheduler{
        private Consumer<T> consumer;

        private ConsumingScheduler(Consumer<T> consumer) {
            super();
            this.consumer = consumer;
        }

        public void consume(T t) {
            consumer.accept(t);
        }
    }

    public static class SupplyingScheduler<T> extends Scheduler {
        private Supplier<T> supplier;

        private SupplyingScheduler(Supplier<T> supplier) {
            super();
            this.supplier = supplier;
        }

        public T get() {
            return supplier.get();
        }
    }

    public static class ExecutingScheduler<T, R> extends Scheduler {
        private Function<T, R> function;

        private ExecutingScheduler(Function<T, R> function) {
            super();
            this.function = function;
        }

        public R execute(T t) {
            return function.apply(t);
        }
    }
}