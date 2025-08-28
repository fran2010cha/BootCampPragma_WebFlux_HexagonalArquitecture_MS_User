package co.com.pragma.api;

import co.com.pragma.api.config.UserPath;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {


    private final UserPath userPath;
    private final Handler userHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET(userPath.getUsers()), userHandler::listenGetAllUsers)
                .andRoute(POST(userPath.getUsers()), userHandler::listenSavedUser)
                .and(route(GET(userPath.getUsersById()), userHandler::listenGETOtherUseCase));
    }
}
