package me.xhy.java.rxjava2.s5operators;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuhuaiyu on 2017/5/25.
 */
public class Operators {

    public static void main(String[] args) {

        // 数据
        List list1 = new ArrayList<String>();
        list1.add("abc");
        list1.add("def");
        list1.add("ghi");
        List list2 = new ArrayList<String>();
        list2.add("jkl");
        list2.add("mno");
        list2.add("pqs");
        List<List<String>> list = new ArrayList<>();
        list.add(list1);
        list.add(list2);

        /**
         * 1. 创建操作符
         * 在 me.xhy.java.rxjava2.s3otherWays.OtherWaysOfObservableAndObserver 中，
         * 介绍通过其他方式创建 Observable 的方式，就是使用的创建操作符
         * create defer empty/never/throw from... interval just range repeat start timer
         */

        /**
         * 2. 变换操作符
         * buffer flatMap groupBy map scan Window
         */
        // map 对原来 Observable 对象发射的每一项数据进行转换，返回一个发射转换数据的 Observable
        Observable.just("hello", "world").map(new Function<String, Integer>() {
            /**
             * 对于触发时间的数据不方便直接应用的情况，使用 map 进行转换后，再发射
             */
            @Override
            public Integer apply(String s) throws Exception {
                return s.length();
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });

        /** flatMap 重新定义 Observable 的结构
         *
         * map : List<List<String>> [["abc","def","ghi"],["jkl","mon","pqs"]] 用 map 只能转换成类似 [length,length] 的形式
         * flatMap : 可以重新定义 Observable 结构， 可以转换成 ["abc","def","ghi","jkl", ...] 的形式
         *  甚至是 ["a","b","c","d", ...] 的形式
         */
        System.out.println("======= flatMap =======");

        Observable.just(list).flatMap(new Function<List<List<String>>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(List<List<String>> lists) throws Exception {
                List<String> fList = new ArrayList<>();
                lists.forEach(e -> fList.addAll(e));
                return Observable.fromIterable(fList);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });

        /**
         * 3. 过滤操作符
         * debounce distinct elementAt filter first ignoreElement last sample skip skipLast take takeLast
         */
        // filter
        System.out.println("======= filter =======");
        Observable.just(list).flatMap(new Function<List<List<String>>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(List<List<String>> lists) throws Exception {
                List<String> fList = new ArrayList<>();
                lists.forEach(e -> fList.addAll(e));
                return Observable.fromIterable(fList);
            }
        }).filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                if (s.charAt(2) - '0' > 60) {
                    return true;
                }
                return false;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println((String)o);
            }
        });

        /**
         * 4. 结合操作符
         */

        /**
         * 5. 错误处理
         */

        /**
         * 6. 辅助操作
         */

        /**
         * 7. 条件和布尔操作
         */

        /**
         * 8. 算数和聚合
         */

        /**
         * 9. 异步
         */

        /**
         * 10. 连接
         */

        /**
         * 11. 转换
         */

        /**
         * 12. 阻塞操作
         */

        /**
         * 13. 字符串操作
         */

        TimeUnit.SECONDS.toSeconds(2);
    }
}
