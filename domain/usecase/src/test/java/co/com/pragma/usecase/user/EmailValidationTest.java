package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.usecase.user.validationhandling.validations.EmailValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmailValidationTest {


    private UserRepository usuarioRepository;
    private EmailValidation emailValidation;

    @BeforeEach
    void setUp() {
        usuarioRepository = Mockito.mock(UserRepository.class);
        emailValidation = new EmailValidation(usuarioRepository);
    }

    @Test
    void validate_emailNull_error() {
        User usuario = User.builder().email(null).build();

        StepVerifier.create(emailValidation.validate(usuario))
                .expectErrorMatches(e -> e instanceof IllegalArgumentException &&
                        e.getMessage().contains("El correo electrónico es obligatorio"))
                .verify();
    }

    @Test
    void validate_emailVacio_error() {
        User usuario = User.builder().email("").build();

        StepVerifier.create(emailValidation.validate(usuario))
                .expectErrorMatches(e -> e instanceof IllegalArgumentException &&
                        e.getMessage().contains("El correo electrónico es obligatorio"))
                .verify();
    }

    @Test
    void validate_emailFormatoInvalido_error() {
        User usuario = User.builder().email("correo_invalido").build();

        StepVerifier.create(emailValidation.validate(usuario))
                .expectErrorMatches(e -> e instanceof IllegalArgumentException &&
                        e.getMessage().contains("Correo electrónico inválido"))
                .verify();
    }

    @Test
    void validate_emailNoExiste_completaOK() {
        User usuario = User.builder().email("test@example.com").build();

        when(usuarioRepository.findByEmail(anyString()))
                .thenReturn(Mono.empty());

        StepVerifier.create(emailValidation.validate(usuario))
                .verifyComplete();

        verify(usuarioRepository).findByEmail("test@example.com");
    }

    @Test
    void validate_emailYaExiste_error() {
        User usuario = User.builder().email("test@example.com").build();

        when(usuarioRepository.findByEmail("test@example.com"))
                .thenReturn(Mono.just(usuario));

        StepVerifier.create(emailValidation.validate(usuario))
                .expectErrorMatches(e -> e instanceof IllegalArgumentException &&
                        e.getMessage().contains("El correo ya está registrado"))
                .verify();

        verify(usuarioRepository).findByEmail("test@example.com");
    }
}
