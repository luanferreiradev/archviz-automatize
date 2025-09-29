package com.devandcode.service;

import com.devandcode.files.FilesUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FolderStructureService {

    private final Path rootPath;
    private final String fileFormat;
    private final List<String> folders;

    public FolderStructureService(Path rootPath, String fileFormat, List<String> folders) {
        Objects.requireNonNull(rootPath);
        this.rootPath = rootPath;
        Objects.requireNonNull(fileFormat);
        this.fileFormat = fileFormat;
        Objects.requireNonNull(folders);
        this.folders = folders;
    }

    public void create() {
        for (Path filePath : getFilesPaths()) {
            Path projectFolderPath = createProjectFolder(filePath);
            moveFile(filePath, projectFolderPath.resolve(filePath.getFileName()));
            createSubFolders(projectFolderPath);
        }
    }

    private void createSubFolders(Path projectFolderPath) {
        for (String folder : folders) {
            try {
                Files.createDirectory(projectFolderPath.resolve(folder));
            } catch (IOException e) {
                throw new RuntimeException("It was not possible to create the subfolders", e);
            }
        }
    }

    private Path createProjectFolder(Path filePath) {
        Path projectFolderPath = rootPath.resolve(FilesUtils.getFileName(filePath));
        try {
            Files.createDirectory(projectFolderPath);
        } catch (IOException e) {
            throw new RuntimeException("It was not possible to create the folders", e);
        }
        return projectFolderPath;
    }

    private List<Path> getFilesPaths() {
        try (Stream<Path> stream = Files.list(rootPath)) {
            return stream.filter(path -> fileFormat.equals(FilesUtils.getFileExtension(path)))
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new RuntimeException("It was not possible to list the files int the root folder", e);
        }
    }

    private void moveFile(Path origin, Path destiny) {
        try {
            Files.move(origin, destiny);
        } catch (IOException e) {
            throw new RuntimeException("It was not possible to move the file", e);
        }
    }
}
