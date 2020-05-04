package com.mylibrary.book.rest.assemblers;

import com.mylibrary.book.dto.BookResource;
import com.mylibrary.book.rest.BookController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Allows to construct the response according to API Guidelines and HAL format:
 *
 */
@Component
public class BookResourceAssembler extends AbstractResourceAssembler<BookResource, BookResource> {

    /**
     * Creates a new {@link ResourceAssemblerSupport} using the given controller class and resource type.
     */
    public BookResourceAssembler() {
        super(BookController.class, BookResource.class);
    }

    /**
     * Allows to append HAL metadata, like self link, links to other resources, etc.
     *
     * @param entity {@link BookResource}
     * @return adjusted {@link BookResource}
     */
    public BookResource toResource(BookResource entity) {
        // Add self link
        entity.add(linkTo(methodOn(BookController.class).getBookByISBN(entity.getIsbn())).withSelfRel());

        return entity;
    }

}
