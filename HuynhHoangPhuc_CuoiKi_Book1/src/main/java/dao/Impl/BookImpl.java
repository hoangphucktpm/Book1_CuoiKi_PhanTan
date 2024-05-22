package dao.Impl;

import dao.BookDao;
import entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookImpl extends UnicastRemoteObject implements BookDao {

    private static final long serialVersionUID = 1L;
    private EntityManager em;

    public BookImpl() throws RemoteException {
        em = Persistence.createEntityManagerFactory("SQLdb").createEntityManager();
    }

    // a) (1.5 điểm) Liệt kê danh sách các cuốn sách được viết bởi tác giả nào đó khi biết tên tác giả và
    //có điểm đánh giá từ mấy sao trở lên.
    //+ listRatedBooks(author: String, rating: int): List<Book>

    @Override
    public List<Book> listRatedBooks(String author, int rating) throws RemoteException {
        return em.createQuery("SELECT b FROM Book b " +
                                "JOIN b.reviews r " +
                                "JOIN b.authors a " +
                                "WHERE r.rating >= :rating " +
                                "AND a = :author",
                        Book.class)
                .setParameter("author", author)
                .setParameter("rating", rating)
                .getResultList();
    }

    // b) (1.5 điểm) Thống kê số cuốn sách được dịch sang ngôn ngữ khác của từng tác giả, kết quả sắp
    //xếp theo tên tác giả.
    //+ countBooksByAuthor(): Map<String, Long>

    @Override
    public Map<String, Long> countBooksByAuthor() throws RemoteException {
        return em.createQuery("SELECT a, COUNT(b) FROM Book b " +
                        "JOIN b.authors a " +
                        "GROUP BY a",
                Object[].class)
                .getResultList()
                .stream()
                .collect(Collectors.toMap(
                        o -> (String) o[0],
                        o -> (Long) o[1],
                        (a, b) -> a,
                        LinkedHashMap::new
                ));


    }
}