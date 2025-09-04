package co.com.pragma.usecase.validationclient;

import reactor.core.publisher.Mono;

public interface IValidateJwt {
    Mono<Boolean> validate(String jwt);
}
