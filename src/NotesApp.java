import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class NotesApp {
    private static final String FILE_NAME = "notes.txt";
    private static ArrayList<String> notes = new ArrayList<>();

    public static void main(String[] args) {
        loadNotes();
        if (notes.isEmpty()) {
            notes.add("Это моя первая заметка!");
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nСписок заметок:");
            for (int i = 0; i < notes.size(); i++) {
                System.out.println((i + 1) + ". " + notes.get(i));
            }

            System.out.println("\nВыберите действие:");
            System.out.println("1. Создать новую заметку");
            System.out.println("2. Редактировать заметку");
            System.out.println("3. Удалить заметку");
            System.out.println("4. Выйти");

            int choice = 0;
            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.print("Введите номер действия: ");
                    choice = Integer.parseInt(scanner.nextLine());
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Недопустимое значение, введите число.");
                }
            }

            switch (choice) {
                case 1:
                    System.out.println("Введите текст новой заметки:");
                    String newNote = scanner.nextLine();
                    notes.add(newNote);
                    break;
                case 2:
                    System.out.println("Введите номер заметки для редактирования:");
                    int editIndex = getNoteIndex(scanner);
                    if (editIndex != -1) {
                        System.out.println("Введите новый текст:");
                        String updatedNote = scanner.nextLine();
                        notes.set(editIndex, updatedNote);
                    }
                    break;
                case 3:
                    System.out.println("Введите номер заметки для удаления:");
                    int deleteIndex = getNoteIndex(scanner);
                    if (deleteIndex != -1) {
                        notes.remove(deleteIndex);
                    }
                    break;
                case 4:
                    saveNotes();
                    System.out.println("Выход из приложения.");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static int getNoteIndex(Scanner scanner) {
        int index = -1;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Введите номер: ");
                index = Integer.parseInt(scanner.nextLine()) - 1;
                if (index >= 0 && index < notes.size()) {
                    validInput = true;
                } else {
                    System.out.println("Неверный номер заметки.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Недопустимое значение, введите число.");
            }
        }
        return index;
    }

    private static void loadNotes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                notes.add(line);
            }
        } catch (IOException e) {
            // Файл не существует, ничего не делаем
        }
    }

    private static void saveNotes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String note : notes) {
                writer.write(note);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении заметок.");
        }
    }
}