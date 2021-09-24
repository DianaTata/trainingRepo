package com.epam.rd.java.basic.finalProject.service.impl;

import com.epam.rd.java.basic.finalProject.dao.UserDao;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.User;
import com.epam.rd.java.basic.finalProject.exception.UserException;
import com.epam.rd.java.basic.finalProject.mapper.Mapper;
import com.epam.rd.java.basic.finalProject.service.UserService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private UserDao userDao;
    private Mapper<UserDTO, User> userMapper;

    public UserServiceImpl(UserDao userDao, Mapper<UserDTO, User> userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO getUserById(int userId) throws UserException {
        User userById = userDao.findUserById(userId);
        LOGGER.warn("started getUserById");
        if (Objects.nonNull(userById)) {
            return userMapper.mapToDTO(userById);
        }
        return null;
    }

    @Override
    public UserDTO insertUser(UserDTO userDto) throws UserException {
        User user = userMapper.mapToEntity(userDto);
        User result = userDao.insertUser(user);
        return userMapper.mapToDTO(result);
    }

    @Override
    public UserDTO getUserByEmail(String email) throws UserException {
        User userByEmail = userDao.findUserByEmail(email);
        LOGGER.warn("started getUserByEmail");
        if (Objects.nonNull(userByEmail)) {
            return userMapper.mapToDTO(userByEmail);
        }
        return null;
    }

    @Override
    public List<UserDTO> getUsers(String role, PaginationDTO paginationDTO) throws UserException {
        List<User> users = userDao.getUsers(role, paginationDTO);
        LOGGER.warn("started get users");
        return users.stream()
                .map(userMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean performBlockUser(int userId, String status) throws UserException {
        LOGGER.warn("Can't performBlockUser");
        return userDao.updateUserStatus(userId, status);
    }

    @Override
    public boolean updateUserNameAndSurname(int userId, String name, String surname) throws UserException {
        LOGGER.warn("Can't performUserNameAndSurname");
        return userDao.updateUserNameAndSurname(userId, name, surname);
    }

    @Override
    public int getUsersCount(String role) throws UserException {
        LOGGER.warn("started get users count");
        return userDao.getUsersNumber(role);
    }
}
