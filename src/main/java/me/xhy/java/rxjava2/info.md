# 目的

RxJava的目的就是*异步*。

# 概念

1. Observable：被观察者。
2. Observer：观察者，可接收Observable发送的数据。
3. subscribe：订阅，观察者与被观察者，通过subscribe()方法进行订阅。
4. Subscriber：也是一种观察者，在2.0中，它与Observer没什么实质的区别，不同的是 Subscriber要与Flowable(也是一种被观察者)联合使用，该部分内容是2.0新增的。  
    Obsesrver用于订阅Observable，而Subscriber用于订阅Flowable。
    

# 大纲

## s1.meet.MeetRxJava2

分别说明 Observable 、 Observer 、 subscribe  和 如何给他们建立联系。

## s2.join.JoinThreePartsOfMeet

1. 将 Observable 、 Observer 、 subscribe 合并

2. 解释说明 ObservableEmitter 和 Disposable

## s3.otherWaysOfObservable

1. Observable 的其他创建方式

    just()
    fromIterable()
    defer()
    interval()
    range()
    timer()
    repeat()