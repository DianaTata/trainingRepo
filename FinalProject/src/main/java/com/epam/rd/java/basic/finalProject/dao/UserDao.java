package com.epam.rd.java.basic.finalProject.dao;

import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.User;
import com.epam.rd.java.basic.finalProject.exception.UserException;

import java.util.List;

public interface UserDao {

    User insertUser(User user) throws UserException;

    User findUserById(int id) throws UserException;

    User findUserByEmail(String email) throws UserException;

    List<User> getUsers(String role, PaginationDTO paginationDTO) throws UserException;

    boolean updateUserStatus(int userId, String status) throws UserException;

    int getUsersNumber(String role) throws UserException;

    boolean updateUserNameAndSurname(int userId, String name, String surname) throws UserException ;
}
