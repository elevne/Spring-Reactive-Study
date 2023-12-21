package com.study.reactor.chapter7;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class Example7_2 {

    public static void main(String[] args) throws InterruptedException {
        String[] singers = {"Singer A", "Singer B", "Singer C", "Singer D", "Singer E"};

        System.out.println("# Begin Concert");

        Flux<String> concertFlux =
                Flux
                        .fromArray(singers)
                        .delayElements(Duration.ofSeconds(1))
                        .share();

        concertFlux.subscribe(
                singer -> System.out.println("# Subscriber1 is watching singer: " + singer)
        );

        Thread.sleep(2500);

        concertFlux.subscribe(
                singer -> System.out.println("# Subsriber2 is watching singer: " + singer)
        );

        Thread.sleep(3000);

    }

}
