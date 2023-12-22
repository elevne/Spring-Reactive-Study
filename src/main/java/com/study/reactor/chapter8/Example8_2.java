package com.study.reactor.chapter8;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class Example8_2 {

    public static void main(String[] args) throws InterruptedException {
        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureError()
                .doOnNext(data -> System.out.println("# doOnNext: " + data))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                    try {
                        Thread.sleep(5L);
                    } catch (InterruptedException e) {
                    }
                    System.out.println("# onNext: " + data);
                },
                        error -> System.out.println("# onError")
                        );
        Thread.sleep(2000L);
    }

}
