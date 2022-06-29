package com.parpiiev.time.controllers;

import com.parpiiev.time.utils.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class RegistrationControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void servletContextProvidesRegistrationController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(webApplicationContext.getBean("registrationController")
                instanceof RegistrationController);
    }

    @Test
    void getMethodReturnsRegistrationHTMLViewName() throws Exception {
        mockMvc.perform(get("/registration")).andDo(print())
                .andExpect(view().name("registration"));
    }

    @Test
    void getMethodHasCorrectModelAttributes() throws Exception {
        mockMvc.perform(get("/registration")).andExpect(status().isOk())
                .andExpect(model().attribute("userDTO", new UserDTO()));
    }

    @Test
    void postMethodWithNotValidUser() throws Exception {

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr( "userDTO", new UserDTO())
                        .param("name", "test")
                        .param("login", "")
                        .param("password", "2o")
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("registration"));
    }

    @Test
    void postMethodWithNewValidUser() throws Exception {

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr( "userDTO", new UserDTO())
                        .param("name", "test")
                        .param("login", "u2")
                        .param("password", "2o")
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("userRegistered", true))
                .andExpect(view().name("index"));
    }
}