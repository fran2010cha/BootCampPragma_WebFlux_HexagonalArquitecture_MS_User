package co.com.pragma.usecase.login;

import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface IJwtTokenProvider {
    Mono<String> createToken(String email ,BigInteger id);
}
