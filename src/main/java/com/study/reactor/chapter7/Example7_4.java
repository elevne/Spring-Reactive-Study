package com.study.reactor.chapter7;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

// Hot Sequence Http
public class Example7_4 {

    public static void main(String[] args) throws InterruptedException {
        URI worldTimeUri = UriComponentsBuilder.newInstance().scheme("http")
                .host("worldtimeapi.org")
                .port(80)
                .path("/api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri();

        Mono<String> mono = getWorldTime(worldTimeUri).cache();
        mono.subscribe(dateTime -> System.out.println("# " + Thread.currentThread() + " dateTime1: " + dateTime));
        Thread.sleep(2000);
        mono.subscribe(dateTime -> System.out.println("# " + Thread.currentThread() + " dateTime2: " + dateTime));
        Thread.sleep(2000);
    }

    private static Mono<String> getWorldTime(URI worldTimeUri) {
        return WebClient.create()
                .get()
                .uri(worldTimeUri)
                .retrieve()
                .bodyToMono(String.class)
                .map(resp -> {
                    DocumentContext jsonContext = JsonPath.parse(resp);
                    String dateTime = jsonContext.read("$.datetime");
                    return dateTime;
                });
    }

}
