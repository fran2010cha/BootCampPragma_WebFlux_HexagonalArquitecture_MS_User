package co.com.pragma.usecase.user.validationhandling.validations;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Mono;

public class ApellidoValidation implements IUserValidation {

    @Override
    public Mono<Void> validate(User user) {
        if (user.getApellido() == null || user.getApellido().isEmpty()) {
            return Mono.error(new IllegalArgumentException("El apellido es obligatorio"));
        }
        return Mono.empty();
    }
}
