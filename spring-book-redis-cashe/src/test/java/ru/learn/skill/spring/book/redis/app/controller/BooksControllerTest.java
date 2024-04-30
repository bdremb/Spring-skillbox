package ru.learn.skill.spring.book.redis.app.controller;

import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.learn.skill.spring.book.redis.app.AbstractTest;
import ru.learn.skill.spring.book.redis.app.entity.BookEntity;
import ru.learn.skill.spring.book.redis.app.model.request.BookRequest;

import java.util.Random;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BooksControllerTest extends AbstractTest {

    @Test
    void whenGetAllBooks_thenReturnBookList() throws Exception {
        assertTrue(requireNonNull(redisTemplate.keys("*")).isEmpty());

        String actualResponse = mockMvc.perform(get("/api/v1/books").param("category", "cat2"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(booksService.findAll("cat2"));

        assertFalse(requireNonNull(redisTemplate.keys("*")).isEmpty());
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    void whenGetBookByNameAndAuthor_thenReturnOneBook() throws Exception {
        assertTrue(requireNonNull(redisTemplate.keys("*")).isEmpty());

        String actualResponse = mockMvc.perform(get("/api/v1/books/name/{name}/author/{author}", "bookname3", "author3"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(booksService.findByNameAndAuthor("bookname3", "author3"));

        assertFalse(requireNonNull(redisTemplate.keys("*")).isEmpty());
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    void whenCreateBook_thenCreateBookAndEvictCache() throws Exception {
        assertTrue(requireNonNull(redisTemplate.keys("*")).isEmpty());
        bookRepository.deleteAll();
        assertEquals(0, bookRepository.count());

        mockMvc.perform(get("/api/v1/books").param("category", "cat2"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertFalse(requireNonNull(redisTemplate.keys("*")).isEmpty());

        BookRequest request = new BookRequest();
        request.setInfo("newInfo");
        request.setName("newBookName");
        request.setAuthor("newAuthor");
        request.setCategoryName("newCategory");

        String actualResponse = mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(
                BookEntity.builder()
                        .id(new Random().nextLong())
                        .name("newBookName")
                        .author("newAuthor")
                        .info("newInfo")
                        .build()
        );

        assertTrue(requireNonNull(redisTemplate.keys("*")).isEmpty());
        assertEquals(1, bookRepository.count());
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse, JsonAssert.whenIgnoringPaths("id", "category", "categoryName"));
    }

    @Test
    void whenUpdateBook_thenUpdateBookAndEvictCache() throws Exception {
        assertTrue(requireNonNull(redisTemplate.keys("*")).isEmpty());

        mockMvc.perform(get("/api/v1/books").param("category", "cat2"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertFalse(requireNonNull(redisTemplate.keys("*")).isEmpty());

        BookRequest request = new BookRequest();
        request.setInfo("updatedInfo");
        String actualResponse = mockMvc.perform(put("/api/v1/books/name/{name}/author/{author}", "bookname4", "author3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(
                BookEntity.builder()
                        .id(4L)
                        .name("bookname4")
                        .author("author3")
                        .info("updatedInfo")
                        .build()
        );

        assertTrue(requireNonNull(redisTemplate.keys("*")).isEmpty());
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse, JsonAssert.whenIgnoringPaths("category", "categoryName"));
    }

    @Test
    void whenDeleteById_thenDeleteByIdAndEvictCache() throws Exception {
        assertTrue(requireNonNull(redisTemplate.keys("*")).isEmpty());
        assertEquals(5, bookRepository.count());

        mockMvc.perform(get("/api/v1/books").param("category", "cat2"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertFalse(requireNonNull(redisTemplate.keys("*")).isEmpty());

        mockMvc.perform(delete("/api/v1/books/name/{name}/author/{author}", "bookname3", "author3"))
                .andExpect(status().isNoContent());

        assertTrue(requireNonNull(redisTemplate.keys("*")).isEmpty());
        assertEquals(4, bookRepository.count());
    }

}
