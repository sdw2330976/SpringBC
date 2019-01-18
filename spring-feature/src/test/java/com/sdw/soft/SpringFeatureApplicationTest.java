package com.sdw.soft;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Unit test for simple SpringFeatureApplication.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringFeatureApplicationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private MockHttpSession httpSession;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        httpSession = new MockHttpSession();
    }

    @Test
    public void shouldAnswerWithTrue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8).session(httpSession))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print());
    }
}
