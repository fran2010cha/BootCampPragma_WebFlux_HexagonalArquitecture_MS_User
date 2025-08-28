package co.com.pragma.usecase.user.validationhandling.validations;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

public class EmailValidation implements IUserValidation {

    private final  UserRepository userRepository;

    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public EmailValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Void> validate(User user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return Mono.error(new IllegalArgumentException("El correo electrónico es obligatorio"));
        }
        if (!EMAIL_REGEX.matcher(user.getEmail()).matches()) {
            return Mono.error(new IllegalArgumentException("Correo electrónico inválido"));
        }

        return userRepository.findByEmail(user.getEmail())
                .flatMap(existing -> Mono.<User>error(
                        new IllegalArgumentException("El correo ya está registrado")))
                .then();
    }
}
