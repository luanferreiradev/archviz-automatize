package com.devandcode.files.reader;

import com.devandcode.files.FilesUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextReader implements FileReader {

    private final Path path;
    private final Charset charset;

    public TextReader(Path path, Charset charset) {
        Objects.requireNonNull(charset);
        this.charset = charset;
        Objects.requireNonNull(path);
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public String getExtension() {
        return FilesUtils.getFileExtension(path);
    }

    @Override
    public String getName() {
        return FilesUtils.getFileName(path);
    }

    @Override
    public List<String> read() {
        List<String> list = new ArrayList<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(path, charset)) {
            String line = bufferedReader.readLine() ;
            while (line != null) {
                line = new String(line.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
                list.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("It was not possible to read the file");
        }
        return list;
    }
}
