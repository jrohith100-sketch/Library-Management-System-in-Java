import java.io.*;
import java.util.*;

class Book {
    private String id;
    private String title;
    private String author;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    @Override
    public String toString() {
        return id + "," + title + "," + author;
    }

    public static Book fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length < 3) return null;
        return new Book(parts[0], parts[1], parts[2]);
    }
}

public class LibraryManagement {
    private static final String FILE_NAME = "books.txt";
    private static List<Book> books = new ArrayList<>();

    public static void main(String[] args) {
        loadBooks();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addBook(sc);
                case 2 -> viewBooks();
                case 3 -> searchBook(sc);
                case 4 -> deleteBook(sc);
                case 5 -> {
                    saveBooks();
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }
    }

    private static void addBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();

        books.add(new Book(id, title, author));
        System.out.println("Book added successfully!");
    }

    private static void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        System.out.println("\n--- Book List ---");
        for (Book b : books) {
            System.out.println("ID: " + b.getId() + " | Title: " + b.getTitle() + " | Author: " + b.getAuthor());
        }
    }

    private static void searchBook(Scanner sc) {
        System.out.print("Enter title or author to search: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword) || b.getAuthor().toLowerCase().contains(keyword)) {
                System.out.println("Found -> ID: " + b.getId() + " | Title: " + b.getTitle() + " | Author: " + b.getAuthor());
                found = true;
            }
        }
        if (!found) System.out.println("No matching book found.");
    }

    private static void deleteBook(Scanner sc) {
        System.out.print("Enter Book ID to delete: ");
        String id = sc.nextLine();

        books.removeIf(b -> b.getId().equals(id));
        System.out.println("Book deleted if ID existed.");
    }

    private static void loadBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Book book = Book.fromString(line);
                if (book != null) books.add(book);
            }
        } catch (IOException e) {
            System.out.println("No existing book file found, starting fresh.");
        }
    }

    private static void saveBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Book b : books) {
                bw.write(b.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }
}
