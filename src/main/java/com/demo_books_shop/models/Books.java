package com.demo_books_shop.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int booksId;
    private String name;
    private int price;
    @Column(columnDefinition = "text")
    private String description;
    private Date dateOfRelease;
    private String category;
    private String image;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User author;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Bucket bucket;

    public Books(String name, int price, String description, Date dateOfRelease, String category,String image){
        this.name = name;
        this.price = price;
        this.description = description;
        this.dateOfRelease = dateOfRelease;
        this.category = category;
        this.image = image;
    }
}
