package co.com.pragma.r2dbc;

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
        return super.save(user)
                .doOnNext(u -> log.info("ADAPTER: Usuario guardado en la BD: {}", u))
                .as(transactionalOperator::transactional);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        return findByExample(user)
                .next()
                .doOnNext(u -> log.info("ADAPTER: Se valida si existe email registrado en la BD: {}", u))
                .switchIfEmpty(Mono.empty());
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
}
