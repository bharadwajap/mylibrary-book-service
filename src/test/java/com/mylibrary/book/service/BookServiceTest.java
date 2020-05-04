package com.mylibrary.book.service;

import com.mylibrary.book.dao.BookDAO;
import com.mylibrary.book.domain.Book;
import com.mylibrary.book.dto.BookResource;
import com.mylibrary.book.exceptions.ConstraintViolationException;
import com.mylibrary.book.exceptions.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class BookServiceTest {

    private BookService bookService;

    private BookDAO bookDAO;

    @Before
    public void setUp() throws Exception {
        this.bookDAO = Mockito.mock(BookDAO.class);
        this.bookService = new BookService(this.bookDAO);
    }

    @Test
    public void getBookByISBN() throws Exception {
        when(this.bookDAO.findBookByIsbn("1")).thenReturn(Optional.of(new Book("1", "Some title", "John Doe", 2)));

        BookResource bookResource = this.bookService.getBookByISBN("1");
        Assert.assertEquals("1", bookResource.getIsbn());
        Assert.assertEquals("Some title", bookResource.getTitle());

        verify(this.bookDAO, times(1)).findBookByIsbn("1");
        verifyNoMoreInteractions(this.bookDAO);
    }

    @Test
    public void getBookByISBN_NO_RESOURCE_FOUND() throws Exception {
        when(this.bookDAO.findBookByIsbn("1")).thenReturn(Optional.empty());
        try {
            this.bookService.getBookByISBN("1");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ResourceNotFoundException);
        } finally {
            verify(this.bookDAO, times(1)).findBookByIsbn("1");
            verifyNoMoreInteractions(this.bookDAO);
        }
    }

    @Test
    public void testGetBooks() {
        Pageable pageable = new PageRequest(0, 10, null);
        when(this.bookDAO.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(new Book("1", "Some title", "John Doe", 1))));

        Page<BookResource> page = this.bookService.getBooks(pageable);
        Assert.assertEquals(1, page.getTotalElements());
        Assert.assertEquals(1, page.getTotalPages());

        Assert.assertEquals("1", page.getContent().get(0).getIsbn());
        Assert.assertEquals("Some title", page.getContent().get(0).getTitle());

        verify(this.bookDAO, times(1)).findAll(pageable);
        verifyNoMoreInteractions(this.bookDAO);
    }

    @Test
    public void testGetBooks_EMPTY_RESULT_SET() {
        Pageable pageable = new PageRequest(0, 10, null);
        when(this.bookDAO.findAll(pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));

        Page<BookResource> page = this.bookService.getBooks(pageable);
        Assert.assertEquals(0, page.getTotalElements());
        Assert.assertEquals(0, page.getTotalPages());

        verify(this.bookDAO, times(1)).findAll(pageable);
        verifyNoMoreInteractions(this.bookDAO);
    }

    @Test
    public void testCreateBook() {
        when(this.bookDAO.findBookByIsbn("1")).thenReturn(Optional.empty());

        this.bookService.createBook(new BookResource("1", "title", "John Doe", 1));

        verify(this.bookDAO, times(1)).findBookByIsbn("1");
        verify(this.bookDAO, times(1)).save(Mockito.any(Book.class));
        verifyNoMoreInteractions(this.bookDAO);
    }

    @Test
    public void testCreateBook_ALREADY_EXISTS() {
        when(this.bookDAO.findBookByIsbn("1")).thenReturn(Optional.of(new Book("1", "title", "John Doe", 1)));

        try {
            this.bookService.createBook(new BookResource("1", "title", "John Doe", 1));
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ConstraintViolationException);
        } finally {
            verify(this.bookDAO, times(1)).findBookByIsbn("1");
            verifyNoMoreInteractions(this.bookDAO);
        }
    }

    @Test
    public void testUpdateBook() {
        when(this.bookDAO.findBookByIsbn("1")).thenReturn(Optional.of(new Book("1", "title", "John Doe", 1)));

        this.bookService.updateBook(new BookResource("1", "title", "John Doe", 1));

        verify(this.bookDAO, times(1)).findBookByIsbn("1");
        verify(this.bookDAO, times(1)).save(Mockito.any(Book.class));
        verifyNoMoreInteractions(this.bookDAO);
    }

    @Test
    public void testUpdateBook_DOES_NOT_EXIST() {
        when(this.bookDAO.findBookByIsbn("1")).thenReturn(Optional.empty());

        try {
            this.bookService.updateBook(new BookResource("1", "title", "John Doe", 1));
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ResourceNotFoundException);
        } finally {
            verify(this.bookDAO, times(1)).findBookByIsbn("1");
            verifyNoMoreInteractions(this.bookDAO);
        }
    }

    @Test
    public void testDeleteBook() {
        when(this.bookDAO.findBookByIsbn("1")).thenReturn(Optional.of(new Book("1", "title", "John Doe", 1)));

        this.bookService.deleteBook("1");

        verify(this.bookDAO, times(1)).findBookByIsbn("1");
        verify(this.bookDAO, times(1)).delete(Mockito.any(Book.class));
        verifyNoMoreInteractions(this.bookDAO);
    }

    @Test
    public void testDeleteBook_DOES_NOT_EXIST() {
        when(this.bookDAO.findBookByIsbn("1")).thenReturn(Optional.empty());

        try {
            this.bookService.deleteBook("1");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ResourceNotFoundException);
        } finally {
            verify(this.bookDAO, times(1)).findBookByIsbn("1");
            verifyNoMoreInteractions(this.bookDAO);
        }
    }
}