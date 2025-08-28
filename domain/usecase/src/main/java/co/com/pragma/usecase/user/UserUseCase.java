package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.usecase.user.validationhandling.UserValidation;
import co.com.pragma.usecase.user.validationhandling.validations.ApellidoValidation;
import co.com.pragma.usecase.user.validationhandling.validations.EmailValidation;
import co.com.pragma.usecase.user.validationhandling.validations.NombreValidation;
import co.com.pragma.usecase.user.validationhandling.validations.SalarioBaseValidation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;
import java.util.Objects;

@RequiredArgsConstructor
public class UserUseCase implements IUserUseCase {

    private final UserRepository userRepository;


    @Override
    public Mono<User> saveUser(User user) {

        UserValidation validation = new UserValidation()
                .addValidation(new NombreValidation())
                .addValidation(new ApellidoValidation())
                .addValidation(new EmailValidation(userRepository))
                .addValidation(new SalarioBaseValidation());

        return validation.validate(user)
                .then(userRepository.save(user)).log();
    }
    @Override
    public Flux<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

}
