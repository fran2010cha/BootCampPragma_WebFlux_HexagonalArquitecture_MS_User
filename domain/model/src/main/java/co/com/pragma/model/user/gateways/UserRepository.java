package co.com.pragma.model.user.gateways;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> save(User user);
    Mono<User> findByEmail(String email);
    Flux<User> getAllUsers();
    Mono<User> findById(String id);
    Mono<Void> deleteById(String id);
    Mono<User> getUsuarioByEmailAndDocument(String email, String document);
}
