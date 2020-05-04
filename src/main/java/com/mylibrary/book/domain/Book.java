package com.mylibrary.book.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Domain class which represent Book entity in data storage
 *
 * @author Bharadwaj Adepu
 */
@Entity
@Table(name = "books")
@Data
public class Book implements Serializable {

    private static final long serialVersionUID = -1727518477635717091L;

    @Id
    private String isbn;

    @Column
    private String title;

    @Column
    private String author;
    
    @Column
    private int volume;
    /**
     * Must be present because JPA is using default constructor
     */
    @SuppressWarnings("unused")
    public Book() {
    }

    public Book(String isbn, String title, String author, int volume) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.volume = volume;
    }
}
