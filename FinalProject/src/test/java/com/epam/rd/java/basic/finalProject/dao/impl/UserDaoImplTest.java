package com.epam.rd.java.basic.finalProject.dao.impl;

import com.epam.rd.java.basic.finalProject.dao.UserDao;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.User;
import com.epam.rd.java.basic.finalProject.exception.UserException;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImplTest {

    public static final int NULL = 0;
    private UserDao userDao = new UserDaoImpl();

    @Test
    public void shouldFindUserByEmail() throws UserException {
        User actual = userDao.findUserByEmail("test@test.com");

        Assert.assertNotNull(actual);

    }

    @Test
    public void shouldFindUserById() throws UserException {
        User actual = userDao.findUserById(20);

        Assert.assertNotNull( actual);
    }

    @Test
    public void shouldGetUsers() throws UserException {
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setAmountOfItems(5);
        paginationDTO.setOffset(0);
        List<User> actual = userDao.getUsers("client", paginationDTO);

        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void shouldUpdateUserStatus() throws SQLException, UserException {
        boolean actual = userDao.updateUserStatus(16, "locked");
        Assert.assertTrue(actual);
    }

    @Test
    public void shouldGetUsersNumber() throws UserException {
        int actual = userDao.getUsersNumber("client");

        Assert.assertTrue(actual != NULL);
    }

}