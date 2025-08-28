package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegistryUserUseCaseTest {


    private UserRepository usuarioRepository;
    private UserUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UserRepository.class);
        useCase = new UserUseCase(usuarioRepository);
    }

    @Test
    void registryUser_conUsuarioValido_guardaUsuario() {
        // Arrange
        User usuario = User.builder()
                .id(BigInteger.TEN)
                .nombre("Francisco")
                .apellido("Aristi")
                .fechaNacimiento("12-02-1994")
                .direccion("cra 25 41 45")
                .telefono("5565655")
                .email("fran@example.com")
                .salarioBase(new BigDecimal(14000000))
                .documentoIdentidad("25561111")
                .rolId(3L)
                .build();


        when(usuarioRepository.findByEmail(usuario.getEmail()))
                .thenReturn(Mono.empty());
        when(usuarioRepository.save(any()))
                .thenAnswer(invocation -> {
                    User u = invocation.getArgument(0);
                    return Mono.just(u);
                });

        // se prueba el flujo recativo
        StepVerifier.create(useCase.saveUser(usuario))
                .expectNextMatches(u -> u.getNombre().equals("Francisco"))
                .verifyComplete();

        // Verificar que el usuario se guardó
        //capturamos los argumentos que le pasamos al Mock durante la ejecucion de pruebas
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(usuarioRepository).save(captor.capture());
        User usuarioGuardado = captor.getValue();
        assertEquals("Francisco", usuarioGuardado.getNombre());
    }

}
