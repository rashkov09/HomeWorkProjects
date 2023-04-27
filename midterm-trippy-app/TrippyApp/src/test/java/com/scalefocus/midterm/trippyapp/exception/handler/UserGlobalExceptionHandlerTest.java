package com.scalefocus.midterm.trippyapp.exception.handler;

import com.scalefocus.midterm.trippyapp.controller.UserController;
import com.scalefocus.midterm.trippyapp.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;


@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class UserGlobalExceptionHandlerTest {
    private MockMvc mockMvc;
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void handleUserAlreadyExists() throws Exception {


    }

    @Test
    void handleControllerValidationException() {
    }

    @Test
    void handleNullPointerException() {
    }

    @Test
    void handleUserNotFoundException() {
    }

    @Test
    void handleNoDataFoundException() {
    }
}