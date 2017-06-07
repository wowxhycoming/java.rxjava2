package me.xhy.java.rxjava2.s3otherWays;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuhuaiyu on 2017/5/24.
 *
 * 创建 Observable 的其他方式
 * 创建 Observer 的其他方式
 */
public class OtherWaysOfObservableAndObserver {

    public static void main(String[] args) {

        System.out.println("======= just =======");
        /**
         * just() 方式
         *
         * 使用 just() ， 将为创建一个 Observable 并自动为你调用 onNext() 发射数据。
         * 通过 just() 方式直接触发 onNext() ， just 中传递的参数将直接在 Observer 的 onNext() 方法中接收到。
         * just() 方式以 onComplete() 结束。
         */
        Observable<String> observableJust = Observable.just("Hello Just","Just Hello");

        observableJust.subscribe(new Consumer<String>() {
            /**
             * Consumer 中的 accept 方法相当于 ObservableOnSubscribe 中的 onNext
             */
            @Override
            public void accept(String s) throws Exception {
                System.out.println("[just - same as onNext] [" + Thread.currentThread().getName() + "] " + s);
            }
        }, new Consumer<Throwable>() {
            /**
             * Consumer 中的 accept 方法相当于 ObservableOnSubscribe 中的 onError
             */
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("[just - same as onError]" + throwable.getMessage());
            }
        }, new Action() {
            /**
             * Action 中的 run 方法相当于 ObservableOnSubscribe 中的 onComplete
             */
            @Override
            public void run() throws Exception {
                System.out.println("[just - same as onComplete]");
            }
        }, new Consumer<Disposable>() {
            /**
             * Consumer 中的 accept 方法相当于 ObservableOnSubscribe 中的 onSubscribe
             */
            @Override
            public void accept(Disposable disposable) throws Exception {
                System.out.println("[just - same as onSubscribe]");
            }
        });

        System.out.println("======= fromIterable =======");
        /**
         * fromIterable() 方式
         *
         * 使用 fromIterable() ，遍历集合，发送每个 item 。相当于多次回调 onNext() 方法，每次传入一个 item。
         * Collection 是 Iterable 的子接口，所以所有 Collection 接口的实现类都可以作为 Iterable 对象直接传入 fromIterable() 方法。
         */
        List<String> list = new ArrayList<String>();
        for(int i = 0; i < 10; i++) {
            list.add("Hello Iterable "+i);
        }
        Observable<String> observableInterable = Observable.fromIterable((Iterable<String>) list);

        observableInterable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("[Iterable - onNext] [" + Thread.currentThread().getName() + "] " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("[Iterable - onError] ");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("[Iterable - onComplete] ");
            }
        });

        System.out.println("======= defer =======");
        /**
         * defer() 方式
         *
         * 当观察者订阅时，才创建 Observable ，并且针对每个观察者创建都是一个新的 Observable。
         * 以何种方式创建这个 Observable 对象，当满足回调条件后，就会进行相应的回调。
         */
        Observable<String> observableDefer = Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.just("hello defer");
            }
        });

        observableDefer.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("[defer - onNext] [" + Thread.currentThread().getName() + "] " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("[defer - onError] ");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("[defer - onComplete] ");
            }
        });

        System.out.println("======= interval 需要主线程存活 =======");
        /**
         * interval() 方式
         *
         * 创建一个按固定时间间隔发射整数序列的 Observable ，可用作定时器。
         * 即按照固定2秒一次调用 onNext() 方法。
         */
        Observable.interval(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long l) throws Exception {
                System.out.println("[interval - onNext] [" + Thread.currentThread().getName() + "] " + l);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("[interval - onError] ");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("[interval - onComplete] ");
            }
        });

        System.out.println("======= range =======");
        /**
         * range() 方式
         *
         * 创建一个发射特定整数序列的 Observable，第一个参数为起始值，第二个为发送的个数，如果为0则不发送，负数则抛异常。
         * 上述表示发射1到20的数。即调用20次nNext()方法，依次传入1-20数字。
         */
        Observable.range(1,20).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("[range - onNext ] [" + Thread.currentThread().getName() + "] " + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("[range - onError ] ");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("[range - onComplete] ");
            }
        });

        System.out.println("======= timer 需要主线程存活 =======");
        /**
         * timer() 方式
         *
         * 创建一个 Observable ，它在一个给定的延迟后发射一个特殊的值，即表示延迟2秒后，调用 onNext() 方法。
         */
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println("[timer - onNext ] [" + Thread.currentThread().getName() + "] " + aLong);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("[timer - onError ] ");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("[timer - onComplete] ");
            }
        });

        /**
         * repeat() 方式
         *
         * 创建一个 Observable ，该Observable 的事件可以重复调用。
         */
        Observable.just(123).repeat().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("[repeat - onNext ] [" + Thread.currentThread().getName() + "] " + integer + " 发太快，下面睡一会");
                TimeUnit.SECONDS.sleep(2);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("[repeat - onError ] ");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("[repeat - onComplete] ");
            }
        });


        // 让 interval 和 timer 有效果，创建等待
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
