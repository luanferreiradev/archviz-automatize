package com.devandcode.service;

import com.devandcode.files.writer.TextWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RenderScriptService {

    private final Path rootPath;
    private static final String SCRIPT_FOLDER_NAME = "00 - Render Script";
    private static final String MASTER_FILE_NAME = "master.ps1";
    private static final Path RESOURCES_PATH = Path.of("resources");

    public RenderScriptService(Path rootPath) {
        Objects.requireNonNull(rootPath);
        this.rootPath = rootPath;
    }

    public void create() {
        createFolderStructure();
        copyMasterFile();
        creteBatFiles();
    };

    private void copyMasterFile() {
        try {
            Files.copy(RESOURCES_PATH.resolve(MASTER_FILE_NAME), Files.newOutputStream(
                    rootPath.resolve(SCRIPT_FOLDER_NAME).resolve(MASTER_FILE_NAME),
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE));
        } catch (IOException e) {
            throw new RuntimeException("Was not possible to copy the file master.ps1 to the destination", e);
        }
    }

    private List<Path> getProjectsPaths() {
        try (Stream<Path> stream = Files.list(rootPath)) {
            return stream.filter(path -> !SCRIPT_FOLDER_NAME.equals(
                            path.getFileName().toString()) && Files.isDirectory(path))
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new RuntimeException("It is not possible to list the projects", e);
        }
    }

    private void createFolderStructure() {
        List<String> folders = List.of("completed", "ignore", "logs");

        folders.forEach(folder -> {
            try {
                Files.createDirectories(rootPath.resolve(SCRIPT_FOLDER_NAME).resolve(folder));
            } catch (IOException e) {
                throw new RuntimeException("Was not possible to create the folder " + folder, e);
            }
        });
    }

    private void creteBatFiles() {
        for (Path projectPath : getProjectsPaths()) {
            Path scriptFolderPath = rootPath.resolve(SCRIPT_FOLDER_NAME);
            Path maxFilePath = projectPath.resolve(projectPath.getFileName() + ".max");
            List<String> batLines = createBatLines(maxFilePath);
            new TextWriter(scriptFolderPath, projectPath.getFileName().toString(), "bat", batLines).write();
        }
    }

    private static List<String> createBatLines(Path maxFilePath) {
        String texto = "@CHCP 1252 >NUL\n" +
                "3dsmaxcmd \"" + maxFilePath.toString() + "\" -batchRender -gammaCorrection:1 -gammaValueIn:2.2 -gammaValueOut:2.2";
        return Arrays.asList(texto.split("\\n"));
    }
}
