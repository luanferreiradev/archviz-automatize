import com.devandcode.files.FilesUtils;
import com.devandcode.files.reader.BatchRead;
import com.devandcode.files.writer.BatchWrite;
import com.devandcode.service.FolderStructureService;
import com.devandcode.service.RenderScriptService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            chooseOperation(sc);
            System.out.print("Continue: (y)/(n) ");
            String continueOperations = sc.nextLine();
            if (continueOperations.equals("n")) break;
        } while (true);
    }

    public static void chooseOperation(Scanner sc) {
        System.out.println("Inform the operation: (1) Create Render Script; (2) Create Folder Structure; (3) Clear Logs; (4) List Projects;");
        int option = sc.nextInt();
        sc.nextLine();
        switch (option) {
            case 1 -> createRenderScript(sc);
            case 2 -> createFolderStructure(sc);
            case 3 -> clearLogs(sc);
            case 4 -> listProjects(sc);
        }
    }

    public static void clearLogs(Scanner sc) {
        System.out.print("Inform the folder: ");
        Path path = Path.of(sc.nextLine());

        BatchRead batchRead = new BatchRead(path);
        new BatchWrite(batchRead.read(StandardCharsets.UTF_16LE)).write();
    }

    public static void createRenderScript(Scanner sc) {
        System.out.print("Inform the folder: ");
        String rootPath = sc.nextLine();

        new RenderScriptService(Path.of(rootPath)).create();
    }

    public static void createFolderStructure(Scanner sc) {
        Properties appProperties = new Properties();
        Path appPropertiesPath = Path.of("resources").resolve("app.properties");
        try {
            appProperties.load(Files.newInputStream(appPropertiesPath));
        } catch (IOException e) {
            throw new RuntimeException("It was not possible to read the file: " + appPropertiesPath, e);
        }
        List<String> subFolders = Arrays.asList(appProperties.getProperty("subfolders").split(","));
        System.out.print("Inform the folder: ");
        String rootPath = sc.nextLine();
        FolderStructureService folderStructureService = new FolderStructureService(
                Path.of(rootPath), "skp", subFolders);
        folderStructureService.create();
    }

    public static void listProjects(Scanner sc) {
        System.out.print("Inform the folder: ");
        Path rootPath = Path.of(sc.nextLine());
        var projects = FilesUtils.listProjects(rootPath);
        int i = 0;
        NumberFormat numberFormat = new DecimalFormat("00");
        for (String project : projects) {
            System.out.println(numberFormat.format(i) + " - " + project);
            i++;
        }
    }
}

