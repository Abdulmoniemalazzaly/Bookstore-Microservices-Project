package com.bookstore.book.model;

import com.bookstore.commons.model.jpa.AuditorEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends AuditorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Min(value = 1 , message = "Rating couldn't be less than 1")
    @Max(value = 5 , message = "Rating couldn't be more than 5")
    private Float rating;
    @Column(nullable = false)
    private String review;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id" , nullable = false)
    @JsonBackReference
    private Book book;
}
