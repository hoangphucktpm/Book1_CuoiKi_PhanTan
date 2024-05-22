package dao.Impl;

import dao.BookDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookImplTest {
    private BookDao bookDao;

    @BeforeAll
    public void setUp() throws RemoteException {
        bookDao = new BookImpl();
    }

    // Câu a
    @Test
    public void testListRatedBooks() throws RemoteException {
        String author = "Richard Helm";
        int rating = 0;
        assertEquals(2, bookDao.listRatedBooks(author, rating).size());
    }

    // Câu b
    @Test
    public void testCountBooksByAuthor() throws RemoteException {
        assertEquals(14, bookDao.countBooksByAuthor().size());

    }



    @AfterAll
    public void tearDown() throws RemoteException {
        bookDao = null;
    }

}