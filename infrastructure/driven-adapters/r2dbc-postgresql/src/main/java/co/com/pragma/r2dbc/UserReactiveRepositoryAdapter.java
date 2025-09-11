package co.com.pragma.r2dbc;

import co.com.pragma.model.rol.Rol;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.r2dbc.entity.UserEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User/* change for domain model */,
        UserEntity/* change for adapter model */,
        BigInteger,
        UserReactiveRepository
> implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserReactiveRepositoryAdapter.class);

    //para manejo de las transacciones dentro del flujo reactivo
    private final TransactionalOperator transactionalOperator;

    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {

        super(repository, mapper, d -> mapper.map(d, User.class/* change for domain model */));
        this.transactionalOperator =  transactionalOperator;
    }

    @Override
    public Mono<User> save(User user) {
        UserEntity entity = mapper.map(user, UserEntity.class);
        // manualmente mapear rolId
        if (user.getRol() != null) {
            entity.setRolId(user.getRol().getUniqueId());
        }
        return super.saveData(entity)
                .map(savedEntity -> mapper.map(savedEntity, User.class));
    }

    @Override
    public Mono<User> findByEmail(String email) {
        UserEntity user = new UserEntity();
        user.setEmail(email);

        return findByExampleEntity(user)
                .next()
                .map(entity -> {
                    User mapped = mapper.map(entity, User.class);
                    if (entity.getRolId() != null) {
                        mapped.setRol(Rol.builder().uniqueId(entity.getRolId()).build());
                    }
                    return mapped;
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("No se encontró usuario con email={}", email);
                    return Mono.empty();
                }))
                .doOnError(e -> log.error("Error al buscar usuario por email={}: {}", email, e.getMessage(), e));
    }

    @Override
    public Flux<User> getAllUsers() {
        return super.findAll()
                .doOnNext(String -> log.info("ADAPTER: Ingrese a listar Usuarios"));
    }

    @Override
    public Mono<User> findById(String id) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return null;
    }

    @Override
    public Mono<User> getUsuarioByEmailAndDocument(String email, String document) {
        log.info("Buscando usuario por email={} y documento={}", email, document);

        User usuario = new User();
        usuario.setEmail(email);
        usuario.setDocumentoIdentidad(document);

        return findByExample(usuario)
                .doOnNext(u -> log.debug("Coincidencia encontrada: {}", u))
                .next()
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("No se encontró usuario con email={} y documento={}", email, document);
                    return Mono.empty();
                }))
                .doOnError(e -> log.error("Error al buscar usuario por email={} y documento={}: {}", email, document, e.getMessage(), e));

    }
}
