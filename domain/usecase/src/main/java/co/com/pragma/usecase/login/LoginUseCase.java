package co.com.pragma.usecase.login;

import co.com.pragma.model.user.gateways.PasswordEncoder;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUseCase implements ILogin {

    private final UserRepository usuarioRepository;
    private final IJwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<String> login(String email, String password) {
        return usuarioRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Usuario no encontrado")))
                .flatMap(user -> {
                    if (passwordEncoder.matches(password, user.getPasswordHash())) {
                        return Mono.just(jwtTokenProvider.createToken(user.getEmail(),user.getRol().getUniqueId()));
                    }
                    return Mono.error(new IllegalArgumentException("Credenciales inválidas"));
                }).flatMap(token -> token);
    }
}
