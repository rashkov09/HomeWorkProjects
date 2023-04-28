package com.scalefocus.midterm.trippyapp.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.midterm.trippyapp.model.dto.UserDto;
import com.scalefocus.midterm.trippyapp.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static com.scalefocus.midterm.trippyapp.testutils.User.UserConstants.*;
import static com.scalefocus.midterm.trippyapp.testutils.User.UserFactory.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class UserControllerImplTest {
    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserControllerImpl userController;



    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Test
    public void getAllUsers_success() throws Exception {
        UserDto userDto = getDefaultUserDto();
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(userDto));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(USER_ID))
                .andExpect(jsonPath("$[0].username").value(USER_USERNAME))
                .andExpect(jsonPath("$[0].email").value(USER_EMAIL))
                .andExpect(jsonPath("$[0].firstName").value(USER_FIRSTNAME))
                .andExpect(jsonPath("$[0].lastName").value(USER_LASTNAME))
                .andExpect(jsonPath("$[0].city").value(USER_CITY))
                .andExpect(jsonPath("$[0].joiningDate", instanceOf(List.class)))
                .andExpect(jsonPath("$[0].joiningDate[0]").value(2023))
                .andExpect(jsonPath("$[0].joiningDate[1]").value(4))
                .andExpect(jsonPath("$[0].joiningDate[2]").value(27))
                .andExpect(jsonPath("$[0].joiningDate", hasSize(3)))
                .andExpect(jsonPath("$[0].joiningDate", hasItems(2023, 4, 27)))
                .andExpect(jsonPath("$[0].joiningDate").value(hasToString("[2023,4,27]")))
                .andExpect(jsonPath("$[0].reviews").doesNotExist());
    }

    @Test
    public void getAllUsers_emptyList_success() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0]").doesNotExist());
    }


    @Test
    public void getUserById_noExceptions_success() throws Exception {
        when(userService.getUserById(USER_ID)).thenReturn(getDefaultUserDto());

        mockMvc.perform(get("/users/" + USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value(USER_USERNAME))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.firstName").value(USER_FIRSTNAME))
                .andExpect(jsonPath("$.lastName").value(USER_LASTNAME))
                .andExpect(jsonPath("$.city").value(USER_CITY))
                .andExpect(jsonPath("$.joiningDate", instanceOf(List.class)))
                .andExpect(jsonPath("$.joiningDate[0]").value(2023))
                .andExpect(jsonPath("$.joiningDate[1]").value(4))
                .andExpect(jsonPath("$.joiningDate[2]").value(27))
                .andExpect(jsonPath("$.joiningDate", hasSize(3)))
                .andExpect(jsonPath("$.joiningDate", hasItems(2023, 4, 27)))
                .andExpect(jsonPath("$.joiningDate").value(hasToString("[2023,4,27]")));
    }


    @Test
    public void getUserByUsername_NoException_success() throws Exception {
        when(userService.getUserByUsername(USER_USERNAME)).thenReturn(getDefaultUserDto());

        mockMvc.perform(get("/users?username="+USER_USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value(USER_USERNAME))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.firstName").value(USER_FIRSTNAME))
                .andExpect(jsonPath("$.lastName").value(USER_LASTNAME))
                .andExpect(jsonPath("$.city").value(USER_CITY))
                .andExpect(jsonPath("$.joiningDate", instanceOf(List.class)))
                .andExpect(jsonPath("$.joiningDate[0]").value(2023))
                .andExpect(jsonPath("$.joiningDate[1]").value(4))
                .andExpect(jsonPath("$.joiningDate[2]").value(27))
                .andExpect(jsonPath("$.joiningDate", hasSize(3)))
                .andExpect(jsonPath("$.joiningDate", hasItems(2023, 4, 27)))
                .andExpect(jsonPath("$.joiningDate").value(hasToString("[2023,4,27]")));
    }

    @Test
    public void getUserByEmail_noException_success() throws Exception {
        when(userService.getUserByEmail(USER_EMAIL)).thenReturn(getDefaultUserDto());

        mockMvc.perform(get("/users?email=" + USER_EMAIL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value(USER_USERNAME))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.firstName").value(USER_FIRSTNAME))
                .andExpect(jsonPath("$.lastName").value(USER_LASTNAME))
                .andExpect(jsonPath("$.city").value(USER_CITY))
                .andExpect(jsonPath("$.joiningDate", instanceOf(List.class)))
                .andExpect(jsonPath("$.joiningDate[0]").value(2023))
                .andExpect(jsonPath("$.joiningDate[1]").value(4))
                .andExpect(jsonPath("$.joiningDate[2]").value(27))
                .andExpect(jsonPath("$.joiningDate", hasSize(3)))
                .andExpect(jsonPath("$.joiningDate", hasItems(2023, 4, 27)))
                .andExpect(jsonPath("$.joiningDate").value(hasToString("[2023,4,27]")));
    }

    @Test
    public  void addUser_noException_success() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultUserRequest());

        when(userService.createUser(any())).thenReturn(getDefaultUser().getId());
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/users/1"));
    }

    @Test
    public void updateUser_noException_success() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultUserRequest());

        when(userService.editUser(any(), eq(USER_ID.intValue()))).thenReturn(getDefaultUserDto());

        mockMvc.perform(put("/users/" + USER_ID)
                        .queryParam("returnOld", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(USER_USERNAME))
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.firstName").value(USER_FIRSTNAME))
                .andExpect(jsonPath("$.lastName").value(USER_LASTNAME))
                .andExpect(jsonPath("$.city").value(USER_CITY))
                .andExpect(jsonPath("$.joiningDate", instanceOf(List.class)))
                .andExpect(jsonPath("$.joiningDate[0]").value(2023))
                .andExpect(jsonPath("$.joiningDate[1]").value(4))
                .andExpect(jsonPath("$.joiningDate[2]").value(27))
                .andExpect(jsonPath("$.joiningDate", hasSize(3)))
                .andExpect(jsonPath("$.joiningDate", hasItems(2023, 4, 27)))
                .andExpect(jsonPath("$.joiningDate").value(hasToString("[2023,4,27]")));
    }
}