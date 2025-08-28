package co.com.pragma.usecase.user.validationhandling.validations;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Mono;

public class NombreValidation implements  IUserValidation {
    @Override
    public Mono<Void> validate(User user) {
        if (user.getNombre() == null || user.getNombre().isEmpty()) {
            return Mono.error(new IllegalArgumentException("El nombre es obligatorio"));
        }
        return Mono.empty();
    }
}

