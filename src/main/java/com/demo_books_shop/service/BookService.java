package com.demo_books_shop.service;

import com.demo_books_shop.exception.BookIsAlreadyExistException;
import com.demo_books_shop.exception.BookNotFoundException;
import com.demo_books_shop.models.Books;
import com.demo_books_shop.models.Bucket;
import com.demo_books_shop.models.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    List<Books> getAllBooks();

    Books getBook(int BooksId)throws BookNotFoundException;

    @Transactional
    void delete(int bookId,int userId);
    Books saveBook(Books book, @AuthenticationPrincipal User user) throws BookIsAlreadyExistException;

    List<Books>getByAuthor(int authorId);

    List<Books> getBooksByCategory(String name);
    List<Books> getBooksByName(String name);

    List<Books> getAllBooksInBucket(int bucketId);

    void updateBucket(int bucketId, int booksId);

    void deleteFromBucket(int booksId, Bucket bucket);

    String transferFile(MultipartFile multipartFile);


}
