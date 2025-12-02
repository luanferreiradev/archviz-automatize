package com.devandcode.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesUtils {
    public static List<String> cleanText(List<String> lines) {
        return lines.stream().map(line -> line.replaceAll("[^\\p{Alnum}\\p{Punct}\\p{Blank}]", ""))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static String getFileExtension(Path path) {
        var fileName = path.getFileName().toString();
        var dotPosition = fileName.lastIndexOf(".");
        return fileName.substring(dotPosition + 1);
    }

    public static String getFileName(Path path) {
        var fileCompleteName = path.getFileName().toString();
        var dotPosition = fileCompleteName.lastIndexOf(".");
        return path.getFileName().toString().substring(0, dotPosition);
    }

    public static List<String> listProjects(Path rootPath) {

        try (Stream<Path> stream = Files.list(rootPath)) {
            return stream.filter(Files::isDirectory)
                    .filter(file -> !file.getFileName().toString().contains("00 - Render Script"))
                    .map(file -> file.getFileName().toString())
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException("It was not possible to list the projects", e);
        }
    }
}
