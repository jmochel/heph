package org.saltations.hephaestus;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static freemarker.template.Configuration.SQUARE_BRACKET_INTERPOLATION_SYNTAX;


/**
 * Top Level command for all template management sub-commands
 */
@Slf4j
@CommandLine.Command(name = "generate", aliases = {"gen"}, description = "Generate content from a shelf or template",
        mixinStandardHelpOptions = true
)
public class HEPHGenerateCommand implements Runnable
{
    public static final String FREEMARKER_TEMPLATE_EXTENSION = ".ftl";
    public static final char STRINGTEMPLATE_PROPERTY_START = '$';

    public static final char STRINGTEMPLATE_PROPERTY_END = '$';
    @Named("cabinet")
    private final Path cabinetFolder;

    @CommandLine.Option(names = {"-r", "--real"}, description = "If true, this actually generates the contents of the files and folders.")
    boolean realRun = false;

    @CommandLine.Option(names = {"--source", "-src"}, arity = "1", description = "Path to source folder")
    private Path sourceRootFolder;

    @CommandLine.Option(names = {"--shelf", "-s"}, arity = "1", description = "Name of the shelf to be generated from")
    private String shelfName;

    @CommandLine.Option(names = {"--target", "-tgt"}, arity = "1", description = "Path to the target folder")
    private Path targetRootFolder;

    @CommandLine.Option(names = {"--config", "-c"}, arity = "1", description = "Path to the TOML config file")
    private Path dataModelConfigPath;

    @Inject
    public HEPHGenerateCommand(@Named("cabinet") Path cabinetFolder)
    {
        this.cabinetFolder = cabinetFolder;
    }

    @Override
    public void run()
    {

        // Load the data model for use later

        try {
            TomlParseResult result = Toml.parse(dataModelConfigPath);
            var dataModel = result.toMap();

            // Log config
            dataModel.entrySet().forEach(entry -> log.info("\tCONFIG:\t{}=>{}", entry.getKey(), entry.getValue()));

            // Walk the source tree doing the following

            var shelfFolder = sourceRootFolder.resolve(shelfName);
            var fileSystemActions = realRun ? new LiveFileSysBridge(dataModel, shelfFolder) :  new FileSysLogOnlyBridge();

            Set<String> filesToExclude = Set.of(dataModelConfigPath.getFileName().toString());

            var mapper = new SourceAndTargetMapper(dataModel, sourceRootFolder, targetRootFolder);

            Files.walkFileTree(sourceRootFolder, new CopyAndTransform(mapper, filesToExclude, fileSystemActions));

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @RequiredArgsConstructor
    static class CopyAndTransform implements FileVisitor<Path>
    {
        private final SourceAndTargetMapper sourceAndTargetMapper;
        private final Set<String> filesToExclude;
        private final FileSysBridge actions;

        @Override
        public FileVisitResult preVisitDirectory(Path sourceFolder, BasicFileAttributes attrs) throws IOException
        {
            var targetFolder = sourceAndTargetMapper.createTargetFolderName(sourceFolder);

            actions.makeFolder(targetFolder);

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path sourceFile, BasicFileAttributes attrs) throws IOException
        {
            // Skip any files that shouldn't be copied

            var fileName = sourceFile.getFileName().toString();

            if (filesToExclude.contains(fileName))
            {
                actions.skipFile(sourceFile);
                return FileVisitResult.CONTINUE;
            }

            // Create a new relative target name (expanding ony properties in the path as necessary)

            var targetFile = sourceAndTargetMapper.createTargetFileName(sourceFile);

            // For each source file

            var isTemplate = sourceFile.toString().endsWith(FREEMARKER_TEMPLATE_EXTENSION);

            if (isTemplate)
            {
                actions.createFile(sourceFile, targetFile);
            }
            else {
                actions.copyFile(sourceFile, targetFile);
            }

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
        {
            return  FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
        {
            return  FileVisitResult.CONTINUE;
        }
    }

}
