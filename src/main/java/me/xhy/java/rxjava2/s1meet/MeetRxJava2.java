package me.xhy.java.rxjava2.s1meet;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by xuhuaiyu on 2017/5/23.
 */
public class MeetRxJava2 {

    public static void main(String[] args) {

        /**
         * step 3 创建订阅关系
         */
        MeetRxJava2 meetRxJava2 = new MeetRxJava2();
        Observable observable = meetRxJava2.getObservable();
        Observer observer = meetRxJava2.getObserver();

        /** */
        observable.subscribe(observer);

    }

    /**
     * 使用 RxJava 时，有一些标准动作：
     * 1. 创建 Observable : Observable 是 被观察者（也叫 主题）
     * 2. 创建 Observe : 观察者
     * 3. 创建订阅关系 Subscribe
     */

    /**
     * step 1 : 创建 Observable
     * @return Observable
     */
    public Observable<String> getObservable() {

        /**
         * 1. 使用 Observable.create() 创建 Observable ， 这是最基本的方式
         * 2. 需要传入 ObservableOnSubscribe 对象，该对象相当于一个计划表，
         *      当 Observable 被订阅的时候，ObservableOnSubscribe 的 subscribe() 方法会自动被调用
         * 3. subscribe() 方法中提供了一个 ObservableEmitter 时间发射器， 提供发射事件的方法
         * 4. 使用 ObservableEmitter 对象的 onNext() 方法发射事件
         * 5. onNext() 方法的次序，是事件发生的次序
         */
        return Observable.create(new ObservableOnSubscribe<String>() {
            public void subscribe(ObservableEmitter<String> e) throws Exception {

                // 搞事情：如执行一些逻辑、操作一些数据
                // ...
                // 后
                /**
                 * onNext 用来发射事件，相当于观察者模式中，被观察者发生状态改变时调用的 change() 方法。
                 */
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");

                /**
                 * onComplete() 和 onError() 只能发生一个，写在后面的将不会被执行
                 *
                 * onError() 写在 onComplete() 后面会引发异常；而反过来则不会
                 */
                e.onComplete();
//                e.onError(new RuntimeException("一个运行时异常"));

            }
        });

    }

    /**
     * step 2 : 创建 Observer
     * @return Observer
     */
    public Observer<String> getObserver() {

        return new Observer<String>() {

            /**
             * 创建订阅关系时， 该方法被调用
             * @param d
             */
            public void onSubscribe(Disposable d) {
                System.out.println("创建订阅关系");
            }

            /**
             * 当 Observable 发射事件（也就是调用了 onNext() 方法时）时，对应会 Observer 的 onNext() 方法会被调用
             * 被观察者的 onNext 执行一次，观察者的 onNext 对应的执行一次。
             * @param s
             */
            public void onNext(String s) {
                System.out.println("被观察者发射事件：" + s);
            }

            public void onError(Throwable e) {
                System.out.println("被观察者发生异常：" + e.getMessage());
            }

            public void onComplete() {
                System.out.println("被观察者完成");
            }
        };
    }
}
