package com.study.reactor.chapter13;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class StepVerifierGeneralExample01Test {

    @Test
    public void sayHelloReactorTest() {
        StepVerifier
                .create(Mono.just("Hello Reactor"))
                .expectNext("Hello Reactor")
                .expectComplete()
                .verify();
    }

}
