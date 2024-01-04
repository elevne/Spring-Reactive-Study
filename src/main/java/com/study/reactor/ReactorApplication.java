package com.study.reactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactorApplication {

	@Bean
	RouterFunction<ServerResponse> routes() {
		return RouterFunctions.route(RequestPredicates.GET("/2"), req -> ServerResponse
				.ok().body(Flux.just("Hello", "World!"), String.class));
	}

	public static void main(String[] args) {
		SpringApplication.run(ReactorApplication.class, args);
	}

}
