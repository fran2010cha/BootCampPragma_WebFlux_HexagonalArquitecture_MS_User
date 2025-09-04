package co.com.pragma.api;

import co.com.pragma.api.dto.login.LoginRequestDTO;
import co.com.pragma.api.dto.login.TokenDTO;
import co.com.pragma.api.dto.registry.UserRequestDTO;
import co.com.pragma.api.dto.registry.UserValidationRequest;
import co.com.pragma.api.mapper.registry.UserDTOMapper;
import co.com.pragma.model.user.User;
import co.com.pragma.usecase.login.ILogin;
import co.com.pragma.usecase.user.IUserUseCase;
import co.com.pragma.usecase.validationclient.IValidationClientUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {
    private final IUserUseCase userUseCase;
    private final UserDTOMapper userDTOMapper;
    private final IValidationClientUseCase validationClientUseCase;
    private final ILogin loginUseCase;

    public Mono<ServerResponse> listenGetAllUsers(ServerRequest serverRequest) {

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userUseCase.getAllUsers(), User.class);
    }

    public Mono<ServerResponse> listenSavedUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserRequestDTO.class)
                .flatMap(dto ->
                        userUseCase.saveUser(userDTOMapper.mapToEntity(dto))
                                .then(ServerResponse.ok().bodyValue("Usuario guardado correctamente"))
                )
                .onErrorResume( error -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(error.getMessage()))
                .onErrorResume(e -> ServerResponse.status(500).bodyValue("Error interno: " + e.getMessage()));
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        log.info("Iniciando ILogin de usuario");

        return request.bodyToMono(LoginRequestDTO.class)
                .doOnNext(dto -> log.debug("Payload ILogin recibido: {}", dto))
                .flatMap(dto -> loginUseCase.login(dto.email(), dto.password())
                        .map(TokenDTO::new)
                        .flatMap(resp -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(resp))
                )
                .onErrorResume(IllegalArgumentException.class, e -> {
                    log.warn("Error de credenciales: {}", e.getMessage());
                    return ServerResponse.status(401)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(Map.of("error", e.getMessage()));
                })
                .onErrorResume(e -> {
                    log.error("Error interno en ILogin", e);
                    return ServerResponse.status(500)
                            .bodyValue(Map.of("error", "Error interno: " + e.getMessage()));
                });
    }

    public Mono<ServerResponse> validateUser(ServerRequest request) {
        log.info("Iniciando validación de usuario");

        return request.bodyToMono(UserValidationRequest.class)
                .doOnNext(req -> log.debug("Payload validación recibido: {}", req))
                .flatMap(userRequest -> validationClientUseCase.isUserValid(
                                        request.headers().firstHeader("Authorization"),
                                        userRequest.getId(),
                                        userRequest.getEmail()
                                )
                                .doOnNext(isValid -> log.info("Resultado validación usuario [{}]: {}", userRequest.getEmail(), isValid))
                                .flatMap(isValid -> {
                                    if (Boolean.TRUE.equals(isValid)) {
                                        return ServerResponse.ok()
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .bodyValue(true);
                                    } else {
                                        return ServerResponse.status(401)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .bodyValue(false);
                                    }
                                })
                )
                .onErrorResume(e -> {
                    log.error("Error en validación de usuario", e);
                    return ServerResponse.status(500).bodyValue("Error interno: " + e.getMessage());
                });
    }

}
