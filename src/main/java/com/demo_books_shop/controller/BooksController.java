package com.demo_books_shop.controller;

import com.demo_books_shop.exception.BookIsAlreadyExistException;
import com.demo_books_shop.exception.BookNotFoundException;
import com.demo_books_shop.models.Books;
import com.demo_books_shop.models.User;

import com.demo_books_shop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Controller
public class BooksController {
    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String getBooks(Model model){
       List<Books> books = bookService.getAllBooks();
       model.addAttribute("books",books);
       return "books";
    }
    @GetMapping("/myBooks")
    @PreAuthorize("hasAnyAuthority('ROLE_AUTHOR')")
    public String getMyBooks(@AuthenticationPrincipal User user,Model model){
      List<Books> books = bookService.getByAuthor(user.getUserId());
      model.addAttribute("books",books);
        return "myBooks";
    }
    @GetMapping("/books/category/{name}")
    public String getCategoryBooks(@PathVariable("name") String category,Model model){
        List<Books> book = bookService.getBooksByCategory(category);
        model.addAttribute("book",book);
        return "category";
    }

    @GetMapping("/books/searchResult")
    public String getSearchingBooks(@RequestParam String name, Model model ){
        List<Books> search = bookService.getBooksByName(name);
        model.addAttribute("search", search);
        return "searchResult";
    }
    @PostMapping("/saveBook")
    @PreAuthorize("hasAnyAuthority('ROLE_AUTHOR')")
    public RedirectView save(@RequestParam String name,
                       @RequestParam int price,
                       @RequestParam String description,
                       @RequestParam Date dateOfRelease,
                       @RequestParam String category,
                       @RequestParam MultipartFile image,
                       @AuthenticationPrincipal User user,
                       Model model){
        String fileName = bookService.transferFile(image);
        Books book = new Books(name,price,description,dateOfRelease,category,fileName);
        try {
            bookService.saveBook(book,user);
            model.addAttribute("book", book);
        } catch (BookIsAlreadyExistException e) {
            throw new RuntimeException(e);
        }
        return new RedirectView("/books");
    }
    @PostMapping("/deleteBook")
    @PreAuthorize("hasAnyAuthority('ROLE_AUTHOR')")
    public String deleteBook(@RequestParam int bookId, @RequestParam int userId){
      bookService.delete(bookId,userId);
      return "redirect:/books";
    }
    @GetMapping("/book/{id}")
    public String getBook(@PathVariable int id, Model model){
        try {
           Books books = bookService.getBook(id);
           model.addAttribute("book",books);
        } catch (BookNotFoundException e) {
            return "books";
        }
        return "book-viewing";
    }

    @GetMapping("/usersCart")
    public String getBucket(@AuthenticationPrincipal User user,
                            Model model){
        Collection<Books> bucketList = bookService.getAllBooksInBucket(user.getBucket().getId());
        model.addAttribute("books",bucketList);
        return "bucket";
    }
    @PostMapping("/addToBucket")
    public ResponseEntity<Void> addBooksToBucket(
            @RequestParam int booksId,
            @AuthenticationPrincipal User user) {
        int bucketId = user.getBucket().getId();
        bookService.updateBucket(bucketId,booksId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/bucket/deleteBooks")
    public String delete(@RequestParam int booksId,@AuthenticationPrincipal User user){
        bookService.deleteFromBucket(booksId,user.getBucket());
        return "redirect:/usersCart";
    }
}
