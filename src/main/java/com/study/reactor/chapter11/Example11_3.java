package com.study.reactor.chapter11;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

public class Example11_3 {

    public static void main(String[] args) throws InterruptedException {
        final String key1 = "company";
        final String key2 = "firstName";
        final String key3 = "lastName";

        Mono
                .deferContextual(ctx ->
                        Mono.just(ctx.get(key1) + ", " + ctx.getOrEmpty(key2).orElse("no firstName") + " " + ctx.getOrDefault(key3, "no lastName")))
                .publishOn(Schedulers.parallel())
                .contextWrite(context -> context.putAll(Context.of(key2, "Steve", key3, "Jobs").readOnly()))
                .contextWrite(context -> context.put(key1, "Apple"))
                .subscribe(data -> System.out.println("# onNext: " + data));

        Thread.sleep(100L);
    }

}
