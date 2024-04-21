package org.saltations.hephaestus.app;

import io.micronaut.context.annotation.Property;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Set;

/**
 * Initializes the user's configuration folder for HEPH.
 */

@Slf4j
@CommandLine.Command(name = "initialize", aliases = {"init"}, description = "Initialize HEPH configuration folder in the user's root folder",
        mixinStandardHelpOptions = true)
public class HEPHInitCommand implements Runnable
{
    private static final String HEPH_CABINET_SUBFOLDER_NAME = "cabinets";
    private static final String HEPH_MODEL_SUBFOLDER_NAME = "models";
    private static final String HEPH_SNIPPET_SUBFOLDER_NAME = "snippets";

    private static final Set<String> HEPH_SUBFOLDERS = Set.of(HEPH_CABINET_SUBFOLDER_NAME, HEPH_MODEL_SUBFOLDER_NAME, HEPH_SNIPPET_SUBFOLDER_NAME);

    @Property(name = "heph.config-folder")
    private Path userConfigFolder;

    public void run()
    {
        log.info("Checking on HEPH configuration folder in the user's root folder: {}", userConfigFolder);

        if (userConfigFolder.toFile().exists())
        {
            log.info("HEPH configuration folder already exists in the user's root folder: {}", userConfigFolder);
        }
        else
        {
            log.info("Initializing HEPH configuration folder in the user's root folder: {}", userConfigFolder);
            userConfigFolder.toFile().mkdirs();
        }

        HEPH_SUBFOLDERS.stream().map(userConfigFolder::resolve).filter(p -> !p.toFile().exists()).forEach(p -> {
            log.info("Creating HEPH configuration subfolder: {}", p);
            p.toFile().mkdirs();
        });

    }
}
