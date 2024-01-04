package org.saltations.hephaestus;

import io.micronaut.configuration.picocli.PicocliRunner;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "heph", description = "Template Code Generator",
        mixinStandardHelpOptions = true,
        header = {
            """
              @|green   _    _ ______ _____  _    _   |@
              @|green  | |  | |  ____|  __ \\| |  | |  |@
              @|green  | |__| | |__  | |__) | |__| |  |@
              @|green  |  __  |  __| |  ___/|  __  |  |@
              @|green  | |  | | |____| |    | |  | |  |@
              @|green  |_|  |_|______|_|    |_|  |_|  |@
             """},
        subcommands = {HEPHTemplateCommand.class, HEPHGenerateCommand.class}
)

public class HEPHApp implements Runnable {

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(HEPHApp.class, args);
    }

    public void run() {
    }
}
