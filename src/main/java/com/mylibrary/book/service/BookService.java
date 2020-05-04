package com.mylibrary.book.service;

import com.mylibrary.book.dao.BookDAO;
import com.mylibrary.book.domain.Book;
import com.mylibrary.book.dto.BookResource;
import com.mylibrary.book.exceptions.ConstraintViolationException;
import com.mylibrary.book.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Simple class used as an example.
 * TODO: must be removed
 *
 * @author Vadym Lotar
 * @since 0.1.0.RELEASE
 */
@Service
public class BookService {

    private BookDAO bookDAO;

    /**
     * Instantiates a new {@link BookService}
     *
     * @param bookDAO the {@link BookDAO}
     */
    @Autowired
    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    /**
     * Allows to retrieve the book by ISBN
     *
     * @param isbn book identifier
     * @return {@link com.mylibrary.book.dto.BookResource}
     */

    public BookResource getBookByISBN(String isbn) {
        Book book = this.bookDAO.findBookByIsbn(isbn).orElseThrow(() -> new ResourceNotFoundException("Book", isbn));
        return new BookResource(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getVolume());
    }

    /**
     * Gets a page with books.
     *
     * @param pageable the {@link Pageable} pageable
     * @return page of books
     */
    public Page<BookResource> getBooks(Pageable pageable) {
        return convert(this.bookDAO.findAll(pageable), pageable);
    }

    /**
     * Creates a book.
     *
     * @param bookResource the {@link BookResource}
     * @return the {@link BookResource}
     */
    public BookResource createBook(BookResource bookResource) {
        if (this.bookDAO.findBookByIsbn(bookResource.getIsbn()).isPresent()) {
            throw new ConstraintViolationException("Book with such ISBN already exists");
        }

        Book book = new Book(bookResource.getIsbn(), bookResource.getTitle(), 
        		bookResource.getAuthor(), bookResource.getVolume());
        this.bookDAO.save(book);
        return bookResource;
    }

    /**
     * Updates a book.
     *
     * @param bookResource the {@link BookResource}
     * @return the {@link BookResource}
     */
    public BookResource updateBook(BookResource bookResource) {
        Book book = loadBookByISBN(bookResource.getIsbn());
        book.setTitle(bookResource.getTitle());

        this.bookDAO.save(book);
        return bookResource;
    }

    /**
     * Deletes a book.
     *
     * @param isbn the isbn
     */
    public void deleteBook(String isbn) {
        this.bookDAO.delete(loadBookByISBN(isbn));
    }

    private Book loadBookByISBN(String isbn) {
        return this.bookDAO.findBookByIsbn(isbn).orElseThrow(() -> new ResourceNotFoundException("Book", isbn));
    }

    private Page<BookResource> convert(Page<Book> page, Pageable pageable) {
        return new PageImpl<>(page.getContent().stream()
                .map(this::convert).collect(Collectors.toList()), pageable, page.getTotalElements());
    }

    private BookResource convert(Book book) {
        return new BookResource(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getVolume());
    }
}
