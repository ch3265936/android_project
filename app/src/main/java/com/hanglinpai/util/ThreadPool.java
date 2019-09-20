package com.hanglinpai.util;

import android.os.SystemClock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 无
 *
 * @author xuhe Email:874983926@qq.com
 * @version 1.0
 * @since 2014-9-24 t上午9:18:52
 */
public class ThreadPool implements IThreadPool {
    private static ThreadPool instance;
    private ThreadPoolExecutor threadPoolExecuror;
    /**
     * 线程池维护线程的最少数量
     */
    private final static int corePoolSize = 1;
    /**
     * 线程池维护线程的最大数量
     */
    private final static int maximumPoolSize = 6;
    /**
     * 线程池维护线程所允许的空闲时间
     */
    private final static long keepAliveTime = 5 * 1000;

    private final static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(
            5);

    public static ThreadPool getInstance() {
        if (instance == null) {
            instance = new ThreadPool();
        }
        return instance;
    }

    public ThreadPool() {
        threadPoolExecuror = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                workQueue, new ThreadPoolExecutor.DiscardPolicy()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {

            }
        };
    }

    public void run(Runnable runnable) {
        threadPoolExecuror.execute(runnable);
    }

    /**
     * 默认尝试连接5次，请在子线程中调用
     *
     * @param callable
     * @param r
     * @return
     */
    public boolean timeOutJudge(Callable<Integer> callable, Runnable r) {
        return timeOutJudge(callable, r, 5);
    }

    /**
     * @param callable
     * @param r
     * @param times
     * @return
     */
    public boolean timeOutJudge(Callable<Integer> callable, Runnable r,
                                int times) {
        int count = 1;
        Integer status = 0;
        try {
            status = callable.call();

            while (status < 0) {
                if (count >= times) {
                    r.run();
                    return false;
                }
                SystemClock.sleep(800);
                status = callable.call();
                count++;
            }
            return true;
            // 出来说明请求成功
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
