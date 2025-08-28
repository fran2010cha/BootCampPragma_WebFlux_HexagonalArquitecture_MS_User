package co.com.pragma.usecase.user.validationhandling;

import co.com.pragma.model.user.User;
import co.com.pragma.usecase.user.validationhandling.validations.IUserValidation;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class UserValidation implements IUserValidation {
    private final List<IUserValidation> validations = new ArrayList<>();

    public UserValidation addValidation(IUserValidation validation) {
        validations.add(validation);
        return this;
    }

    @Override
    public Mono<Void> validate(User user) {
        Mono<Void> result = Mono.empty();
        for (IUserValidation validation : validations) {
            result = result.then(validation.validate(user));
        }
        return result;
    }
}

