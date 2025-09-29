package com.devandcode.files.reader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BatchRead {

    Path rootPath;

    public BatchRead(Path rootPath) {
        this.rootPath = rootPath;
    }

    public Path getRootPath() {
        return rootPath;
    }

    private void setRootPath(Path rootPath) {
        this.rootPath = rootPath;
    }

    public Map<Path, List<String>> read(Charset charset) {
        Map<Path, List<String>> filesTexts = new HashMap<>();
        for (Path subPath : getSubPaths()) {
            FileReader fileReader = new TextReader(subPath, charset);
            filesTexts.put(subPath, fileReader.read());
        }
        return filesTexts;
    }

    private List<Path> getSubPaths() {
        try (Stream<Path> stream = Files.list(rootPath)) {
            return stream.filter(path -> !Files.isDirectory(path))
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new RuntimeException("Could not get subpaths");
        }
    }
}
