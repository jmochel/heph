package org.saltations.hephaestus;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;


@Slf4j
class FileSysLogOnlyBridge implements FileSysBridge
{
    @Override
    public void makeFolder(Path targetFolderPath)
    {
        log.info("Creating folder [{}]", targetFolderPath);
    }

    @Override
    public void createFile(Path sourceFile, Path targetFile)
    {
        log.info("Transforming from [{}] to [{}]", sourceFile, targetFile);
    }

    @Override
    public void copyFile(Path sourceFile, Path targetFile)
    {
        log.info("Copying from [{}] to [{}]", sourceFile, targetFile);
    }

    @Override
    public void skipFile(Path sourceFile)
    {
        log.info("Skipping processing [{}]", sourceFile);
    }
}
