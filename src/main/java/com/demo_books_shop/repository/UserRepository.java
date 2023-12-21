package com.demo_books_shop.repository;
import com.demo_books_shop.models.Bucket;
import com.demo_books_shop.models.Role;
import com.demo_books_shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
    @Modifying
    @Query(value = "UPDATE User u  SET  u.active = :active WHERE u.user_id = :id", nativeQuery = true)
     void updateActiveStatus  (@Param("active") boolean active, @Param("id") int id);

}
