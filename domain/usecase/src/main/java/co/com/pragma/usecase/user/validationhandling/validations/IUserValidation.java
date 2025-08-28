package co.com.pragma.usecase.user.validationhandling.validations;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Mono;

public interface IUserValidation {
    Mono<Void> validate(User user);
}
