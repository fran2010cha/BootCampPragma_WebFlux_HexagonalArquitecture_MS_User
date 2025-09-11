package co.com.pragma.usecase.validationclient;

import reactor.core.publisher.Mono;

public interface IValidateJwt {
    Mono<String> validate(String jwt);
}
