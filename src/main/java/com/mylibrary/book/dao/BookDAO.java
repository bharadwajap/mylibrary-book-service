package com.mylibrary.book.dao;

import com.mylibrary.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * JPA Repository for {@link Book} entity
 *
 * @author Vadym Lotar
 * @see JpaRepository
 */
public interface BookDAO extends JpaRepository<Book, String> {

    /**
     * Loads book by isbn.
     *
     * @return {@link Optional <Book>}
     */
    Optional<Book> findBookByIsbn(String isbn);

}
