package com.demo_books_shop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn
    private User user;
    @OneToMany( fetch = FetchType.EAGER,cascade = CascadeType.ALL,targetEntity = Books.class)
    private Set<Books>books;

    public Bucket(User user, Set<Books>books){
        this.user = user;
        this.books = books;
    }
}

