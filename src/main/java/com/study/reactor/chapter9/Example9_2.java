package com.study.reactor.chapter9;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

import static com.study.reactor.chapter9.Example9_1.doTask;

public class Example9_2 {

    public static void main(String[] args) throws InterruptedException {
        int tasks = 6;
        Sinks.Many<String> unicastSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<String> fluxView = unicastSink.asFlux();
        IntStream
                .range(1, tasks)
                .forEach(n -> {
                    try {
                        new Thread(() -> {
                            unicastSink.emitNext(doTask(n), Sinks.EmitFailureHandler.FAIL_FAST);
                            System.out.println("# emitted: " + n);
                        }).start();
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                });
        fluxView
                .publishOn(Schedulers.parallel())

                .map(result -> result + " success!")
                .doOnNext(n -> System.out.println("# map(): " + n))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> System.out.println("# onNext: " + data));
        Thread.sleep(200L);
    }

}
