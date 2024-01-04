package org.saltations.hephaestus;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;

import static freemarker.template.Configuration.SQUARE_BRACKET_INTERPOLATION_SYNTAX;


@Slf4j
class LiveFileSysBridge extends FileSysLogOnlyBridge
{
    private final Configuration freemarkerCfg = new Configuration(Configuration.VERSION_2_3_27);;
    private final Configuration freemarkerPOMCfg = new Configuration(Configuration.VERSION_2_3_27);;

    private final Map<String,Object> dataModel;
    private final Path shelfFolder;

    public LiveFileSysBridge(Map<String,Object> dataModel, Path shelfFolder)
    {
        this.dataModel = dataModel;
        this.shelfFolder = shelfFolder;

        // Freemarker config for non-pom scripting

        freemarkerCfg.setDefaultEncoding("UTF-8");
        freemarkerCfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        freemarkerCfg.setLocale(Locale.US);

        // Freemarker config for POM scripting Where we want to preserve any existing '${property-name}' entries

        freemarkerPOMCfg.setDefaultEncoding("UTF-8");
        freemarkerPOMCfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        freemarkerPOMCfg.setLocale(Locale.US);
        freemarkerPOMCfg.setInterpolationSyntax(SQUARE_BRACKET_INTERPOLATION_SYNTAX);

        try {

            freemarkerCfg.setDirectoryForTemplateLoading(shelfFolder.toFile());
            freemarkerPOMCfg.setDirectoryForTemplateLoading(shelfFolder.toFile());

            log.info("Loading templates from cabinet [{}] in folder [{}]", shelfFolder.getFileName(), shelfFolder);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void makeFolder(Path targetFolderPath)
    {
        super.makeFolder(targetFolderPath);

        targetFolderPath.toFile().mkdirs();
    }

    @Override
    public void createFile(Path sourceFile, Path targetFile)
    {
        super.createFile(sourceFile, targetFile);

        var templatePath = shelfFolder.relativize(sourceFile);

        try {
            var template = sourceFile.toString()
                                     .contains("pom.xml") ? freemarkerPOMCfg.getTemplate(templatePath.toString()) : freemarkerCfg.getTemplate(templatePath.toString());

            var writer  = new FileWriter(targetFile.toFile());

            template.process(dataModel, writer);
        }
        catch (TemplateNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
        catch (MalformedTemplateNameException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (TemplateException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void copyFile(Path sourceFile, Path targetFile)
    {
        super.copyFile(sourceFile, targetFile);

        try {
            Files.copy(sourceFile, targetFile);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void skipFile(Path sourceFile)
    {
        super.skipFile(sourceFile);
    }
}
