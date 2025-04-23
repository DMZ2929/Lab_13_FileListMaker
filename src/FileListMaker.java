import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileListMaker {
    private static ArrayList<String> itemList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static boolean needsToBeSaved = false;
    private static String currentFilename = "";

    public static void main(String[] args) {
        System.out.println("Welcome toFile List Maker!");

        boolean running = true;
        while (running) {
            displayMenu();
            String choice = SafeInput.getRegExString(scanner, "Enter your choice", "[AaDdIiVvQqOoSsCcMm]").toUpperCase();

            try {
                switch (choice) {
                    case "A":
                        addItem();
                        break;
                    case "D":
                        deleteItem();
                        break;
                    case "I":
                        insertItem();
                        break;
                    case "V":
                        viewList();
                        break;
                    case "M":
                        moveItem();
                        break;
                    case "O":
                        if (checkSaveBeforeAction()) {
                            openList();
                        }
                        break;
                    case "S":
                        saveList();
                        break;
                    case "C":
                        if (confirmClear()) {
                            clearList();
                        }
                        break;
                    case "Q":
                        running = !confirmQuit();
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("Goodbye!");
    }

    private static void displayMenu() {
        System.out.println("\nCurrent List: " + (currentFilename.isEmpty() ? "Unsaved list" : currentFilename));
        System.out.println("Menu Options:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("I - Insert an item into the list");
        System.out.println("V - View the list");
        System.out.println("M - Move an item in the list");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C - Clear the current list");
        System.out.println("Q - Quit the program");
    }

    private static void viewList() {
        System.out.println("\nCurrent List:");
        printNumberedList();
    }

    private static void printNumberedList() {
        if (itemList.isEmpty()) {
            System.out.println("The list is currently empty");
        } else {
            for (int i = 0; i < itemList.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, itemList.get(i));
            }
        }
    }

    private static void addItem() {
        String newItem = SafeInput.getNonZeroLenString(scanner, "Enter the item to add");
        itemList.add(newItem);
        needsToBeSaved = true;
        System.out.println("Item added: " + newItem);
    }

    private static void deleteItem() {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty - nothing to delete");
            return;
        }

        printNumberedList();
        int itemNum = SafeInput.getRangedInt(scanner, "Enter the item number to delete", 1, itemList.size());
        String removedItem = itemList.remove(itemNum - 1);
        needsToBeSaved = true;
        System.out.println("Item removed: " + removedItem);
    }

    private static void insertItem() {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty - adding as first item");
            addItem();
            return;
        }

        printNumberedList();
        int itemNum = SafeInput.getRangedInt(scanner, "Enter the position number to insert at", 1, itemList.size() + 1);
        String newItem = SafeInput.getNonZeroLenString(scanner, "Enter the item to insert");
        itemList.add(itemNum - 1, newItem);
        needsToBeSaved = true;
        System.out.println("Item inserted: " + newItem);
    }

    private static void moveItem() {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty - nothing to move");
            return;
        }

        printNumberedList();
        int fromPos = SafeInput.getRangedInt(scanner, "Enter the item number to move", 1, itemList.size());
        int toPos = SafeInput.getRangedInt(scanner, "Enter the new position", 1, itemList.size());

        if (fromPos == toPos) {
            System.out.println("Item is already at position " + toPos);
            return;
        }

        String item = itemList.remove(fromPos - 1);
        itemList.add(toPos - 1, item);
        needsToBeSaved = true;
        System.out.printf("Moved item '%s' from position %d to position %d\n", item, fromPos, toPos);
    }

    private static void openList() throws IOException {
        String filename = SafeInput.getNonZeroLenString(scanner, "Enter the filename to open (without .txt extension)");
        Path filePath = Paths.get(filename + ".txt");

        if (!Files.exists(filePath)) {
            System.out.println("File does not exist");
            return;
        }

        if (confirmAction("This will replace the current list. Continue?")) {
            itemList = new ArrayList<>(Files.readAllLines(filePath));
            currentFilename = filename;
            needsToBeSaved = false;
            System.out.println("List loaded from " + filePath);
        }
    }

    private static void saveList() throws IOException {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty - nothing to save");
            return;
        }

        String filename = currentFilename.isEmpty()
                ? SafeInput.getNonZeroLenString(scanner, "Enter filename to save (without .txt extension)")
                : currentFilename;

        Path filePath = Paths.get(filename + ".txt");

        if (Files.exists(filePath) && !confirmAction("File exists. Overwrite?")) {
            return;
        }

        Files.write(filePath, itemList);
        currentFilename = filename;
        needsToBeSaved = false;
        System.out.println("List saved to " + filePath);
    }

    private static void clearList() {
        itemList.clear();
        needsToBeSaved = true;
        System.out.println("List cleared");
    }

    private static boolean confirmQuit() {
        if (needsToBeSaved) {
            if (SafeInput.getYNConfirm(scanner, "You have unsaved changes. Save before quitting?")) {
                try {
                    saveList();
                } catch (IOException e) {
                    System.out.println("Error saving file: " + e.getMessage());
                    return false;
                }
            }
        }
        return SafeInput.getYNConfirm(scanner, "Are you sure you want to quit?");
    }

    private static boolean checkSaveBeforeAction() throws IOException {
        if (needsToBeSaved) {
            if (SafeInput.getYNConfirm(scanner, "You have unsaved changes. Save before continuing?")) {
                saveList();
                return true;
            }
            return SafeInput.getYNConfirm(scanner, "Continue without saving?");
        }
        return true;
    }

    private static boolean confirmClear() {
        if (!itemList.isEmpty()) {
            return SafeInput.getYNConfirm(scanner, "This will clear all items. Continue?");
        }
        return false;
    }

    private static boolean confirmAction(String prompt) {
        return SafeInput.getYNConfirm(scanner, prompt);
    }
}