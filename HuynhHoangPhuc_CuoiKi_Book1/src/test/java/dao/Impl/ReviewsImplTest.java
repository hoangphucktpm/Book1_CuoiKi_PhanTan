package dao.Impl;

import dao.ReviewsDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReviewsImplTest {
    private ReviewsDao reviewsDao;

    @BeforeAll
    public void setUp() throws RemoteException {
        reviewsDao = new ReviewsImpl();
    }

    // Câu c
    @Test
    public void testUpdateReviews() throws RemoteException {
        String isbn = "888-0321660783";
        String readerID = "14";
        int rating = 5;
        String comment = "Good 77777    77777777777";
        assertTrue(reviewsDao.updateReviews(isbn, readerID, rating, comment));
    }




    @AfterAll
    public void tearDown() throws RemoteException {
        reviewsDao = null;
    }

}