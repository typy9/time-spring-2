package com.parpiiev.time.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.parpiiev.time.controllers.IndexController;
import com.parpiiev.time.utils.dto.UserDTO;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@AutoConfigureMockMvc
class IndexControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void servletContextProvidesIndexController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(webApplicationContext.getBean("indexController")
                instanceof IndexController);
    }

    @Test
    void getMethodReturnsIndexHTMLViewName() throws Exception {
        mockMvc.perform(get("/")).andDo(print())
                .andExpect(view().name("index"));
    }

    @Test
    void getMethodHasCorrectModelAttributes() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(model().attribute("userDTO", new UserDTO()));
    }

    @Test
    void postMethodExists() throws Exception {
        mockMvc.perform(post("/")).andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void postMethodWithNullUser() throws Exception {

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr( "userDTO", new UserDTO())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:index"));
    }

    @Test
    void postMethodWithValidUser() throws Exception {

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr( "userDTO", new UserDTO())
                        .param("login", "u")
                        .param("password", "2")
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user-menu"));
    }
}
