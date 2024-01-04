package org.saltations.hephaestus;

import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

/**
 * Initializes the root folder for storing a user's template files
 */

@Slf4j
@CommandLine.Command(name = "init", description = "Initialize user's root folder for HEPH templates",
        mixinStandardHelpOptions = true)
public class HEPHTemplateInitCommand implements Runnable
{
    private final HEPHPrefs prefs;

    @Inject
    public HEPHTemplateInitCommand(HEPHPrefs prefs)
    {
        this.prefs = prefs;
    }

    public void run() {

        if (!prefs.doesUsersCabinetExist())
        {
            prefs.initializeUsersCabinet();
        }
    }
}
