package com.dann41.anki.cmd.infrastructure.configuration;

import com.dann41.anki.cmd.infrastructure.presentation.PresentationModule;
import com.dann41.anki.cmd.infrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.cmd.infrastructure.services.FileCollectionImporter;
import com.dann41.anki.cmd.infrastructure.services.Starter;
import com.dann41.anki.core.shared.infrastructure.framework.CoreApiModule;
import com.dann41.anki.shared.application.CommandBus;
import com.dann41.anki.shared.infrastructure.framework.SharedModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@ComponentScan(
    basePackageClasses = {
            CoreApiModule.class,
            SharedModule.class,
            PresentationModule.class
    }
)
public class CmdConfiguration {

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
