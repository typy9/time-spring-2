package com.parpiiev.time.controllers;

import com.parpiiev.time.utils.dto.ActivityRequestDTO;
import com.parpiiev.time.utils.dto.UserDTO;
import com.parpiiev.time.utils.dto.UsersActivityDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
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
class UserMenuControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void servletContextProvidesUserMenuController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(webApplicationContext.getBean("userMenuController")
                instanceof UserMenuController);
    }

    @Test
    void showPageGetMethodReturnsUserMenuHTMLViewName() throws Exception {
        mockMvc.perform(get("/user-menu")
                        .sessionAttr("user", new UserDTO())
                        .param("name", "petrov")
                        .param("login", "u")
                        .param("password", "2")
                        .param("role", "user")
                )
                .andDo(print())
                .andExpect(view().name("user-menu"));
    }

    @Test
    void showPageGetMethodHasCorrectUserInSession() throws Exception {

        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(get("/user-menu").session(session)
                        .sessionAttr("user", new UserDTO())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userDTO"));
    }

    @Test
    void userUpdateTimePageGetMethodReturnsTimeUpdateHTMLViewName() throws Exception {
        mockMvc.perform(get("/user-menu/time-edit/{id}/{activityId}", 2, 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/user-menu/time-update"));
    }

    @Test
    void updateTimePostMethodFunctionsCorrectly() throws Exception {
        MockHttpSession session = new MockHttpSession();

        UserDTO user = new UserDTO();
        user.setUser_id(2);
        user.setLogin("u");
        user.setPassword("2");
        user.setRole("user");

        session.setAttribute("user", user);
        int activityId = 1;

        mockMvc.perform(post("/user-menu/time-edit/{id}", activityId).session(session)
                        .sessionAttr( "usersActivityDTO", new UsersActivityDTO())
                        .param("time", "100")
                        .param("user_id", "2")
                        .param("activity_id", "1")
                ).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user-menu"));
    }

    @Test
    void showRequestFormGetMethodReturnsCorrectHTMLViewName() throws Exception {
        mockMvc.perform(get("/user-menu/add-request-form/{id}", 2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("activity"))
                .andExpect(model().attributeExists("activities"))
                .andExpect(model().attributeExists("userId"))
                .andExpect(view().name("user-menu/add-request-form"));
    }

    @Test
    void addRequestFormPostMethodFunctionsCorrectly() throws Exception {

        MockHttpSession session = new MockHttpSession();

        UserDTO user = new UserDTO();
        user.setLogin("u");
        user.setPassword("2");

        session.setAttribute("user", user);

        mockMvc.perform(post("/user-menu/add-request-form").session(session)
                .sessionAttr( "activityRequestDTO", new ActivityRequestDTO())
                        .param("activityId", "1")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user-menu"));
    }
}