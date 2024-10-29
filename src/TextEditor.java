import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class TextEditor {
    private String text;
    private Stack<String> undoStack;
    private Stack<String> redoStack;

    public TextEditor() {
        text = "";
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void loadFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
            text = fileContent.toString();
            System.out.println("File berhasil dimuat.");
        } catch (IOException e) {
            System.out.println("Error saat memuat file: " + e.getMessage());
        }
    }

    public void saveFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(text);
            System.out.println("File berhasil disimpan.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void write(String newText) {
        undoStack.push(text);
        redoStack.clear();
        text += newText;
    }

    public void show() {
        System.out.println( text);
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(text);
            text = undoStack.pop();
        } else {
            System.out.println("silahkan masukkan kalimat");
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(text);
            text = redoStack.pop();
        } else {
            System.out.println("silahkan masukkan kalimat");
        }
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        Scanner scanner = new Scanner(System.in);
        String perintah;

        System.out.println("Contoh:C:\\Users\\Dell Latitude\\Documents\\test.txt");
        System.out.print("Masukkan nama file: ");
        String filePath = scanner.nextLine();
        editor.loadFile(filePath);

        while (true) {
            System.out.print("Masukkan perintah (write/show/undo/redo/save/exit): ");
            perintah = scanner.nextLine().trim();

            switch (perintah.toLowerCase()) {
                case "write":
                    System.out.print("Enter text to write: ");
                    String textToWrite = scanner.nextLine();
                    editor.write(textToWrite);
                    break;
                case "show":
                    editor.show();
                    break;
                case "undo":
                    editor.undo();
                    break;
                case "redo":
                    editor.redo();
                    break;
                case "save":
                    System.out.println("Contoh:C:\\Users\\Dell Latitude\\Documents\\test.txt");
                    System.out.print("Tulis alamat untuk menyimpan file: ");
                    String savePath = scanner.nextLine();
                    editor.saveFile(savePath);
                    break;
                case "exit":
                    System.out.println("Exiting the editor.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Unknown command. Please try again.");
            }
        }
    }
}
