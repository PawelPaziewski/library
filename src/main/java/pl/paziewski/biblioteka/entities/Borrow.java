package pl.paziewski.biblioteka.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "borrows")
public class Borrow {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User borrower;
    @ManyToOne
    private Book book;
    private BorrowStatus status;
    private Date borrowDate;
    private Date returnDate;
}
