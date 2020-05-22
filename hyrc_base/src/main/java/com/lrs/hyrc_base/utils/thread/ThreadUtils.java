package com.lrs.hyrc_base.utils.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class ThreadUtils {
    //线程池容器
    public static ExecutorService poolExecutor;
    //线程池总数
    private static int THREADNUMS = 5;

    /**
     * 实例线程池
     */
    private static ExecutorService getThread() {
        if (poolExecutor == null) {
            poolExecutor = Executors.newFixedThreadPool(THREADNUMS);
        }
        return poolExecutor;
    }

    /**
     * 启动线程
     */
    public static void runThread(Runnable runnable) {
        if (poolExecutor == null) {
            getThread();
        }
        poolExecutor.execute(runnable);
    }

    /**
     * 延时执行 任务
     *
     * @param senCond 秒
     * @param onNext  执行方法
     */
    public static void timeLapse(int senCond, Consumer onNext) {
        Observable.timer(senCond, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext);
    }
}
