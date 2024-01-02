package com.study.reactor.chapter11;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Example11_1 {

    public static void main(String[] args) throws InterruptedException {
        Mono
                .deferContextual(ctx ->
                    Mono
                            .just("Hello "+ ctx.get("firstName"))
                            .doOnNext(data -> System.out.println("# just doOnNext: " + data))
                )
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.parallel())
                .transformDeferredContextual(
                        (mono, ctx) -> mono.map(data -> data + " " + ctx.get("lastName"))
                )
                .contextWrite(context -> context.put("lastName", "Jobs"))
                .contextWrite(context -> context.put("firstName", "Steve"))
                .subscribe(data -> System.out.println("# onNext: " + data));
        Thread.sleep(100L);
    }

}
