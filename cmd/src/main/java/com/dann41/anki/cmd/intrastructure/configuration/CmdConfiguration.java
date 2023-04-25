package com.dann41.anki.cmd.intrastructure.configuration;

import com.dann41.anki.cmd.intrastructure.presentation.cmd.core.Navigator;
import com.dann41.anki.cmd.intrastructure.services.FileCollectionImporter;
import com.dann41.anki.cmd.intrastructure.services.Starter;
import com.dann41.anki.core.shared.infrastructure.framework.CoreApiModule;
import com.dann41.anki.shared.application.CommandBus;
import com.dann41.anki.shared.infrastructure.framework.SharedModule;
import org.springframework.context.annotation.*;

@Configuration
@Import({SharedModule.class, CoreApiModule.class})
@ComponentScan(basePackages = "com.dann41.anki.cmd.intrastructure.presentation")
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
