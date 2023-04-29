package com.scalefocus.midterm.trippyapp.service.impl;

import com.scalefocus.midterm.trippyapp.controller.request.UserRequest;
import com.scalefocus.midterm.trippyapp.exception.MissingRequestFieldsException;
import com.scalefocus.midterm.trippyapp.exception.NoDataFoundException;
import com.scalefocus.midterm.trippyapp.exception.UserExceptions.UserAlreadyExistsException;
import com.scalefocus.midterm.trippyapp.exception.UserExceptions.UserNotFoundException;
import com.scalefocus.midterm.trippyapp.mapper.UserMapper;
import com.scalefocus.midterm.trippyapp.model.User;
import com.scalefocus.midterm.trippyapp.model.dto.UserDto;
import com.scalefocus.midterm.trippyapp.repository.CustomRepository;
import com.scalefocus.midterm.trippyapp.util.ObjectChecker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static com.scalefocus.midterm.trippyapp.testutils.User.UserConstants.*;
import static com.scalefocus.midterm.trippyapp.testutils.User.UserFactory.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    private CustomRepository<User> userCustomRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ObjectChecker<UserRequest> objectChecker;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getAllUsers_noException_success() {
        when(userCustomRepository.getAll()).thenReturn(Collections.singletonList(getDefaultUser()));
        when(userMapper.mapToDto(any())).thenReturn(getDefaultUserDto());
        List<UserDto> users = userService.getAllUsers();
        Assert.assertEquals(USER_ID, users.get(0).getId());
        Assert.assertEquals(USER_USERNAME, users.get(0).getUsername());
        Assert.assertEquals(USER_EMAIL, users.get(0).getEmail());
        Assert.assertEquals(USER_FIRSTNAME, users.get(0).getFirstName());
        Assert.assertEquals(USER_LASTNAME, users.get(0).getLastName());
        Assert.assertEquals(USER_CITY, users.get(0).getCity());
        Assert.assertEquals(USER_JOIN_DATE, users.get(0).getJoiningDate());
        Assert.assertTrue(users.get(0).getReviewList().isEmpty());
    }

    @Test
    public void getUserById_noException_success() {
        when(userCustomRepository.getById(any())).thenReturn(getDefaultUser());
        when(userMapper.mapToDto(any())).thenReturn(getDefaultUserDto());
        UserDto user = userService.getUserById(any());
        Assert.assertEquals(USER_ID, user.getId());
        Assert.assertEquals(USER_USERNAME, user.getUsername());
        Assert.assertEquals(USER_EMAIL, user.getEmail());
        Assert.assertEquals(USER_FIRSTNAME, user.getFirstName());
        Assert.assertEquals(USER_LASTNAME, user.getLastName());
        Assert.assertEquals(USER_CITY, user.getCity());
        Assert.assertEquals(USER_JOIN_DATE, user.getJoiningDate());
        Assert.assertTrue(user.getReviewList().isEmpty());
    }

    @Test
    public void getUserByEmail_noException_success() {
        when(userCustomRepository.getByEmail(any())).thenReturn(getDefaultUser());
        when(userMapper.mapToDto(any())).thenReturn(getDefaultUserDto());
        UserDto user = userService.getUserByEmail(any());
        Assert.assertEquals(USER_ID, user.getId());
        Assert.assertEquals(USER_USERNAME, user.getUsername());
        Assert.assertEquals(USER_EMAIL, user.getEmail());
        Assert.assertEquals(USER_FIRSTNAME, user.getFirstName());
        Assert.assertEquals(USER_LASTNAME, user.getLastName());
        Assert.assertEquals(USER_CITY, user.getCity());
        Assert.assertEquals(USER_JOIN_DATE, user.getJoiningDate());
        Assert.assertTrue(user.getReviewList().isEmpty());
    }

    @Test
    public void getUserByUsername_noException_success() {
        when(userCustomRepository.getByUsername(any())).thenReturn(getDefaultUser());
        when(userMapper.mapToDto(any())).thenReturn(getDefaultUserDto());
        UserDto user = userService.getUserByUsername(any());
        Assert.assertEquals(USER_ID, user.getId());
        Assert.assertEquals(USER_USERNAME, user.getUsername());
        Assert.assertEquals(USER_EMAIL, user.getEmail());
        Assert.assertEquals(USER_FIRSTNAME, user.getFirstName());
        Assert.assertEquals(USER_LASTNAME, user.getLastName());
        Assert.assertEquals(USER_CITY, user.getCity());
        Assert.assertEquals(USER_JOIN_DATE, user.getJoiningDate());
        Assert.assertTrue(user.getReviewList().isEmpty());
    }

    @Test
    public void checkIfUserWithEmailExists_noException_returnsTrue() {
        when(userCustomRepository.getByEmail(any())).thenReturn(getDefaultUser());
        User user = getDefaultUser();
        assertTrue(userService.userExists(user));
    }

    @Test
    public void checkIfUserWithUsernameExists_noException_returnsTrue() {
        when(userCustomRepository.getByUsername(any())).thenReturn(getDefaultUser());
        User user = getDefaultUser();
        assertTrue(userService.userExists(user));
    }

    @Test
    public void createUser_noException_returnsId() throws SQLException {
        when(userCustomRepository.add(any())).thenReturn(USER_ID);
        when(userMapper.mapFromRequest(any())).thenReturn(getDefaultUser());
       Long id = userService.createUser(getDefaultUserRequest());
        Assert.assertEquals(USER_ID,id);
    }

    @Test
    public void updateUser_noException_returnsUpdatedUserDto() throws SQLException {
        User updatedUser = getDefaultUser();
        updatedUser.setFirstName("Test2");
        updatedUser.setLastName("Test3");
        updatedUser.setUsername("tamantam");
        updatedUser.setEmail("tamantam@asdadd.com");
        UserDto updatedUserDto = getDefaultUserDto();
        updatedUserDto.setFirstName("Test2");
        updatedUserDto.setLastName("Test3");
        updatedUserDto.setUsername("tamantam");
        updatedUserDto.setEmail("tamantam@asdadd.com");
        when(userMapper.mapFromRequest(any())).thenReturn(updatedUser);
        when(userCustomRepository.edit(any(), any())).thenReturn(updatedUser);
        when(userMapper.mapToDto(any())).thenReturn(updatedUserDto);
        UserDto userDto = userService.editUser(getDefaultUserRequest(), USER_ID.intValue());
        assertEquals(userDto.getFirstName(), updatedUser.getFirstName());
        assertEquals(userDto.getEmail(), updatedUser.getEmail());
        assertEquals(userDto.getLastName(), updatedUser.getLastName());
        assertEquals(userDto.getEmail(), updatedUser.getEmail());
    }

    @Test(expected = NoDataFoundException.class)
    public void getAllUsers_withException_throws() {
        when(userCustomRepository.getAll()).thenReturn(Collections.emptyList());
        userService.getAllUsers();
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserById_withException_throws() {
        when(userCustomRepository.getById(any())).thenReturn(null);
        userService.getUserById(any());
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserByUsername_withException_throws() {
        when(userCustomRepository.getByUsername(any())).thenReturn(null);
        userService.getUserByUsername(any());
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserByEmail_withException_throws() {
        when(userCustomRepository.getByEmail(any())).thenReturn(null);
        userService.getUserByEmail(any());
    }

    @Test(expected = MissingRequestFieldsException.class)
    public void createUserMissingEmail_withException_throws() {
        when(objectChecker.checkForMissingFields(any())).thenThrow(new MissingRequestFieldsException());
        UserRequest userRequest = getDefaultUserRequest();
        userRequest.setEmail(null);
        userService.createUser(userRequest);
    }

    @Test(expected = MissingRequestFieldsException.class)
    public void createUserWithMissingUsername_withException_throws() {
        when(objectChecker.checkForMissingFields(any())).thenThrow(new MissingRequestFieldsException());
        UserRequest userRequest = getDefaultUserRequest();
        userRequest.setUsername(null);
        userService.createUser(userRequest);
    }

    @Test(expected = MissingRequestFieldsException.class)
    public void createUserWithMissingFirstName_withException_throws() {
        when(objectChecker.checkForMissingFields(any())).thenThrow(new MissingRequestFieldsException());
        UserRequest userRequest = getDefaultUserRequest();
        userRequest.setFirstName(null);
        userService.createUser(userRequest);
    }

    @Test(expected = MissingRequestFieldsException.class)
    public void createUserWithMissingLastName_withException_throws() {
        when(objectChecker.checkForMissingFields(any())).thenThrow(new MissingRequestFieldsException());
        UserRequest userRequest = getDefaultUserRequest();
        userRequest.setLastName(null);
        userService.createUser(userRequest);
    }

    @Test(expected = MissingRequestFieldsException.class)
    public void createUserWithMissingCity_withException_throws() {
        when(objectChecker.checkForMissingFields(any())).thenThrow(new MissingRequestFieldsException());
        UserRequest userRequest = getDefaultUserRequest();
        userRequest.setCity(null);
        userService.createUser(userRequest);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void createUserWithAlreadyExistingEmail_withException_throws() {
        when(objectChecker.checkForMissingFields(any())).thenReturn(false);
        when(userCustomRepository.getByEmail(any())).thenReturn(getDefaultUser());
        when(userMapper.mapFromRequest(any())).thenReturn(getDefaultUser());
        userService.createUser(getDefaultUserRequest());
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void createUserWithAlreadyExistingUsername_withException_throws() {
        when(userCustomRepository.getByUsername(any())).thenReturn(getDefaultUser());
        when(userMapper.mapFromRequest(any())).thenReturn(getDefaultUser());
        when(objectChecker.checkForMissingFields(any())).thenReturn(false);
        userService.createUser(getDefaultUserRequest());
    }

}