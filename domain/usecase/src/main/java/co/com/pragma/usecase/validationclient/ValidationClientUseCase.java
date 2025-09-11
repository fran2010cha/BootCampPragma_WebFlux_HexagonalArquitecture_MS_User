package co.com.pragma.usecase.validationclient;

import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ValidationClientUseCase  implements IValidationClientUseCase{

    private final UserRepository usuarioRepository;
    private final IValidateJwt validateJwt;

    @Override
    public Mono<Boolean> isUserValid(String jwt, String document, String email) {
        return validateJwt.validate(jwt)
                .flatMap(rol -> {
                    if (rol == null || rol.isEmpty()) {
                        // token inválido → error
                        return Mono.error(new IllegalArgumentException("JWT no es válido o no tiene rol"));
                    }

                    // opcional: si solo quieres permitir ADMIN:
                    if (!"ADMIN".equalsIgnoreCase(rol)) {
                        return Mono.error(new IllegalAccessException("Usuario no tiene rol ADMIN"));
                    }
                        return usuarioRepository.getUsuarioByEmailAndDocument(email, document)
                                .flatMap(usuario ->
                                        {
                                            return Mono.just(usuario.getDocumentoIdentidad().equalsIgnoreCase(document));
                                        }
                                ).defaultIfEmpty(false);

                });
    }
}
