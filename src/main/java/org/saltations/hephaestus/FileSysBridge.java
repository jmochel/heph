package org.saltations.hephaestus;

import java.nio.file.Path;

/**
 * TODO Summary(ends with '.',third person[gets the X, not Get X],do not use @link) FileSysBridge represents xxx OR FileSysBridge does xxxx.
 *
 * <p>TODO Description(1 lines sentences,) References generic parameters with {@code <T>} and uses 'b','em', dl, ul, ol tags
 */
interface FileSysBridge
{

    void makeFolder(Path targetFolderPath);

    void skipFile(Path sourceFile);

    void createFile(Path sourceFile, Path targetFile);

    void copyFile(Path sourceFile, Path targetFile);
}
