package com.epam.rd.java.basic.finalProject.service.impl;

import com.epam.rd.java.basic.finalProject.dao.UserDao;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.Role;
import com.epam.rd.java.basic.finalProject.entity.User;
import com.epam.rd.java.basic.finalProject.entity.UserStatus;
import com.epam.rd.java.basic.finalProject.exception.UserException;
import com.epam.rd.java.basic.finalProject.mapper.Mapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    public static final int INT = 1;
    public static final String TEST_EMAIL = "email";

    @InjectMocks
    @Spy
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Mock
    private UserDTO userDTO;

    @Mock
    private User user;

    @Mock
    private Mapper<UserDTO, User> userMapper;

    @Mock
    private PaginationDTO paginationDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetUserById() throws UserException {
        when(userDao.findUserById(INT)).thenReturn(user);
        when(userMapper.mapToDTO(user)).thenReturn(userDTO);

        UserDTO actual = userService.getUserById(INT);

        Assert.assertEquals(userDTO, actual);
    }

    @Test
    public void shouldNotGetUserById() throws UserException {
        when(userDao.findUserById(INT)).thenReturn(null);

        UserDTO actual = userService.getUserById(INT);

        Assert.assertNull(actual);
    }

    @Test
    public void shouldInsertUser() throws UserException {
        when(userMapper.mapToEntity(userDTO)).thenReturn(user);
        when(userDao.insertUser(user)).thenReturn(user);
        when(userMapper.mapToDTO(user)).thenReturn(userDTO);

        UserDTO actual = userService.insertUser(userDTO);

        Assert.assertEquals(userDTO, actual);
    }

    @Test(expected = UserException.class)
    public void shouldNotInsertUser() throws UserException {
        when(userMapper.mapToEntity(userDTO)).thenReturn(user);
        when(userDao.insertUser(user)).thenThrow(new UserException());

        userService.insertUser(userDTO);
    }

    @Test
    public void shouldGetUserByEmail() throws UserException {
        when(userDao.findUserByEmail(TEST_EMAIL)).thenReturn(user);
        when(userMapper.mapToDTO(user)).thenReturn(userDTO);

        UserDTO actual = userService.getUserByEmail(TEST_EMAIL);

        Assert.assertEquals(userDTO, actual);
    }

    @Test
    public void shouldNotGetUserByEmail() throws UserException {
        when(userDao.findUserByEmail(TEST_EMAIL)).thenReturn(null);

        UserDTO actual = userService.getUserByEmail(TEST_EMAIL);

        Assert.assertNull(actual);
    }

    @Test
    public void shouldGetUsers() throws UserException {
        when(userDao.getUsers(Role.CLIENT.getName(), paginationDTO)).thenReturn(Collections.singletonList(user));
        when(userMapper.mapToDTO(user)).thenReturn(userDTO);

        List<UserDTO> actual = userService.getUsers(Role.CLIENT.getName(), paginationDTO);

        Assert.assertFalse(actual.isEmpty());
        Assert.assertEquals(userDTO, actual.stream().findFirst().get());
    }

    @Test
    public void shouldNotGetUsers() throws UserException {
        when(userDao.getUsers(Role.CLIENT.getName(), paginationDTO)).thenReturn(Collections.emptyList());
        when(userMapper.mapToDTO(user)).thenReturn(userDTO);

        List<UserDTO> actual = userService.getUsers(Role.CLIENT.getName(), paginationDTO);

        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void shouldBlockUser() throws UserException {
        when(userDao.updateUserStatus(INT, UserStatus.LOCKED.getName())).thenReturn(true);

        boolean actual = userService.performBlockUser(INT, UserStatus.LOCKED.getName());

        Assert.assertTrue(actual);
    }

    @Test
    public void shouldNotBlockUser() throws UserException {
        when(userDao.updateUserStatus(INT, UserStatus.LOCKED.getName())).thenReturn(false);

        boolean actual = userService.performBlockUser(INT, UserStatus.LOCKED.getName());

        Assert.assertFalse(actual);
    }

    @Test
    public void shouldGetUsersCount() throws UserException {
        when(userDao.getUsersNumber(Role.CLIENT.getName())).thenReturn(1);

        int actual = userService.getUsersCount(Role.CLIENT.getName());

        Assert.assertEquals(1, actual);
    }

    @Test(expected = UserException.class)
    public void shouldNotGetUsersCount() {
        when(userDao.getUsersNumber(Role.CLIENT.getName())).thenThrow(new UserException());
        userService.getUsersCount(Role.CLIENT.getName());

    }
}