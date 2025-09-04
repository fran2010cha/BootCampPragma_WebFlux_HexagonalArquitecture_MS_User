package co.com.pragma.usecase.login;

import reactor.core.publisher.Mono;

public interface IJwtTokenProvider {
    Mono<String> createToken(String email);
}
