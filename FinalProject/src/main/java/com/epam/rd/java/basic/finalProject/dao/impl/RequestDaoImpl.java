package com.epam.rd.java.basic.finalProject.dao.impl;

import com.epam.rd.java.basic.finalProject.dao.CountDao;
import com.epam.rd.java.basic.finalProject.dao.RequestDao;
import com.epam.rd.java.basic.finalProject.dao.UserDao;
import com.epam.rd.java.basic.finalProject.db.DBManager;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.entity.Request;
import com.epam.rd.java.basic.finalProject.entity.RequestStatus;
import com.epam.rd.java.basic.finalProject.exception.CountException;
import com.epam.rd.java.basic.finalProject.exception.RequestException;
import com.epam.rd.java.basic.finalProject.exception.UserException;
import org.apache.log4j.Logger;

import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequestDaoImpl implements RequestDao {

    private static final Logger LOGGER = Logger.getLogger(RequestDaoImpl.class);

    private static final String INSERT_REQUEST = "INSERT INTO requests"
            + "(id, requestNumber, userId, requestDate, statusName, countId) VALUES "
            + "(DEFAULT, ?, ?, ?, DEFAULT, ?)";
    private static final String FIND_REQUEST_BY_COUNT_ID_AND_STATUS = "SELECT * FROM requests WHERE countId = ? AND statusName = ?";
    private static final String FIND_ALL_REQUESTS_IN_STATUS = "SELECT * FROM requests WHERE statusName = ? LIMIT ? OFFSET ?";
    private static final String UPDATE_REQUEST_STATUS = "UPDATE requests SET statusName = ? WHERE countId = ?";
    private static final String GET_REQUESTS_NUMBER = "SELECT count(*) FROM requests WHERE statusName = ?";
    private static final int COLUMN_INDEX_ONE = 1;
    private static final int RANDOM_NUMBER_FIRST = 999;
    private static final int RANDOM_NUMBER_TWO = 100;

    private UserDao userDao;
    private CountDao countDao;

    public RequestDaoImpl(UserDao userDao, CountDao countDao) {
        this.userDao = userDao;
        this.countDao = countDao;
    }

    @Override
    public Request insertRequest(Count count) throws RequestException {
        LOGGER.debug("RequestDao insertRequest started");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Request request = null;
        try {
            int k = 1;
            request = createRequest(count);
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(INSERT_REQUEST);
            preparedStatement.setInt(k++, request.getRequestNumber());
            preparedStatement.setInt(k++, request.getUser().getId());
            preparedStatement.setDate(k++, request.getRequestDate());
            preparedStatement.setInt(k, request.getCount().getId());
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(COLUMN_INDEX_ONE);
                    request.setId(id);
                }
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Cant insert Request", sqlException);
            throw new RequestException("Cant insert Request");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("RequestDAO insertRequest finished");
        }
        return request;
    }

    @Override
    public Request findRequestByCountIdAndStatus(int id, String statusName) throws RequestException {
        LOGGER.debug("RequestDao find Request By CountId started");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Request request = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_REQUEST_BY_COUNT_ID_AND_STATUS);
            preparedStatement.setInt(k++, id);
            preparedStatement.setString(k, statusName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                request = fillRequest(resultSet);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("RequestDao find Request By CountId Card", sqlException);
            throw new RequestException("Cant findRequestByCountIdAndStatus");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("RequestDao find Request By CountId finished");
        }
        return request;
    }

    @Override
    public List<Request> getRequests(String statusName, PaginationDTO paginationDTO) {
        LOGGER.debug("RequestsDao getRequests started");
        List<Request> requestList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_REQUESTS_IN_STATUS);
            preparedStatement.setString(k++, statusName);
            preparedStatement.setInt(k++, paginationDTO.getAmountOfItems());
            preparedStatement.setInt(k, paginationDTO.getOffset());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                requestList.add(fillRequest(resultSet));
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Cant getRequests Requests", sqlException);
            throw new RuntimeException("Cant getRequests");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("RequestsDAO getRequests finished");
        }
        return requestList;
    }

    @Override
    public int getUserRequestsNumber(String statusName) throws RequestException {
        LOGGER.debug("getUserRequestsNumber starts");
        int requestCount = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_REQUESTS_NUMBER);
            preparedStatement.setString(k, statusName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                requestCount = resultSet.getInt(COLUMN_INDEX_ONE);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("cant get user requests", sqlException);
            throw new RequestException("There are no requests for user", sqlException);
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
        }
        return requestCount;
    }

    @Override
    public boolean updateRequestStatus(int countId, String status, Connection connection) throws RequestException {
        LOGGER.debug("updateRequestStatus starts");
        boolean result;
        PreparedStatement preparedStatement = null;
        try {
            int k = 1;
            preparedStatement = connection.prepareStatement(UPDATE_REQUEST_STATUS);
            preparedStatement.setString(k++, status);
            preparedStatement.setInt(k, countId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException sqlException) {
            LOGGER.error("cant updateRequestStatus", sqlException);
            throw new RequestException("Can't update request updateRequestStatus");
        } finally {
            DBManager.closeStatement(preparedStatement);
        }
        return result;
    }

    @Override
    public boolean performRequest(int countId, String countStatus, String requestStatus) throws RequestException {
        LOGGER.debug("start performRequest");
        boolean isSuccess;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            countDao.updateCountStatus(countId, countStatus, connection);
            updateRequestStatus(countId, requestStatus, connection);
            connection.commit();
            isSuccess = true;
        } catch (SQLException | CountException | RequestException exception) {
            LOGGER.error("Cant performRequest", exception);
            DBManager.rollback(connection);
            throw new RequestException("Cant performRequest");
        } finally {
            DBManager.closeConnection(connection);
        }
        return isSuccess;
    }

    private Request createRequest(Count count) {
        Request request = new Request();
        request.setRequestNumber(new SecureRandom().nextInt(RANDOM_NUMBER_FIRST) + RANDOM_NUMBER_TWO);
        request.setRequestDate(Date.valueOf(LocalDate.now()));
        request.setCount(count);
        request.setUser(count.getUser());
        return request;
    }

    private Request fillRequest(ResultSet resultSet) throws SQLException {
        Request request = new Request();
        request.setId(resultSet.getInt("id"));
        request.setRequestNumber(resultSet.getInt("requestNumber"));
        request.setRequestDate(resultSet.getDate("requestDate"));
        try {
            request.setCount(countDao.findCountById(resultSet.getInt("countId")));
            request.setUser(userDao.findUserById(resultSet.getInt("userId")));
        } catch (CountException | UserException e) {
            LOGGER.error("RequestDaoImpl cant find user or count by id and Cant fillRequest", e);
        }
        request.setStatusName(RequestStatus.valueOf(resultSet.getString("statusName").toUpperCase()));
        return request;
    }
}
