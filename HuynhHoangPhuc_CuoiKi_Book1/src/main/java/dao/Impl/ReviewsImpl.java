package dao.Impl;

import dao.BookDao;
import dao.ReviewsDao;
import entity.Book;
import entity.Person;
import entity.Reviews;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ReviewsImpl extends UnicastRemoteObject implements ReviewsDao {

    private static final long serialVersionUID = 1L;
    private EntityManager em;

    public ReviewsImpl() throws RemoteException {
        em = Persistence.createEntityManagerFactory("SQLdb").createEntityManager();
    }

    // c) (1.5 điểm) thêm một lượt đánh giá cho một cuốn sách, cập nhật thành công nếu cuốn
    //sách và người đọc đã tồn tại, rating phải từ 1 đến 5 và bình luận không được để trống hay rỗng.
    //+ updateReviews(isbn: String, readerID: String, rating: int, comment: String): boolen

    @Override
    public boolean updateReviews(String isbn, String readerID, int rating, String comment) throws RemoteException  {
        if (rating < 1 || rating > 5 || comment == null || comment.isEmpty()) {
            return false;
        }

        try{
            em.getTransaction().begin();
            String check = "SELECT r FROM Reviews r WHERE r.books.ISBN = :isbn AND r.person.id = :readerID";
            List<Reviews> existingReviews = em.createQuery(check, Reviews.class)
                    .setParameter("isbn", isbn)
                    .setParameter("readerID", readerID)
                    .getResultList();

            if (!existingReviews.isEmpty()) {
                // Update the existing review
                Reviews existingReview = existingReviews.get(0);
                existingReview.setRating(rating);
                existingReview.setComment(comment);
            } else {
                // Create a new review
                Reviews reviews = new Reviews();
                reviews.setBooks(em.find(Book.class, isbn));
                reviews.setPerson(em.find(Person.class, readerID));
                reviews.setRating(rating);
                reviews.setComment(comment);
                em.persist(reviews);
            }

            em.getTransaction().commit();
            return true;
        }
        catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }


}
