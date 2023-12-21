package com.demo_books_shop.repository;

import com.demo_books_shop.models.Books;
import com.demo_books_shop.models.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Books,Integer> {
    List<Books> findBooksByCategory(String category);
    List<Books> findBooksByName(String name);
    @Modifying
    @Query(value = "DELETE FROM books b WHERE b.author_user_id=:userId and b.books_id= :booksId",
            nativeQuery = true)
    void deleteBooksByBooksIdAndAuthorId(@Param("booksId") int booksId, @Param("userId") int userId);

    List<Books>getAllByBucketId(int bucketId);
    Optional<Books> getBooksByBooksIdAndBucket(int booksId, Bucket bucket);
    @Modifying
    @Query(value = "UPDATE books b SET b.bucket_id=:bucketId WHERE b.books_id=:booksId",nativeQuery = true)
    void booksInserting(@Param("bucketId")int bucketId,@Param("booksId")int booksId);

    List<Books>findBooksByAuthorUserId(int authorId);
}
