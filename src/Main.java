import com.devandcode.files.reader.BatchRead;
import com.devandcode.files.writer.BatchWrite;
import com.devandcode.service.RenderScriptService;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

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
        System.out.println("Inform the operation: (1) Create Render Script; (2) Clear Logs;");
        int option = sc.nextInt();
        sc.nextLine();
        switch (option) {
            case 1 -> createRenderScript(sc);
            case 2 -> clearLogs(sc);
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
}

