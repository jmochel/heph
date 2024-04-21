package org.saltations.hephaestus;

import org.saltations.hephaestus.app.HEPHInitCommand;
import picocli.CommandLine;

/**
 * Top Level command for all template management sub-commands
 */
@CommandLine.Command(name = "cabinet", aliases = {"cab"}, description = "Manage HEPH Cabinets (Groupings of Templates (aka Shelves)",
        mixinStandardHelpOptions = true,
        subcommands = {
            HEPHInitCommand.class,
            HEPHTemplateAddShelfCommand.class,
            HEPHTemplateListShelvesCommand.class
        }
)
public class HEPHCabinetCommand
{

}
