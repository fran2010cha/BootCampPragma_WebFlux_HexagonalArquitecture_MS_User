package co.com.pragma.r2dbc;

import co.com.pragma.r2dbc.entity.RolEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.math.BigInteger;

public interface RolReactiveRepository extends ReactiveCrudRepository<RolEntity, BigInteger>, ReactiveQueryByExampleExecutor<RolEntity> {
}
