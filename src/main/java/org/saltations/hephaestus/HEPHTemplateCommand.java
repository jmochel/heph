package org.saltations.hephaestus;

import picocli.CommandLine;

/**
 * Top Level command for all template management sub-commands
 */
@CommandLine.Command(name = "template", aliases = {"tem"}, description = "Manage HEPH Templates and Shelves (Groupings of Templates)",
        mixinStandardHelpOptions = true,
        subcommands = {HEPHTemplateInitCommand.class, HEPHTemplateAddShelfCommand.class, HEPHTemplateListShelvesCommand.class}
)
public class HEPHTemplateCommand
{

}
