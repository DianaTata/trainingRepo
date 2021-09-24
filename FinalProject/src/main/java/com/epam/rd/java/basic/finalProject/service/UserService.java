package com.epam.rd.java.basic.finalProject.service;

import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.exception.UserException;

import java.util.List;

/**
 * class to work with users operations
 */
public interface UserService {

    /**
     * method to retrieve user by user id
     *
     * @param userId - user id
     * @return userDTO
     */
    UserDTO getUserById(int userId) throws UserException;

    /**
     * method to make user
     *
     * @param user - object user
     * @return userDTO
     * @throws UserException - will catch by userServlet
     */
    UserDTO insertUser(UserDTO user) throws UserException;

    /**
     * method to retrieve user by email
     *
     * @param email - user email
     * @return userDTO
     */
    UserDTO getUserByEmail(String email) throws UserException;

    /**
     * method to retrieve list users by role
     *
     * @param role          - user role
     * @param paginationDTO - pagination
     * @return -list users
     */
    List<UserDTO> getUsers(String role, PaginationDTO paginationDTO) throws UserException;

    /**
     * method to perform user status
     *
     * @param userId - user id
     * @param status - user status
     * @return result operation performBlockUser
     */
    boolean performBlockUser(int userId, String status) throws UserException;

    /**
     * method to perform users name and status
     *
     * @param userId  - user id
     * @param name    - user name
     * @param surname - user surname
     * @return result operation performUserNameAndSurname
     */
    boolean updateUserNameAndSurname(int userId, String name, String surname) throws UserException;

    /**
     * method to retrieve users counts
     *
     * @param role - role
     * @return amount users counts
     */
    int getUsersCount(String role) throws UserException;
}
