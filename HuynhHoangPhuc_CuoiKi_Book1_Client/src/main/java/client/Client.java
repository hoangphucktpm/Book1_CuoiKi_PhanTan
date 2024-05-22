package client;

import entity.Book;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost", 6541);
            Scanner scanner = new Scanner(System.in);
       ) {
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                System.out.println("Connected to server");
                int choice = 0;
                while (true) {
                    System.out.println("1. List books by author and rating");
                    System.out.println("2. Count books by author");
                    System.out.println("3. Update reviews");
                    System.out.println("4. Exit");
                    System.out.print("Enter your choice: ");
                    choice = scanner.nextInt();
                    outputStream.writeInt(choice);
                    outputStream.flush();
                    switch (choice) {
                        case 1:
                            scanner.nextLine();
                            System.out.print("Enter author name: ");
                            String author = scanner.nextLine();
                            outputStream.writeUTF(author);
                            System.out.print("Enter rating: ");
                            int rating = scanner.nextInt();
                            outputStream.writeInt(rating);
                            scanner.nextLine();
                            outputStream.flush();

                            List<Book> books = (List<Book>) inputStream.readObject();
                            books.forEach(System.out::println);
                            break;

                        case 2:
                            scanner.nextLine();
                            Map<String, Long> authorBooks = (Map<String, Long>) inputStream.readObject();
                            for (Map.Entry<String, Long> entry : authorBooks.entrySet()) {
                                System.out.println(entry.getKey() + " : " + entry.getValue());
                            }
                            break;
                        case 3:
                            scanner.nextLine();
                            System.out.print("Enter ISBN: ");
                            String isbn = scanner.nextLine();
                            System.out.print("Enter reader ID: ");
                            String readerID = scanner.nextLine();
                            System.out.print("Enter rating: ");
                            int rating1 = scanner.nextInt();
                            System.out.print("Enter comment: ");
                            String comment = scanner.next();
                            outputStream.writeUTF(isbn);
                            outputStream.writeUTF(readerID);
                            outputStream.writeInt(rating1);
                            outputStream.writeUTF(comment);
                            boolean status = inputStream.readBoolean();
                            if (status) {
                                System.out.println("Review updated successfully");
                            } else {
                                System.out.println("Review update failed");
                            }
                            break;
                        case 4:
                            System.out.println("Exiting...");
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

