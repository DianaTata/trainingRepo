package com.epam.rd.java.basic.finalProject.mapper.impl;

import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.dto.RequestDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

    public static final int ID = 1;
    public static final int NUMBER = 11;
    public static final Date DATE = Date.valueOf("2020-02-02");
    public static final String TEST = "test";

    @InjectMocks
    @Spy
    private UserMapper userMapper;

    @Mock
    private User userMock;

    @Mock
    private UserDTO userDTOMock;

    private User user;
    private UserDTO userDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setId(ID);
        user.setName(TEST);
        user.setSurname(TEST);
        user.setPassword(TEST);
        user.setEncrypt(TEST);
        user.setEmail(TEST);
        user.setRole(Role.CLIENT);;
        user.setStatusName(UserStatus.UNLOCKED);
        userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setName(TEST);
        userDTO.setSurname(TEST);
        userDTO.setPassword(TEST);
        userDTO.setEncrypt(TEST);
        userDTO.setEmail(TEST);
        userDTO.setRole(Role.CLIENT);;
        userDTO.setStatusName(UserStatus.UNLOCKED);
    }

    @Test
    public void shouldMapToEntity() {
        when(userDTOMock.getId()).thenReturn(ID);
        when(userDTOMock.getName()).thenReturn(TEST);
        when(userDTOMock.getSurname()).thenReturn(TEST);
        when(userDTOMock.getPassword()).thenReturn(TEST);
        when(userDTOMock.getEncrypt()).thenReturn(TEST);
        when(userDTOMock.getEmail()).thenReturn(TEST);
        when(userDTOMock.getRole()).thenReturn(Role.CLIENT);;
        when(userDTOMock.getStatusName()).thenReturn(UserStatus.UNLOCKED);

        User actual = userMapper.mapToEntity(userDTOMock);

        Assert.assertEquals(user, actual);
    }

    @Test
    public void shouldMapToDTO() {
        when(userMock.getId()).thenReturn(ID);
        when(userMock.getName()).thenReturn(TEST);
        when(userMock.getSurname()).thenReturn(TEST);
        when(userMock.getPassword()).thenReturn(TEST);
        when(userMock.getEncrypt()).thenReturn(TEST);
        when(userMock.getEmail()).thenReturn(TEST);
        when(userMock.getRole()).thenReturn(Role.CLIENT);;
        when(userMock.getStatusName()).thenReturn(UserStatus.UNLOCKED);

        UserDTO actual = userMapper.mapToDTO(userMock);

        Assert.assertEquals(userDTO, actual);
    }
}