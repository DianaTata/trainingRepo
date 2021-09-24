package com.epam.rd.java.basic.finalProject.dao.impl;

import com.epam.rd.java.basic.finalProject.dao.UserDao;
import com.epam.rd.java.basic.finalProject.db.DBManager;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Role;
import com.epam.rd.java.basic.finalProject.entity.User;
import com.epam.rd.java.basic.finalProject.entity.UserStatus;
import com.epam.rd.java.basic.finalProject.exception.UserException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    private static final String INSERT_USER = "INSERT INTO users"
            + "(id, name, surname, email, password, encrypt, role, statusName) VALUES "
            + "(DEFAULT, ?, ?, ?, ?, ?, DEFAULT, DEFAULT)";
    private static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String FIND_ALL_USERS = "SELECT * FROM users where role = ? LIMIT ? OFFSET ?";
    private static final String UPDATE_USER_STATUS = "UPDATE users SET statusName = ? WHERE id = ?";
    private static final String UPDATE_USER_NAME_SURNAME = "UPDATE users SET name = ?, surname = ? WHERE id = ?";
    private static final String GET_USERS_NUMBER = "SELECT count(*) FROM users where role = ?";
    private static final int COLUMN_INDEX_ONE = 1;

    public User insertUser(User user) throws UserException {
        LOGGER.debug("UserDao insertDao started");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER);
            preparedStatement.setString(k++, user.getName());
            preparedStatement.setString(k++, user.getSurname());
            preparedStatement.setString(k++, user.getEmail());
            preparedStatement.setString(k++, user.getPassword());
            preparedStatement.setString(k, user.getEncrypt());
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(COLUMN_INDEX_ONE);
                    user.setId(id);
                }
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Cant insert User", sqlException);
            throw new UserException("Cant insert User");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("UserDAO insertUser finished");
        }
        return user;
    }

    @Override
    public User findUserById(int id) throws UserException {
        LOGGER.debug("findUserById started");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
            preparedStatement.setInt(k, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = fillUser(resultSet);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Cant findUserById User", sqlException);
            throw new UserException("This findUserById");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("UserDAO findUserById finished");
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws UserException {
        LOGGER.debug("findUserByEmail started");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL);
            preparedStatement.setString(k, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = fillUser(resultSet);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Cant findUserByEmail User", sqlException);
            throw new UserException("This findUserByEmail");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("UserDAO findUserByEmail finished");
        }
        return user;
    }

    @Override
    public List<User> getUsers(String role, PaginationDTO paginationDTO) throws UserException {
        LOGGER.debug("UsersDao getUsers started");
        List<User> countList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_USERS);
            preparedStatement.setString(k++, role);
            preparedStatement.setInt(k++, paginationDTO.getAmountOfItems());
            preparedStatement.setInt(k, paginationDTO.getOffset());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                countList.add(fillUser(resultSet));
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Cant getUsers Users", sqlException);
            throw new UserException("This getUsers");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("UsersDAO getUsers finished");
        }
        return countList;
    }

    @Override
    public boolean updateUserStatus(int userId, String status) throws UserException {
        LOGGER.debug("updateUserStatus starts");
        boolean result;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_STATUS);
            preparedStatement.setString(k++, status);
            preparedStatement.setInt(k, userId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException sqlException) {
            LOGGER.error("cant updateUserStatus", sqlException);
            throw new UserException("This updateUserStatus");
        } finally {
            DBManager.closeStatement(preparedStatement);
            DBManager.closeConnection(connection);
        }
        return result;
    }

    @Override
    public boolean updateUserNameAndSurname(int userId, String name, String surname) throws UserException {
        LOGGER.debug("updateUserNameAndSurname starts");
        boolean result;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_NAME_SURNAME);
            preparedStatement.setString(k++, name);
            preparedStatement.setString(k++, surname);
            preparedStatement.setInt(k, userId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException sqlException) {
            LOGGER.error("cant updateUserNameAndSurname", sqlException);
            throw new UserException("This updateUserNameAndSurname");
        } finally {
            DBManager.closeStatement(preparedStatement);
            DBManager.closeConnection(connection);
        }
        return result;
    }

    @Override
    public int getUsersNumber(String role) throws UserException {
        LOGGER.debug("getUsersNumber starts");
        int userCount = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_USERS_NUMBER);
            preparedStatement.setString(k, role);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userCount = resultSet.getInt(COLUMN_INDEX_ONE);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("cant getUsersNumber", sqlException);
            throw new UserException("There are no users", sqlException);
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
        }
        return userCount;
    }

    private User fillUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setEncrypt(resultSet.getString("encrypt"));
        user.setRole(Role.valueOf(resultSet.getString("role").toUpperCase()));
        user.setStatusName(UserStatus.valueOf(resultSet.getString("statusName").toUpperCase()));
        LOGGER.info("UserDaoImpl fill user success");
        return user;
    }
}
