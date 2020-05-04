package com.mylibrary.book.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Resource class for the Book entity
 *
 * @author Bharadwaj Adepu
 */
@Relation(value = "book", collectionRelation = "books")
@Getter
@ApiModel
public class BookResource extends ResourceSupport {

    @ApiModelProperty(value = "Title of the Book", required = true, example = "Clean Code: A Handbook of Agile Software Craftsmanship")
    @NotNull
    @Size(min = 1, max = 1000)
    private String title;

    @ApiModelProperty(value = "Author of the Book", required = true, example = "John Doe")
    @NotNull
    @Size(min = 1, max = 1000)
    private String author;
    
    @ApiModelProperty(value = "Volume of the Book", required = true, example = "1")
    @NotNull
    private int volume;
    
    @ApiModelProperty(value = "Book ISBN as unique identifier", required = true, example = "3826655486")
    @NotNull
    @Size(min = 1, max = 45)
    private String isbn;

    @JsonCreator
    public BookResource(@JsonProperty("isbn") String isbn, @JsonProperty("title") String title, 
    		@JsonProperty("author") String author, @JsonProperty("volume") int volume) {
        super();
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.volume = volume;
    }

}
