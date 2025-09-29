package com.devandcode.files.reader;

import java.io.IOException;
import java.util.List;

public interface FileReader {
    List<String> read();
    String getExtension();
    String getName();
}
