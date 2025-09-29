package com.devandcode.files;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
}
