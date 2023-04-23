package com.dann41.anki.shared.infrastructure.framework;

import com.dann41.anki.shared.application.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class BusConfiguration {

    @Bean
    public CommandBus commandBus(List<CommandHandler<? extends Command>> handlers) {
        return new DispatcherCommandBus(
                handlers.stream()
                        .collect(
                                Collectors.toMap(
                                        CommandHandler::supports,
                                        item -> item
                                )
                        )
        );
    }

    @Bean
    public QueryBus queryBus(List<QueryHandler<? extends Query<?>, ? extends QueryResponse>> handlers) {
        return new DispatcherQueryBus(
                handlers.stream()
                        .collect(
                                Collectors.toMap(
                                        QueryHandler::supports,
                                        item -> item
                                )
                        )
        );
    }

}
