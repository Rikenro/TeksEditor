import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class TextEditor {
    private char[] currentText;      // Menyimpan teks saat ini
    private Stack<String> undoStack; // Stack untuk menyimpan teks untuk fungsi undo
    private Stack<String> redoStack; // Stack untuk menyimpan teks untuk fungsi redo
    private int textLength;           // Menyimpan panjang teks saat ini

    public TextEditor() {
        currentText = new char[1000]; // Ukuran array karakter awal
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        textLength = 0;
    }

    // Fungsi untuk menampilkan isi teks saat ini
    public void show() {
        System.out.print("Current Text: ");
        for (int i = 0; i < textLength; i++) {
            System.out.print(currentText[i]);
        }
        System.out.println();
    }

    // Fungsi untuk menambahkan teks baru
    public void write(String text) {
        undoStack.push(new String(currentText, 0, textLength)); // Simpan teks sebelum perubahan ke undoStack
        for (char c : text.toCharArray()) {
            if (textLength < currentText.length) {
                currentText[textLength++] = c; // Tambahkan karakter ke array
            }
        }
        redoStack.clear(); // Kosongkan redoStack setelah menulis teks baru
    }

    // Fungsi untuk mengembalikan teks ke isi sebelumnya
    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(new String(currentText, 0, textLength)); // Simpan teks saat ini ke redoStack
            String previousText = undoStack.pop();
            currentText = previousText.toCharArray(); // Kembalikan ke teks sebelumnya
            textLength = previousText.length(); // Perbarui panjang teks
        } else {
            System.out.println("Tidak ada tindakan yang bisa di-undo.");
        }
    }

    // Fungsi untuk memulihkan teks ke isi yang lebih baru
    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(new String(currentText, 0, textLength)); // Simpan teks saat ini ke undoStack
            String nextText = redoStack.pop();
            currentText = nextText.toCharArray(); // Kembalikan ke teks yang lebih baru
            textLength = nextText.length(); // Perbarui panjang teks
        } else {
            System.out.println("Tidak ada tindakan yang bisa di-redo.");
        }
    }

    // Fungsi untuk menyimpan isi text editor ke file .txt
    public void exportToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(currentText, 0, textLength); // Simpan karakter ke file
            System.out.println("Teks berhasil disimpan ke file " + filename);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();

        editor.write("Hello ");
        editor.write("world!");
        editor.show();

        editor.undo();
        editor.show();

        editor.redo();
        editor.show();

        editor.write(" Let's code!");
        editor.show();

        editor.exportToFile("output.txt");
    }
}
