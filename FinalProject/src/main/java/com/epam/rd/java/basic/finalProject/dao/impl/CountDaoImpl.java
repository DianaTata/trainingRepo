package com.epam.rd.java.basic.finalProject.dao.impl;

import com.epam.rd.java.basic.finalProject.dao.CardDao;
import com.epam.rd.java.basic.finalProject.dao.CountDao;
import com.epam.rd.java.basic.finalProject.dao.UserDao;
import com.epam.rd.java.basic.finalProject.db.DBManager;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Card;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.entity.CountStatus;
import com.epam.rd.java.basic.finalProject.exception.CardException;
import com.epam.rd.java.basic.finalProject.exception.CountException;
import com.epam.rd.java.basic.finalProject.exception.UserException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountDaoImpl implements CountDao {

    private static final Logger LOGGER = Logger.getLogger(CountDaoImpl.class);

    private static final String INSERT_COUNT = "INSERT INTO counts"
            + "(id, countNumber, countName, amount, userId, cardId, statusName) VALUES "
            + "(DEFAULT, ?, ?, DEFAULT, ?, ?, DEFAULT)";
    private static final String FIND_COUNT_BY_CARD_ID = "SELECT * FROM counts WHERE cardId = ?";
    private static final String UPDATE_COUNT_AMOUNT = "UPDATE counts SET amount = ? WHERE id = ?";
    private static final String UPDATE_COUNT_STATUS = "UPDATE counts SET statusName = ? WHERE id = ?";
    private static final String FIND_COUNT_BY_ID = "SELECT * FROM counts WHERE id = ?";
    private static final String FIND_COUNTS_BY_USER_ID = "SELECT * FROM counts WHERE userId = ?  ORDER BY . LIMIT ? OFFSET ?";
    private static final String FIND_COUNTS_BY_USER_ID_AND_STATUS = "SELECT * FROM counts WHERE userId = ? and statusName = ?";
    private static final String FIND_COUNT_BY_NUMBER_AND_STATUS = "SELECT * FROM counts WHERE countNumber = ? and statusName = ?";
    private static final String GET_USER_COUNTS_NUMBER = "SELECT count(*) FROM counts WHERE userId = ?";
    private static final int COLUMN_INDEX_ONE = 1;
    private static final int RANDOM_NUMBER_FIRST = 8999;
    private static final int RANDOM_NUMBER_TWO = 1000;
    private static final int RANDOM_COUNT = 4;

    private CardDao cardDao;
    private UserDao userDao;

    public CountDaoImpl(CardDao cardDao, UserDao userDao) {
        this.cardDao = cardDao;
        this.userDao = userDao;
    }

    @Override
    public Count insertCount(Count count, Connection connection) throws CountException {
        LOGGER.debug("CountDao insert count started");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            int k = 1;
            preparedStatement = connection.prepareStatement(INSERT_COUNT);
            preparedStatement.setInt(k++, count.getCountNumber());
            preparedStatement.setString(k++, count.getCountName());
            preparedStatement.setInt(k++, count.getUser().getId());
            preparedStatement.setInt(k, count.getCard().getId());
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(COLUMN_INDEX_ONE);
                    count.setId(id);
                }
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Cant insert count");
            throw new CountException("cant create Count");
        } finally {
            DBManager.closeStatement(preparedStatement);
            DBManager.closeResultSet(resultSet);
            LOGGER.debug("CountDAO insert count finished");
        }
        return count;
    }

    @Override
    public Count findCountByNumber(int number, String statusName) throws CountException {
        LOGGER.debug("CountDao findCountByNumber started");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Count count = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_COUNT_BY_NUMBER_AND_STATUS);
            preparedStatement.setInt(k++, number);
            preparedStatement.setString(k, statusName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = fillCount(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Cant findCountByNumber", exception);
            throw new CountException("Cant findCountByNumber");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("CountDAO findCountByNumber finished");
        }
        return count;
    }

    @Override
    public boolean updateCountStatus(int countId, String status) throws CountException {
        LOGGER.debug("update Count Status starts");
        boolean result;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_COUNT_STATUS);
            preparedStatement.setString(k++, status);
            preparedStatement.setInt(k, countId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException sqlException) {
            LOGGER.error("cant updateCountStatus", sqlException);
            throw new CountException("Cant updateCountStatus");
        } finally {
            DBManager.closeStatement(preparedStatement);
            DBManager.closeConnection(connection);
        }
        return result;
    }

    @Override
    public int getUserCountsNumber(int userId) throws CountException {
        LOGGER.debug("Get User Counts Number starts");
        int countsNumber = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_USER_COUNTS_NUMBER);
            preparedStatement.setInt(k, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                countsNumber = resultSet.getInt(COLUMN_INDEX_ONE);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("cant get user counts", sqlException);
            throw new CountException("There are no counts for user");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
        }
        return countsNumber;
    }

    @Override
    public boolean updateCountStatus(int countId, String status, Connection connection) throws CountException {
        LOGGER.debug("update Count Status starts");
        boolean result;
        PreparedStatement preparedStatement = null;
        try {
            int k = 1;
            preparedStatement = connection.prepareStatement(UPDATE_COUNT_STATUS);
            preparedStatement.setString(k++, status);
            preparedStatement.setInt(k, countId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException sqlException) {
            LOGGER.error("cant update Count Status", sqlException);
            throw new CountException("Can't update count status");
        } finally {
            DBManager.closeStatement(preparedStatement);
        }
        return result;
    }

    @Override
    public Count findCountByCardId(int id) throws CountException {
        LOGGER.debug("CountDao find Count By Card Id started");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Count count = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_COUNT_BY_CARD_ID);
            preparedStatement.setInt(k, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = fillCount(resultSet);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("CountDAO find Count By Card id", sqlException);
            throw new CountException("Cant find Count By Card Id");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("CountDAO find Count By Card finished");
        }
        return count;
    }

    @Override
    public boolean linkCountToCard(Card card) throws CountException {
        boolean isSuccess;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            Card result = cardDao.insertCard(card, connection);
            insertCount(createCount(result), connection);
            connection.commit();
            isSuccess = true;
        } catch (SQLException | CardException exception) {
            LOGGER.error("Cant link count To Card", exception);
            DBManager.rollback(connection);
            throw new CountException("Cant link count To Card");
        } finally {
            DBManager.closeConnection(connection);
        }
        return isSuccess;
    }

    @Override
    public boolean updateCountAmount(BigDecimal amount, Connection connection, int countId) throws CountException {
        LOGGER.debug("update Count Amount starts");
        boolean result;
        PreparedStatement preparedStatement = null;
        try {
            int k = 1;
            preparedStatement = connection.prepareStatement(UPDATE_COUNT_AMOUNT);
            preparedStatement.setBigDecimal(k++, amount);
            preparedStatement.setInt(k, countId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException sqlException) {
            LOGGER.error("cant update Count Amount", sqlException);
            throw new CountException("Can't update count amount");
        } finally {
            DBManager.closeStatement(preparedStatement);
        }
        return result;
    }

    @Override
    public boolean refillCount(int cardId, int countId, BigDecimal amount) throws CountException {
        LOGGER.debug("start refill");
        boolean isSuccess = false;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            Card cardById = cardDao.findCardById(cardId);
            Count countById = findCountById(countId);
            BigDecimal cardAmount = cardById.getAmount();
            if (cardAmount.compareTo(amount) >= 0) {
                calculateCardAmountSubtract(cardId, amount, connection, cardAmount);
                calculateCountAmountAdd(countId, amount, connection, countById);
                connection.commit();
                isSuccess = true;
            }
        } catch (SQLException | CountException | CardException exception) {
            LOGGER.error("Cant refillCount", exception);
            DBManager.rollback(connection);
            throw new CountException("Cant refillCount");
        } finally {
            DBManager.closeConnection(connection);
        }
        return isSuccess;
    }

    private void calculateCountAmountAdd(int countId, BigDecimal amount, Connection connection, Count countById) throws CountException {
        BigDecimal sum = countById.getAmount().add(amount);
        updateCountAmount(sum, connection, countId);
    }

    private void calculateCardAmountSubtract(int cardId, BigDecimal amount, Connection connection, BigDecimal cardAmount) throws CardException {
        BigDecimal result = cardAmount.subtract(amount);
        cardDao.updateCardAmount(result, connection, cardId);
    }

    private void calculateCountAmountSubtract(int countId, BigDecimal amount, Connection connection, BigDecimal countAmount) throws CountException {
        BigDecimal result = countAmount.subtract(amount);
        updateCountAmount(result, connection, countId);
    }

    private void calculateCardAmountAdd(int cardId, BigDecimal amount, Connection connection, BigDecimal cardAmount) throws CardException {
        BigDecimal result = cardAmount.add(amount);
        cardDao.updateCardAmount(result, connection, cardId);
    }

    @Override
    public Count findCountById(int countId) throws CountException {
        LOGGER.debug("CountDao findCountById started");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Count count = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_COUNT_BY_ID);
            preparedStatement.setInt(k, countId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = fillCount(resultSet);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Cant findCountById", sqlException);
            throw new CountException("Cant find Count By Id");

        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("CountDao findCountById finished");
        }
        return count;
    }

    @Override
    public List<Count> getUserCounts(int userId, PaginationDTO paginationDTO) throws CountException {
        LOGGER.debug("CountDao getUserCounts started");
        List<Count> countList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuilder query = new StringBuilder(FIND_COUNTS_BY_USER_ID);
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            int charReplace = query.indexOf(".");
            query.replace(charReplace, charReplace + 1, paginationDTO.getSortBy());
            preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.setInt(k++, userId);
            preparedStatement.setInt(k++, paginationDTO.getAmountOfItems());
            preparedStatement.setInt(k, paginationDTO.getOffset());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                countList.add(fillCount(resultSet));
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Cant getUserCounts Count", sqlException);
            throw new CountException("Cant get User Counts");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("CountDAO getUserCounts finished");
        }
        return countList;
    }

    @Override
    public List<Count> getUserCounts(int userId, String status) throws CountException {
        LOGGER.debug("CountDao getUserCounts started");
        List<Count> countList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_COUNTS_BY_USER_ID_AND_STATUS);
            preparedStatement.setInt(k++, userId);
            preparedStatement.setString(k, status);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                countList.add(fillCount(resultSet));
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Cant getUserCounts Count", sqlException);
            throw new CountException("Cant get User Counts");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("CountDAO getUserCounts finished");
        }
        return countList;
    }

    @Override
    public boolean withdrawMoney(int cardId, int countId, BigDecimal amount) throws CountException {
        LOGGER.debug("Start withdraw");
        boolean isSuccess = false;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            Card cartById = cardDao.findCardById(cardId);
            Count countById = findCountById(countId);
            BigDecimal countAmount = countById.getAmount();
            BigDecimal cardAmount = cartById.getAmount();
            if (countAmount.compareTo(amount) >= 0) {
                calculateCountAmountSubtract(countId, amount, connection, countAmount);
                calculateCardAmountAdd(cardId, amount, connection, cardAmount);
                connection.commit();
                isSuccess = true;
            }
        } catch (SQLException | CountException | CardException exception) {
            LOGGER.error("Cant withdraw", exception);
            DBManager.rollback(connection);
            throw new CountException("Cant withdraw Money");
        } finally {
            DBManager.closeConnection(connection);
        }
        return isSuccess;
    }

    private Count createCount(Card card) {
        Count count = new Count();
        count.setCountNumber(new SecureRandom().nextInt(RANDOM_NUMBER_FIRST) + RANDOM_NUMBER_TWO);
        count.setCountName(RandomStringUtils.random(RANDOM_COUNT, true, false).toUpperCase());
        count.setUser(card.getUser());
        count.setCard(card);
        return count;
    }

    private Count fillCount(ResultSet resultSet) throws SQLException {
        Count count = new Count();
        count.setId(resultSet.getInt("id"));
        count.setCountNumber(resultSet.getInt("countNumber"));
        count.setCountName(resultSet.getString("countName"));
        count.setAmount(resultSet.getBigDecimal("amount"));
        count.setStatusName(CountStatus.valueOf(resultSet.getString("statusName").toUpperCase()));
        try {
            count.setCard(cardDao.findCardById(resultSet.getInt("cardId")));
            count.setUser(userDao.findUserById(resultSet.getInt("userId")));
        } catch (CardException | UserException e) {
            LOGGER.error("CountDaoImpl cant find user or card by id and cant withdraw", e);
        }
        return count;
    }
}
