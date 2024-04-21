package org.saltations.hephaestus.app;

import io.micronaut.configuration.picocli.PicocliRunner;

import picocli.CommandLine.Command;

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
        subcommands = {HEPHInitCommand.class, HEPHShowConfigCommand.class}
)

public class HEPHCommand implements Runnable {

    public static void main(String... args) throws Exception {
        PicocliRunner.run(HEPHCommand.class, args);
    }

    public void run() {
    }
}
