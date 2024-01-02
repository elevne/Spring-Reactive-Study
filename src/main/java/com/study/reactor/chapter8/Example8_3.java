package com.study.reactor.chapter8;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

// DROP 전략
public class Example8_3 {

    public static void main(String[] args) throws InterruptedException {
        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureDrop(dropped -> System.out.println("# dropped: " + dropped))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                    try {
                        Thread.sleep(5L);
                    } catch (InterruptedException e) {
                    }
                    System.out.println("# onNext: " + data);
                },
                        error -> System.out.println("# onError"));
        Thread.sleep(2000L);
    }

}
