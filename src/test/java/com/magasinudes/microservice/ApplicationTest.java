package com.magasinudes.microservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void rootTest() throws Exception {
//        mvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("<h1>Ressource index!<h1>"));
    }

    @Test
    public void healtTest() throws Exception {
//        mvc.perform(get("/health"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("ok"));
    }
}
