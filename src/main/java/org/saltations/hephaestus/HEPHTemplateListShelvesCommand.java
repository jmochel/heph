package org.saltations.hephaestus;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.nio.file.Path;

/**
 * Initializes the root folder for storing a user's template files
 */

@Slf4j
@CommandLine.Command(name = "ls-shelf", description = "List the shelves (a grouping of templates) in the user's cabinet",
        mixinStandardHelpOptions = true)
public class HEPHTemplateListShelvesCommand implements Runnable
{
    @Named("cabinet")
    private final Path cabinetFolder;

    @Inject
    public HEPHTemplateListShelvesCommand(@Named("cabinet") Path cabinetFolder)
    {
        this.cabinetFolder = cabinetFolder;
    }

    public void run() {

        var children = Lists.newArrayList(cabinetFolder.toFile().listFiles());

        children.forEach(child -> System.out.println(child.getName()));
    }
}
