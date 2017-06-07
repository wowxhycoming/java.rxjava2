package me.xhy.java.rxjava2.s2join;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by xuhuaiyu on 2017/5/23.
 */
public class JoinThreePartsOfMeet {

    public static void main(String[] args) {
        /**
         * 将 MeetRxJava2 中的3部标准动作进行连接合并
         */
        Observable.create(new ObservableOnSubscribe<String>() {
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
                e.onComplete();
            }
        }).subscribe(new Observer<String>() {
            public void onSubscribe(Disposable d) {
                System.out.println("创建订阅关系");
            }

            public void onNext(String s) {
                System.out.println("被观察者发射事件：" + s);
            }

            public void onError(Throwable e) {
                System.out.println("被观察者发生异常：" + e.getMessage());
            }

            public void onComplete() {
                System.out.println("被观察者完成");
            }
        });

        /**
         * ObservableEmitter
         *
         * ObservableEmitter ： 它可以发出三种类型的事件，
         *  通过调用emitter的onNext(T value)、onComplete()和onError(Throwable error)就可以分别发出next事件、complete事件和error事件。
         *  1. Observable 可以发射任意个 onNext， Observer 也可以接受任意个 onNext
         *  2. 当 Observable 发射了一个 onComplete后，Observable 仍然可以继续发送 onNext ，但是当 Observer 接收到 onComplete ，就不再接收 onNext 了。
         *  3. onError 的同上。
         *  4. Observable 可以不发送 onComplete 或 onError 。
         *  5. onComplete 和 onError 都为 自身和互相唯一互斥， 就是他们中的一个只能发生一次 。
         *      多个 onComplete 多次发送不会导致程序崩溃，
         *      但是 onError 在 onComplete或onError之后发送，当 Observer 收到第二个 onError 时，将导致程序崩溃
         */

        /**
         * Disposable
         *
         * Disposable, 这个单词的字面意思是一次性用品，用完即可丢弃的。
         *  那么在RxJava中怎么去理解它呢, 可以把他理解成连接 Observable 和 Observer 的管道,
         *  当调用它的dispose()方法时, Observable 和 Observer 就不再拥有订阅关系了，这并不影响 Observable 继续发射事件。
         */
        System.out.println("======= Disposable =======");
        Observable.create(new ObservableOnSubscribe<String>() {
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                System.out.println("[Observable] before onNext 1");
                e.onNext("1");
                System.out.println("[Observable] after onNext 1");

                System.out.println("[Observable] before onNext 2");
                e.onNext("2");
                System.out.println("[Observable] after onNext 2");

                System.out.println("[Observable] before onNext 3");
                e.onNext("3");
                System.out.println("[Observable] after onNext 3");

                System.out.println("[Observable] before onComplete");
                e.onComplete();
                System.out.println("[Observable] after onComplete");
            }
        }).subscribe(new Observer<String>() {

            /**
             * Observer 内全局化 Disposable ，扩大 Disposable 的可见范围
             */
            private Disposable disposable;

            public void onSubscribe(Disposable d) {
                System.out.println("[Observer] onNext onSubscribe");

                disposable = d;
            }

            public void onNext(String s) {
                System.out.println("[Observer] onNext " + s);
                /**
                 * 当收到事件 "2" 时， 丢弃订阅关系。
                 */
                if("2".equals(s)) {
                    disposable.dispose();
                    System.out.println("[Observer] disposable.dispose()");
                }
            }

            public void onError(Throwable e) {
                System.out.println("[Observer] onNext onError" + e.getMessage());
            }

            public void onComplete() {
                System.out.println("[Observer] onComplete ");
            }
        });


    }
}
