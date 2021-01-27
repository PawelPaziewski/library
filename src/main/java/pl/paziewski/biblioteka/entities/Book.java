package pl.paziewski.biblioteka.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "books")
public class Book {
    @Id
    private String isbn;
    private String title;
    private String author;
    private String publishingHouse;
    private int numberOfPages;
}
