package com.epam.rd.java.basic.finalProject.dao.impl;

import com.epam.rd.java.basic.finalProject.dao.CountDao;
import com.epam.rd.java.basic.finalProject.dao.PaymentDao;
import com.epam.rd.java.basic.finalProject.dao.UserDao;
import com.epam.rd.java.basic.finalProject.db.DBManager;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.entity.Payment;
import com.epam.rd.java.basic.finalProject.entity.PaymentStatus;
import com.epam.rd.java.basic.finalProject.exception.CountException;
import com.epam.rd.java.basic.finalProject.exception.PaymentException;
import com.epam.rd.java.basic.finalProject.exception.UserException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDaoImpl implements PaymentDao {

    private static final Logger LOGGER = Logger.getLogger(PaymentDaoImpl.class);
    private static final String INSERT_PAYMENT = "INSERT INTO payments"
            + "(id, paymentNumber, userId, paymentDate, amount, fromCount, toCount, statusName) " +
            "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, DEFAULT)";
    private static final String FIND_PAYMENT_BY_ID = "SELECT * FROM payments WHERE id = ?";
    private static final String FIND_PAYMENTS_BY_USER_ID = "SELECT * FROM payments WHERE userId = ? ORDER BY . LIMIT ? OFFSET ?";
    private static final String UPDATE_PAYMENT_STATUS = "UPDATE payments SET statusName = ? WHERE id = ?";
    private static final String DELETE_PAYMENT = "DELETE FROM payments WHERE id = ?";
    private static final String GET_USER_PAYMENTS_NUMBER = "SELECT count(*) FROM payments WHERE userId = ?";
    private static final int COLUMN_INDEX_ONE = 1;

    private UserDao userDao;
    private CountDao countDao;

    public PaymentDaoImpl(UserDao userDao, CountDao countDao) {
        this.userDao = userDao;
        this.countDao = countDao;
    }

    public boolean insertPayment(Payment payment) throws PaymentException {
        LOGGER.debug("PaymentDao insertDao started");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(INSERT_PAYMENT);
            preparedStatement.setInt(k++, payment.getPaymentNumber());
            preparedStatement.setInt(k++, payment.getUser().getId());
            preparedStatement.setDate(k++, payment.getPaymentDate());
            preparedStatement.setBigDecimal(k++, payment.getAmount());
            preparedStatement.setInt(k++, payment.getFromCount().getId());
            preparedStatement.setInt(k, payment.getToCount().getId());
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(COLUMN_INDEX_ONE);
                    payment.setId(id);
                    flag = true;
                }
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Cant insert Payment", sqlException);
            throw new PaymentException("Can't insert payment");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("PaymentDAO insertPayment finished");
        }
        return flag;
    }

    @Override
    public boolean makePayment(Payment payment) throws PaymentException {
        LOGGER.debug("start make payment");
        boolean isSuccess = false;
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            Count fromCount = payment.getFromCount();
            Count toCount = payment.getToCount();
            BigDecimal amount = payment.getAmount();
            BigDecimal fromCountAmount = fromCount.getAmount();
            if (fromCountAmount.compareTo(amount) >= 0) {
                calculateCountAmount(fromCount.getId(), subtractAmountFromCount(amount, fromCountAmount), connection);
                calculateCountAmount(toCount.getId(), addAmountToCount(amount, toCount.getAmount()), connection);
                updatePaymentStatus(payment.getId(), PaymentStatus.SENT.getName(), connection);
                connection.commit();
                isSuccess = true;
            }
        } catch (SQLException | CountException | PaymentException exception) {
            LOGGER.error("Cant make payment", exception);
            DBManager.rollback(connection);
            throw new PaymentException("Can't makePayment");
        } finally {
            DBManager.closeConnection(connection);
        }
        return isSuccess;
    }

    private BigDecimal addAmountToCount(BigDecimal amount, BigDecimal toCount) {
        return toCount.add(amount);
    }

    private BigDecimal subtractAmountFromCount(BigDecimal amount, BigDecimal fromCountAmount) {
        return fromCountAmount.subtract(amount);
    }

    private void calculateCountAmount(int countId, BigDecimal amount, Connection connection) throws CountException {
        countDao.updateCountAmount(amount, connection, countId);
    }

    @Override
    public Payment findPaymentById(int id) throws PaymentException {
        LOGGER.debug("PaymentDao findPaymentById started");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Payment payment = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_PAYMENT_BY_ID);
            preparedStatement.setInt(k, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                payment = fillPayments(resultSet);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Cant findPaymentById Payment", sqlException);
            throw new PaymentException("Can't findPaymentById");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("PaymentDAO findPaymentById finished");
        }
        return payment;
    }

    @Override
    public List<Payment> getUserPayments(int userId, PaginationDTO paginationDTO) throws PaymentException {
        LOGGER.debug("PaymentDao getUserPayments started");
        List<Payment> payments = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuilder query = new StringBuilder(FIND_PAYMENTS_BY_USER_ID);
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
                payments.add(fillPayments(resultSet));
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Cant getUserPayments Payment", sqlException);
            throw new PaymentException("Can't getUserPayments");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("PaymentDAO getUserPayments finished");
        }
        return payments;
    }

    @Override
    public boolean updatePaymentStatus(int paymentId, String status, Connection connection) throws PaymentException {
        LOGGER.debug("update Payment Status starts");
        boolean result;
        PreparedStatement preparedStatement = null;
        try {
            int k = 1;
            preparedStatement = connection.prepareStatement(UPDATE_PAYMENT_STATUS);
            preparedStatement.setString(k++, status);
            preparedStatement.setInt(k, paymentId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException sqlException) {
            LOGGER.error("cant update Payment Status", sqlException);
            throw new PaymentException("Can't update Payment Status");
        } finally {
            DBManager.closeStatement(preparedStatement);
        }
        return result;
    }

    @Override
    public boolean deletePayment(int paymentId) throws PaymentException {
        LOGGER.debug("PaymentDao deletePayment started");
        boolean isSuccess;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(DELETE_PAYMENT);
            preparedStatement.setInt(k, paymentId);
            preparedStatement.executeUpdate();
            isSuccess = true;
        } catch (SQLException exception) {
            LOGGER.error("Cant deletePayment Payment", exception);
            throw new PaymentException("Can't delete payment", exception);
        } finally {
            DBManager.closeStatement(preparedStatement);
            DBManager.closeConnection(connection);
            LOGGER.debug("PaymentDAO deletePayment finished");
        }
        return isSuccess;
    }

    @Override
    public int getUserPaymentsNumber(int userId) throws PaymentException {
        LOGGER.debug("getUserPaymentsNumber starts");
        int paymentsCount = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_USER_PAYMENTS_NUMBER);
            preparedStatement.setInt(k, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                paymentsCount = resultSet.getInt(COLUMN_INDEX_ONE);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("cant getUserPaymentsNumber", sqlException);
            throw new PaymentException("There are no payments for user", sqlException);
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
        }
        return paymentsCount;
    }

    private Payment fillPayments(ResultSet resultSet) throws SQLException {
        Payment payment = new Payment();
        payment.setId(resultSet.getInt("id"));
        payment.setAmount(resultSet.getBigDecimal("amount"));
        try {
            payment.setFromCount(countDao.findCountById(resultSet.getInt("fromCount")));
            payment.setToCount(countDao.findCountById(resultSet.getInt("toCount")));
            payment.setUser(userDao.findUserById(resultSet.getInt("userId")));
        } catch (CountException | UserException e) {
            LOGGER.error("PaymentDaoImpl cant find user or count by id cant fillPayments");
        }
        payment.setPaymentDate(resultSet.getDate("paymentDate"));
        payment.setPaymentNumber(resultSet.getInt("paymentNumber"));
        payment.setStatusName(PaymentStatus.valueOf(resultSet.getString("statusName").toUpperCase()));


        return payment;
    }
}
