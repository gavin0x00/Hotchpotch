package me.newtrekwang.customwidget.task;


import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;

public class WangAsyncTask {
    /**
     * cpu核心数
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * 核心线程：至少两个线程，最多4个线程。
     */
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT-1,4));
    /**
     * 最多线程数
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    /**
     * 多余线程存活时间
     */
    private static final int KEEP_ALIVE_SECONDS = 30;

    /**
     * 处理任务的线程池
     */
    public static final Executor THREAD_POOL_EXECUTOR;
    /**
     * 任务队列
     */
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingDeque<>(128);
    /**
     * 线程工厂
     */
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        /**
         * 线程安全的计数器
         */
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable,"AsyncTask #"+mCount.getAndIncrement());
        }
    };

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,sPoolWorkQueue,sThreadFactory
        );
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }

    /**
     * 串行任务执行器
     */
    private static final Executor SERIAL_EXECUTOR = new SerialExecutor();
    /**
     * 默认任务执行器
     */
    private static volatile Executor sDefaultExecutor = SERIAL_EXECUTOR;
    /**
     * @className WangAsyncTask
     * @createDate 2019/6/20 17:52
     * @author newtrekWang
     * @email 408030208@qq.com
     * @desc 串行任务执行器类
     *
     */
    private static class SerialExecutor implements Executor {
        final ArrayDeque<Runnable> mTasks = new ArrayDeque<>();
        Runnable mActive;
        @Override
        public synchronized void execute(@NonNull final Runnable runnable) {
            // 入队一个runnable,对原始的runnable加了点修饰
            mTasks.offer(new Runnable() {
                @Override
                public void run() {
                    try {
                        runnable.run();
                    } finally {
                        // 任务执行后，要检查是否有下一个runnable需要执行
                        scheduleNext();
                    }
                }
            });
            // 第一次的时候需要触发下才能执行
            if (mActive == null){
                scheduleNext();
            }
        }

        /**
         * 检查是否有下一个runnable需要执行，如果有，则交给另一个线程池执行
         */
        protected synchronized void scheduleNext(){
            if ((mActive = mTasks.poll()) != null){
                THREAD_POOL_EXECUTOR.execute(mActive);
            }
        }
    }


    /**
     * AsyTask状态
     */
    private volatile Status mStatus = Status.PENDING;

    public enum Status{
        /**
         * 准备状态
         */
        PENDING,
        /**
         * 运行状态
         */
        RUNNING,
        /**
         * 结束状态
         */
        FINISH,
    }



}
