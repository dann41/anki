package com.dann41.anki.cmd.intrastructure;

import com.dann41.anki.cmd.intrastructure.configuration.CmdConfiguration;
import com.dann41.anki.cmd.intrastructure.services.Starter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackageClasses = {CmdConfiguration.class}
)
public class CmdApplication {

  public static void main(String[] args) {
    var context = SpringApplication.run(CmdApplication.class, args);
    context.getBean(Starter.class).start();
  }
}
