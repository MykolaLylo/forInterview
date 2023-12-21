package com.demo_books_shop.service.impl;

import com.demo_books_shop.exception.BookIsAlreadyExistException;
import com.demo_books_shop.exception.BookNotFoundException;
import com.demo_books_shop.models.Books;
import com.demo_books_shop.models.Bucket;
import com.demo_books_shop.models.User;
import com.demo_books_shop.repository.BooksRepository;
import com.demo_books_shop.service.BookService;

import com.demo_books_shop.utils.FileUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@EnableTransactionManagement
public class BooksServiceImp implements BookService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksServiceImp(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public List<Books> getAllBooks() {
        return booksRepository.findAll();
    }

    @Override
    public Books getBook(int booksId)throws BookNotFoundException{
         Optional<Books> books = booksRepository.findById(booksId);
         if (books.isEmpty()){
             throw new BookNotFoundException("book not found");
         }
         return books.get();
    }

    @Override
    public void delete(int bookId, int userId){
        booksRepository.deleteBooksByBooksIdAndAuthorId(bookId,userId);
    }

    @Override
    public Books saveBook(Books book, @AuthenticationPrincipal User user)throws BookIsAlreadyExistException {
        Optional<Books> findBook = booksRepository.findById(book.getBooksId());
        if (findBook.isEmpty()){
            book.setAuthor(user);
            return booksRepository.save(book);
        }
        throw new BookIsAlreadyExistException("this book is present");
    }
    @Override
    public List<Books>getByAuthor(int authorId){
       return booksRepository.findBooksByAuthorUserId(authorId);
    }
    @Override
    public List<Books> getBooksByCategory(String category) {
        return booksRepository.findBooksByCategory(category);
    }
    public List<Books> getBooksByName(String name){
        return booksRepository.findBooksByName(name);
    }
    @Override
    public List<Books> getAllBooksInBucket(int bucketId){
      return   booksRepository.getAllByBucketId(bucketId);
    }

    @Override
    public void updateBucket(int bucketId, int booksId) {
        booksRepository.booksInserting(bucketId,booksId);
    }

    @Override
    public void deleteFromBucket(int booksId, Bucket bucket) {
        Optional<Books> books = booksRepository.getBooksByBooksIdAndBucket(booksId,bucket);
        if (books.isPresent()){
            books.get().setBucket(null);
            booksRepository.save(books.get());
        }
    }
    @Override
    public String transferFile(MultipartFile multipartFile) {
        String path = FileUtils.getImageFolder();
        try {
            multipartFile.transferTo(new File(path + multipartFile.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return multipartFile.getOriginalFilename();
    }
}
