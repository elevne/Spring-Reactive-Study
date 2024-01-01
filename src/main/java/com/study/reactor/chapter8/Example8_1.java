package com.study.reactor.chapter8;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

// Backpressure Eg
public class Example8_1 {

    public static void main(String[] args) {
        Flux.range(1, 5)
                .doOnRequest(data -> System.out.println("# doOnRequest: " + data))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(1);
                    }
                    @Override
                    protected void hookOnNext(Integer value) {
                        try {
                            Thread.sleep(2000L);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("# hookOnNext: " + value);
                        request(1);
                    }
                });
    }

}
