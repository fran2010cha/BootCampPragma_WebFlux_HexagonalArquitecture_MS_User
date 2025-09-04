package co.com.pragma.usecase.validationclient;

import reactor.core.publisher.Mono;

public interface IValidationClientUseCase {
    Mono<Boolean> isUserValid(String jwt, String document, String email);
}
