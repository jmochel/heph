package org.saltations.hephaestus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.stringtemplate.v4.ST;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * TODO Summary(ends with '.',third person[gets the X, not Get X],do not use @link) SourceAndTargetMapper represents xxx OR SourceAndTargetMapper does xxxx.
 *
 * <p>TODO Description(1 lines sentences,) References generic parameters with {@code <T>} and uses 'b','em', dl, ul, ol tags
 */
@Getter
@RequiredArgsConstructor
class SourceAndTargetMapper
{

    private final Map<String, Object> dataModel;
    private final Path sourceFolderRootPath;
    private final Path targetFolderRootPath;

    public Path createTargetFolderName(Path sourceFolder)
    {
        var expandedTargetPath = createTargetPath(sourceFolder);

        return expandedTargetPath;
    }

    public Path createTargetFileName(Path sourceFile)
    {
        var expandedTargetPath = createTargetPath(sourceFile);

        var targetPath = Paths.get(expandedTargetPath.toString()
                                                     .replace(HEPHGenerateCommand.FREEMARKER_TEMPLATE_EXTENSION, ""));

        return targetPath;
    }

    public Path createTargetPath(Path sourcePath)
    {
        var sourceTemplate = new ST(sourcePath.toString(), HEPHGenerateCommand.STRINGTEMPLATE_PROPERTY_START, HEPHGenerateCommand.STRINGTEMPLATE_PROPERTY_END);
        dataModel.entrySet()
                 .forEach(entry -> sourceTemplate.add(entry.getKey(), entry.getValue()));

        var expandedSourcePath = Paths.get(sourceTemplate.render());
        return targetFolderRootPath.resolve(sourceFolderRootPath.relativize(expandedSourcePath));
    }

}
