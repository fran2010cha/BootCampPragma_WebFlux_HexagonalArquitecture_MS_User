package co.com.pragma.usecase.user.validationhandling.validations;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class SalarioBaseValidation implements  IUserValidation {
    @Override
    public Mono<Void> validate(User user) {
        if(user.getSalarioBase().compareTo(BigDecimal.ZERO) < 0 || user.getSalarioBase().compareTo(new BigDecimal(15000000.0)) > 0){
            return Mono.error(new IllegalArgumentException("Salario fuera de rango (0 - 15000000)"));
        }
        return Mono.empty();
    }
}
