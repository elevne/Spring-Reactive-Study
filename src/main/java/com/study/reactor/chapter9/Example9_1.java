package com.study.reactor.chapter9;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

public class Example9_1 {

    public static void main(String[] args) throws InterruptedException {
        int tasks = 6;
        Flux
                .create((FluxSink<String> sink) -> {
                    IntStream
                            .range(1, tasks)
                            .forEach(n -> sink.next(doTask(n)));
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(n -> System.out.println("# create(): " + n))
                .publishOn(Schedulers.parallel())
                .map(result -> result + " success!")
                .doOnNext(n -> System.out.println("# map(): " + n))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> System.out.println("# onNext: " + data));
        Thread.sleep(500L);
    }

    static String doTask(int taskNumber) {
        return "task " + taskNumber + " result";
    }

}
