package server;

import dao.BookDao;
import dao.Impl.BookImpl;
import dao.Impl.ReviewsImpl;
import dao.ReviewsDao;
import entity.Book;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class Server {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(6541)) {
            System.out.println("Server is running on port 6541");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                System.out.println("Client IP: " + socket.getInetAddress().getHostAddress());
                Thread clientThread = new Thread(new ClientHandler(socket));
                clientThread.start();
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
    }
}
    }

    class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                BookDao bookDao = new BookImpl();
                ReviewsDao reviewDao = new ReviewsImpl();
                int choice = 0;
                while (true)
                {
                    try{
                        choice = inputStream.readInt();

                    } catch (IOException e) {
                        System.out.println("Client disconnected");
                        break;
                    }
                    switch (choice) {
                        case 1:
                            System.out.println("Câu a: Liệt kê danh sách các cuốn sách được viết bởi tác giả nào đó khi biết tên tác giả và\n" +
                                    "có điểm đánh giá từ mấy sao trở lên");
                            String author = inputStream.readUTF();
                            int rating = inputStream.readInt();
                            List<Book> books = bookDao.listRatedBooks(author, rating);
                            books.forEach(System.out::println);
                            outputStream.writeObject(books);
                            outputStream.flush();
                            break;

                        case 2:
                            System.out.println("Câu b: Thống kê số cuốn sách được dịch sang ngôn ngữ khác của từng tác giả, kết quả sắp \n" +
                                    "xếp theo tên tác giả.");
                            Map<String, Long> countBooksByAuthor = bookDao.countBooksByAuthor();
                            outputStream.writeObject(countBooksByAuthor);
                            outputStream.flush();
                            break;

                        case 3:
                            System.out.println("Câu c: Thêm một lượt đánh giá cho một cuốn sách, cập nhật thành công nếu cuốn sách và người đọc đã tồn tại, rating phải từ 1 đến 5 và bình luận không được để trống hay rỗng.");
                            String isbn = inputStream.readUTF();
                            String readerID = inputStream.readUTF();
                            int rating1 = inputStream.readInt();
                            String comment = inputStream.readUTF();
                            boolean result = reviewDao.updateReviews(isbn, readerID, rating1, comment);
                            outputStream.writeBoolean(result);
                            outputStream.flush();
                            break;
                        default:
                            break;
                    }
                }
                socket.close();


            } catch (IOException e) {
                System.out.println("Client exception: " + e.getMessage());
            }
        }
    }
