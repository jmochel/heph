package org.saltations.hephaestus;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.nio.file.Path;

/**
 * Initializes the root folder for storing a user's template files
 */

@Slf4j
@CommandLine.Command(name = "add-shelf", description = "Add a shelf (a grouping of templates) to the user's cabinet",
        mixinStandardHelpOptions = true)
public class HEPHTemplateAddShelfCommand implements Runnable
{
    @Named("cabinet")
    private final Path cabinetFolder;

    @CommandLine.Parameters(arity = "1", description = "Name of the shelf to be created")
    private String shelfName;

    @Inject
    public HEPHTemplateAddShelfCommand(@Named("cabinet") Path cabinetFolder)
    {
        this.cabinetFolder = cabinetFolder;
    }

    public void run() {

        var newFolder = this.cabinetFolder.resolve(shelfName);

        if (newFolder.toFile().exists())
        {
            log.warn("Shelf [{}] already exists. We are leaving it alone", newFolder.toAbsolutePath());
            return;
        }

        newFolder.toFile().mkdirs();
        System.out.println("Created cabinet " + newFolder.toAbsolutePath());
    }
}
