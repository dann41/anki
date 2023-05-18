package com.dann41.anki.cmd.infrastructure.configuration;

import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.cmd.infrastructure.services.FileCollectionImporter;
import com.dann41.anki.cmd.infrastructure.services.Starter;
import com.dann41.anki.shared.application.CommandBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class CmdConfiguration {

    public CmdConfiguration() {
        System.out.println("CMD");
    }
    @Lazy
    @Bean
    Starter starter(Navigator navigator) {
        return new Starter(navigator);
    }

    @Bean
    FileCollectionImporter fileCollectionImporter(CommandBus commandBus) {
        return new FileCollectionImporter(commandBus);
    }
}
