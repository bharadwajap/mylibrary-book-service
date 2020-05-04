package com.mylibrary.book.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * An example of the WebContext aware MVC test for {@link BookController}.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class BookControllerWebContextTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void getBookByISBN() throws Exception {
        createBook();

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("{" + System.lineSeparator() +
                        "  \"isbn\" : \"1\"," + System.lineSeparator() +
                        "  \"title\" : \"Star Wars: Return of the Jedi\"," + System.lineSeparator() +
                        "  \"_links\" : {" + System.lineSeparator() +
                        "    \"self\" : {" + System.lineSeparator() +
                        "      \"href\" : \"http://localhost/books/1\"" + System.lineSeparator() +
                        "    }" + System.lineSeparator() +
                        "  }" + System.lineSeparator() +
                        "}"));
    }

    @Test
    public void getBookByISBN_NOT_FOUND() throws Exception {
        mockMvc.perform(get("/books/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/problem+json"))
                .andExpect(content().string("{" + System.lineSeparator() +
                        "  \"title\" : \"Resource not found\"," + System.lineSeparator() +
                        "  \"detail\" : \"Requested resource cannot be found\"," + System.lineSeparator() +
                        "  \"instance\" : \"/books/2\"," + System.lineSeparator() +
                        "  \"status\" : 404" + System.lineSeparator() +
                        "}"));
    }

    @Test
    public void getBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("{" + System.lineSeparator() +
                        "  \"_links\" : {" + System.lineSeparator() +
                        "    \"self\" : {" + System.lineSeparator() +
                        "      \"href\" : \"http://localhost/books/?page=0&size=10&sort=title,asc\"" + System.lineSeparator() +
                        "    }" + System.lineSeparator() +
                        "  }," + System.lineSeparator() +
                        "  \"page\" : {" + System.lineSeparator() +
                        "    \"pageSize\" : 10," + System.lineSeparator() +
                        "    \"totalElements\" : 0," + System.lineSeparator() +
                        "    \"totalPages\" : 0," + System.lineSeparator() +
                        "    \"currentPage\" : 0" + System.lineSeparator() +
                        "  }" + System.lineSeparator() +
                        "}"));
    }

    @Test
    public void createBook() throws Exception {
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"isbn\":\"1\",\"title\":\"Star Wars: Return of the Jedi\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("{" + System.lineSeparator() +
                        "  \"isbn\" : \"1\"," + System.lineSeparator() +
                        "  \"title\" : \"Star Wars: Return of the Jedi\"," + System.lineSeparator() +
                        "  \"_links\" : {" + System.lineSeparator() +
                        "    \"self\" : {" + System.lineSeparator() +
                        "      \"href\" : \"http://localhost/books/1\"" + System.lineSeparator() +
                        "    }" + System.lineSeparator() +
                        "  }" + System.lineSeparator() +
                        "}"));
    }

    @Test
    public void updateBook() throws Exception {
        createBook();

        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"isbn\":\"1\",\"title\":\"Star Wars: Return of the Jedi\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("{" + System.lineSeparator() +
                        "  \"isbn\" : \"1\"," + System.lineSeparator() +
                        "  \"title\" : \"Star Wars: Return of the Jedi\"," + System.lineSeparator() +
                        "  \"_links\" : {" + System.lineSeparator() +
                        "    \"self\" : {" + System.lineSeparator() +
                        "      \"href\" : \"http://localhost/books/1\"" + System.lineSeparator() +
                        "    }" + System.lineSeparator() +
                        "  }" + System.lineSeparator() +
                        "}"));
    }

    @Test
    public void deleteBook() throws Exception {
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"isbn\":\"2\",\"title\":\"Star Wars: Return of the Jedi 2\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/books/2"))
                .andExpect(status().isNoContent());
    }

}
