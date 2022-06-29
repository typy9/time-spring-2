package com.parpiiev.time;

import com.parpiiev.time.controllers.AdminMenuController;
import com.parpiiev.time.controllers.IndexController;
import com.parpiiev.time.controllers.RegistrationController;
import com.parpiiev.time.controllers.UserMenuController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TimeApplicationTests {

    @Autowired
    IndexController indexController;
    @Autowired
    RegistrationController registrationController;
    @Autowired
    AdminMenuController adminMenuController;
    @Autowired
    UserMenuController userMenuController;


    @Test
    void contextLoads() throws Exception{
        assertThat(indexController).isNotNull();
        assertThat(registrationController).isNotNull();
        assertThat(adminMenuController).isNotNull();
        assertThat(userMenuController).isNotNull();
    }

}
