package com.dann41.anki.cmd.infrastructure;

import com.dann41.anki.cmd.infrastructure.configuration.CmdConfiguration;
import com.dann41.anki.cmd.infrastructure.presentation.PresentationModule;
import com.dann41.anki.cmd.infrastructure.services.Starter;
import com.dann41.anki.core.shared.infrastructure.framework.CoreApiModule;
import com.dann41.anki.shared.infrastructure.framework.SharedModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackageClasses = {
            CoreApiModule.class,
            SharedModule.class,
            PresentationModule.class,
            CmdConfiguration.class
    }
)
public class CmdApplication {

  public static void main(String[] args) {
    var context = SpringApplication.run(CmdApplication.class, args);
    context.getBean(Starter.class).start();
  }
}
