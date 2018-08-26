package com.backend.tasks.controller;

import com.backend.tasks.Application;
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
 * Implement tests for OrganizationController
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrganizationControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void testGetAll() throws Exception {
        this.mvc.perform(post("/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"
                        + "\"id\" : 221,"
                        + "\"name\" :\"Apple Inc\""
                        + "}"));

        this.mvc.perform(get("/orgs"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Apple")));
    }

    @Test
    public void testSave() throws Exception {
        String input = "{\n  \"id\" : 1,\n  \"name\" : \"Walmart\"\n}";

        this.mvc.perform(post("/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(hasToString(input)));
    }

    @Test
    public void testSaveTwice() throws Exception {
        String input = "{\n  \"id\" : 3,\n  \"name\" : \"Walmart\"\n}";

        this.mvc.perform(post("/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));

        this.mvc.perform(post("/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdate() throws Exception {
        String input = "{\n  \"id\" : 4,\n  \"name\" : \"Walmart\"\n}";
        String update = "{\n  \"name\" : \"Microsoft\"\n}";

        this.mvc.perform(post("/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));

        this.mvc.perform(put("/orgs/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(update))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Microsoft")));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        String update = "{\n  \"name\" : \"Microsoft\"\n}";

        this.mvc.perform(put("/orgs/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(update))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void testGet() throws Exception {
        String input = "{\n  \"id\" : 5,\n  \"name\" : \"Walmart\"\n}";

        this.mvc.perform(post("/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));

        this.mvc.perform(get("/orgs/5"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(hasToString(input)));
    }

    @Test
    public void testGetNotFound() throws Exception {
        this.mvc.perform(get("/orgs/999"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void testDelete() throws Exception {
        String input = "{\n  \"id\" : 6,\n  \"name\" : \"Walmart\"\n}";

        this.mvc.perform(post("/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input));

        this.mvc.perform(delete("/orgs/6"))
                .andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        this.mvc.perform(delete("/orgs/999"))
                .andDo(print()).andExpect(status().isNotFound());
    }
}