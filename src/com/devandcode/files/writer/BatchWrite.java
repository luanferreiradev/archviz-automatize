package com.devandcode.files.writer;

import com.devandcode.files.FilesUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class BatchWrite {
    private final Map<Path, List<String>> fileMap;

    public BatchWrite(Map<Path, List<String>> fileMap) {
        this.fileMap = fileMap;
    }

    public void write() {
        for (Map.Entry<Path, List<String>> pathListEntry : fileMap.entrySet()) {

            Path originalPath = pathListEntry.getKey();
            List<String> lines = FilesUtils.cleanText(pathListEntry.getValue());
            String fileName = FilesUtils.getFileName(pathListEntry.getKey());
            String fileExtension = FilesUtils.getFileExtension(pathListEntry.getKey());
            Path newFolder = Path.of(originalPath.getParent().toString(), "corrigido");

            if (!Files.exists(newFolder)) {
                try {
                    Files.createDirectory(newFolder);
                } catch (IOException e) {
                    throw new RuntimeException("Error creating folder");
                }
            }

            new TextWriter(newFolder, fileName, fileExtension, lines).write();
        }
    }
}
