package com.mylibrary.book.rest;

import com.mylibrary.book.dto.BookResource;
import com.mylibrary.book.dto.ProblemDetail;
import com.mylibrary.book.rest.assemblers.BookResourceAssembler;
import com.mylibrary.book.service.BookService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Controller class for the Books API.
 *
 * @author Bharadwaj Adepi
 */
@RestController
@ExposesResourceFor(BookResource.class)
@RequestMapping("/mylibrary/books")
@Api(value = "Books", description = "Book API")
public class BookController {

    private final BookService bookService;

    private final BookResourceAssembler bookResourceAssembler;

    /**
     * Instantiates new instance of {@link BookService}
     *
     * @param bookService {@link BookService}
     */
    @Autowired
    public BookController(BookService bookService, BookResourceAssembler bookResourceAssembler) {
        this.bookService = bookService;
        this.bookResourceAssembler = bookResourceAssembler;
    }

    @ApiOperation(
            value = "Retrieve a Book resource by identifier",
            notes = "Make a GET request to retrieve a book by a given identifier",
            response = BookResource.class,
            httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved", response = BookResource.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ProblemDetail.class),
            @ApiResponse(code = 403, message = "This operation is forbidden for this user", response = ProblemDetail.class),
            @ApiResponse(code = 404, message = "Book with the given identifier is not found", response = ProblemDetail.class),
            @ApiResponse(code = 500, message = "Unexpected Internal Error", response = ProblemDetail.class)})
    @RequestMapping(value = "/{isbn}", method = RequestMethod.GET, produces = HAL_JSON_VALUE)
    public HttpEntity<BookResource> getBookByISBN(@PathVariable(value = "isbn") String isbn) {

        BookResource book = this.bookResourceAssembler.toResource(this.bookService.getBookByISBN(isbn));

        return ResponseEntity.ok(book);
    }

    @ApiOperation(
            value = "Retrieve all Books",
            notes = "Make a GET request to retrieve a page of books",
            response = PagedResources.class,
            httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved", response = PagedResources.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ProblemDetail.class),
            @ApiResponse(code = 403, message = "This operation is forbidden for this user", response = ProblemDetail.class),
            @ApiResponse(code = 404, message = "Book with the given identifier is not found", response = ProblemDetail.class),
            @ApiResponse(code = 500, message = "Unexpected Internal Error", response = ProblemDetail.class)})
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PagedResources<BookResource>> getBooks(@PageableDefault(sort = "title", direction = Sort.Direction.ASC) final Pageable pageable,
                                                                 final PagedResourcesAssembler<BookResource> pagedResourcesAssembler) {

        return ResponseEntity.ok(pagedResourcesAssembler.toResource(this.bookService.getBooks(pageable),
                this.bookResourceAssembler,
                enhanceSelfLink(pageable, linkTo(ControllerLinkBuilder.methodOn(BookController.class)
                        .getBooks(pageable, pagedResourcesAssembler)).withSelfRel())));
    }

    @ApiOperation(
            value = "Create a book",
            notes = "Make a POST request to register new book",
            response = BookResource.class,
            code = 201,
            httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created", response = BookResource.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ProblemDetail.class),
            @ApiResponse(code = 403, message = "This operation is forbidden for this user", response = ProblemDetail.class),
            @ApiResponse(code = 404, message = "Book with the given identifier is not found", response = ProblemDetail.class),
            @ApiResponse(code = 500, message = "Unexpected Internal Error", response = ProblemDetail.class)})
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaTypes.HAL_JSON_VALUE)
    public HttpEntity<BookResource> createBook(@Validated @RequestBody final BookResource bookResource) {

        BookResource book = this.bookResourceAssembler.toResource(this.bookService.createBook(bookResource));

        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "Update a book",
            notes = "Make a PUT request to update existing book",
            response = BookResource.class,
            httpMethod = "PUT")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated", response = BookResource.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = ProblemDetail.class),
            @ApiResponse(code = 403, message = "This operation is forbidden for this user", response = ProblemDetail.class),
            @ApiResponse(code = 404, message = "Book with the given identifier is not found", response = ProblemDetail.class),
            @ApiResponse(code = 500, message = "Unexpected Internal Error", response = ProblemDetail.class)})
    @RequestMapping(value = "/{isbn}", method = RequestMethod.PUT, produces = MediaTypes.HAL_JSON_VALUE)
    public HttpEntity<BookResource> updateBook(@Validated @RequestBody final BookResource bookResource) {

        BookResource book = this.bookResourceAssembler.toResource(this.bookService.updateBook(bookResource));

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Delete a book",
            notes = "Make a DELETE request to delete existing book",
            code = 204,
            httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted"),
            @ApiResponse(code = 401, message = "Unauthorized", response = ProblemDetail.class),
            @ApiResponse(code = 403, message = "This operation is forbidden for this user", response = ProblemDetail.class),
            @ApiResponse(code = 404, message = "Book with the given identifier is not found", response = ProblemDetail.class),
            @ApiResponse(code = 500, message = "Unexpected Internal Error", response = ProblemDetail.class)})
    @RequestMapping(value = "/{isbn}", method = RequestMethod.DELETE)
    public HttpEntity<Void> deleteBookByISBN(@PathVariable(value = "isbn") String isbn) {

        this.bookService.deleteBook(isbn);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Link enhanceSelfLink(Pageable pageable, Link selfLink) {
        //this version of Spring has a bug, so selfLink must be enhanced with page, size and sort parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(selfLink.expand().getHref());
        new HateoasPageableHandlerMethodArgumentResolver().enhance(builder, null, pageable);
        return new Link(builder.build().toString());
    }

}
