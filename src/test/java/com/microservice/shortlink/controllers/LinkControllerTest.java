package com.microservice.shortlink.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.shortlink.entities.Link;
import com.microservice.shortlink.services.LinkService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LinkController.class)
class LinkControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LinkService linkService;

    @Test
    public void testSubmitLinkShouldReturn400BadRequest() throws Exception {
        var newLink = Link.builder()
                .url("")
                .code("KOGqZi")
                .createdAt(LocalDateTime.now())
                .clickCount(0)
                .build();

        String requestBody = objectMapper.writeValueAsString(newLink);

        mockMvc.perform(post("/shorten").contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testSubmitLinkShouldReturn201Created() throws Exception {
        var newLink = Link.builder()
                .url("http://www.example.com")
                .code("KOGqZi")
                .createdAt(LocalDateTime.now())
                .clickCount(0)
                .build();

        String requestBody = objectMapper.writeValueAsString(newLink);

        mockMvc.perform(post("/shorten").contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testRedirectToLinkShouldReturn404NotFound() throws Exception {
        var code = "KOGqZi";

        mockMvc.perform(get("/{code}", code).contentType("application/json"))
                .andExpect(status().isNotFound())
        .andDo(print());
    }

    @Test
    public void testRedirectToLinkShouldReturn302Found() throws Exception {
        var newLink = Link.builder()
                .url("http://www.example.com")
                .code("KOGqZi")
                .createdAt(LocalDateTime.now())
                .clickCount(0)
                .build();

        var code = "KOGqZi";

        Mockito.when(linkService.findLinkAndIncrementClickCount(code)).thenReturn(newLink);

        mockMvc.perform(get("/{code}", code).contentType("application/json"))
                .andExpect(status().isFound())
                .andDo(print());
    }
}