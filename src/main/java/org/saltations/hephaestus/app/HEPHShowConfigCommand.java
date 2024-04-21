package org.saltations.hephaestus.app;

import io.micronaut.context.annotation.Property;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.nio.file.Path;

/**
 * Initializes the user's configuration folder for HEPH.
 */

@Slf4j
@CommandLine.Command(name = "show-config", description = "Show top level HEPH configuration",
        mixinStandardHelpOptions = true)
public class HEPHShowConfigCommand implements Runnable
{
    @Inject
    @Property(name = "heph.config-folder")
    private Path userConfigFolder;

    public void run()
    {
        log.info("HEPH configuration\n: User Configuration Folder: [{}]", userConfigFolder);
    }
}
