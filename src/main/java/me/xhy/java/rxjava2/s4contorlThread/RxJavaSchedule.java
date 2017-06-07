package me.xhy.java.rxjava2.s4contorlThread;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Created by xuhuaiyu on 2017/5/25.
 */
public class RxJavaSchedule {

    public static void main(String[] args) {
        // 没有切换线程的
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                System.out.println("[Observable] " + Thread.currentThread().getName());
                e.onNext("1");
                e.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("[Observer,onSubscribe] " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(String s) {
                System.out.println("[Observer,onNext] " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("[Observer,onError] " + Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                System.out.println("[Observer,onComplete] " + Thread.currentThread().getName());
            }
        });

        // 切换线程
        /**
         * 1. Schedulers.newThread() 总是新建线程
         * 2. Schedulers.io() I/O 读写的线程（读写数据库、网络交互、读写文件）
         *  行为与 newThread 差不多，不过 io 可以重用线程，效率更高。注意：io 是一个无数量限制的线程池。
         *  不要将计算类型的任务放到 io 中，避免新建不必要的线程
         * 3. Schedulers.computation() CPU 密集型计算的线程，不会被 I/O 等操作限制的性能的计算。
         *  不要将 I/O 操作放到这种调度中，避免因阻塞造成 CPU 时间的浪费。
         * 4. ...
         */
        System.out.println("======= 切换线程 =======");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                System.out.println("[Observable] " + Thread.currentThread().getName());
                e.onNext("2");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread()) /** subscribeOn 指定 Observable 的线程池*/
        .observeOn(Schedulers.newThread()) /** observeOn 指定 Observer 的线程池*/
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("[Observer,onSubscribe] " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
                System.out.println("[Observer,onNext] " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("[Observer,onError] " + Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                System.out.println("[Observer,onComplete] " + Thread.currentThread().getName());
            }
        });


        // 等待子线程执行完毕
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
