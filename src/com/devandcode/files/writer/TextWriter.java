package com.devandcode.files.writer;

import com.devandcode.files.FilesUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class TextWriter implements FileWriter {

    private final Path rootFolder;
    private final String fileName, fileExtension;
    private final List<String> textLines;

    public TextWriter(Path path, String fileName, String fileExtension, List<String> textLines) {
        Objects.requireNonNull(path);
        this.rootFolder = path;
        Objects.requireNonNull(textLines);
        this.textLines = textLines;
        Objects.requireNonNull(fileName);
        if (fileName.isEmpty()) throw new IllegalArgumentException("File name should not be empty");
        this.fileName = fileName;
        Objects.requireNonNull(fileExtension);
        if (fileExtension.isEmpty()) throw new IllegalArgumentException("File extension should not be empty");
        this.fileExtension = fileExtension;
    }

    private Path getDestinationFolder() {
        return Path.of(rootFolder.toString(), fileName + "." + fileExtension);
    }

    @Override
    public void write() {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(getDestinationFolder(), StandardCharsets.UTF_8)) {
            for (String line : textLines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("File could not be written");
        }
    }
}
