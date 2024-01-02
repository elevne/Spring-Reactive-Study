package com.study.reactor.chapter8;

import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class Example8_6 {

    public static void main(String[] args) throws InterruptedException {
        Flux
                .interval(Duration.ofMillis(1L))
                .doOnNext(data -> System.out.println("# emitted by original Flux: " + data))
                .onBackpressureBuffer(2,
                        dropped -> System.out.println("** Overflow & Dropped: " + dropped + " **"),
                        BufferOverflowStrategy.DROP_OLDEST)
                .doOnNext(data -> System.out.println("[ # emitted by Buffer: " + data + " ]"))
                .publishOn(Schedulers.parallel(), false, 1)
                .subscribe(data -> {
                            try {
                                Thread.sleep(5L);
                            } catch (InterruptedException e) {}
                            System.out.println("# onNext: " + data);
                        },
                        error -> System.out.println("# onError"));
        Thread.sleep(3000L);
    }


}
