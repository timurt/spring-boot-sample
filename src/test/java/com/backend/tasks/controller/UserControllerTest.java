package com.backend.tasks.controller;

import com.backend.tasks.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Implement tests for UserController
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        this.mvc.perform(post("/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"
                        + "\"id\" : 11,"
                        + "\"name\" :\"Tesla\""
                        + "}"));

        this.mvc.perform(post("/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"
                        + "\"id\" : 22,"
                        + "\"name\" :\"Ford\""
                        + "}"));
    }

    @Test
    public void testNonExistingOrganization() throws Exception {
        this.mvc.perform(get("/orgs/999/users"))
                .andDo(print()).andExpect(status().isNotFound());

        this.mvc.perform(post("/orgs/999/users")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNotFound());

        this.mvc.perform(get("/orgs/999/users/123"))
                .andDo(print()).andExpect(status().isNotFound());

        this.mvc.perform(put("/orgs/999/users/123")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNotFound());

        this.mvc.perform(delete("/orgs/999/users/123"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void testGetAll() throws Exception {
        this.mvc.perform(post("/orgs/11/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"
                        + "\"id\" : 1,"
                        + "\"username\" :\"Bruce Wayne\","
                        + "\"password\" :\"qwerty\""
                        + "}"));

        this.mvc.perform(get("/orgs/11/users"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Wayne")));
    }

    @Test
    public void testGetAllEmpty() throws Exception {
        this.mvc.perform(get("/orgs/22/users"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(hasToString("[ ]")));
    }

    @Test
    public void testSave() throws Exception {
        String input = "{\n  "
                + "\"id\" : 2,\n  "
                + "\"username\" : \"Santa\",\n  "
                + "\"password\" : \"hohoho\"\n}";

        this.mvc.perform(post("/orgs/11/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(hasToString(input)));
    }

    @Test
    public void testSaveTwice() throws Exception {
        String input = "{\n  "
                + "\"id\" : 3,\n  "
                + "\"username\" : \"small_pony\",\n  "
                + "\"password\" : \"bigPony!\"\n}";

        this.mvc.perform(post("/orgs/11/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));

        this.mvc.perform(post("/orgs/11/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdate() throws Exception {
        String input = "{\n  "
                + "\"id\" : 3,\n  "
                + "\"username\" : \"Vasya Pupkin\",\n  "
                + "\"password\" : \"zxcv\"\n}";
        String update = "{\n  "
                + "\"username\" : \"Vanya Utkin\",\n  "
                + "\"password\" : \"123\"\n}";

        this.mvc.perform(post("/orgs/11/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));

        this.mvc.perform(put("/orgs/11/users/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(update))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Vanya Utkin")));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        String update = "{\n  "
                + "\"username\" : \"Vanya Utkin\",\n  "
                + "\"password\" : \"123\"\n}";

        this.mvc.perform(put("/orgs/11/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(update))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void testGet() throws Exception {
        String input = "{\n  "
                + "\"id\" : 4,\n  "
                + "\"username\" : \"Lord Vader\",\n  "
                + "\"password\" : \"anyKey\"\n}";

        this.mvc.perform(post("/orgs/11/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));

        this.mvc.perform(get("/orgs/11/users/4"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(hasToString(input)));
    }

    @Test
    public void testGetNotFound() throws Exception {
        this.mvc.perform(get("/orgs/11/users/999"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void testDelete() throws Exception {
        String input = "{\n  "
                + "\"id\" : 5,\n  "
                + "\"username\" : \"Harry Potter\",\n  "
                + "\"password\" : \"potter123\"\n}";

        this.mvc.perform(post("/orgs/11/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));

        this.mvc.perform(delete("/orgs/11/users/5"))
                .andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        this.mvc.perform(delete("/orgs/11/users/999"))
                .andDo(print()).andExpect(status().isNotFound());
    }
}