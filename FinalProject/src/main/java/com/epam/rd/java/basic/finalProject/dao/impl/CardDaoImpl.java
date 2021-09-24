package com.epam.rd.java.basic.finalProject.dao.impl;

import com.epam.rd.java.basic.finalProject.dao.CardDao;
import com.epam.rd.java.basic.finalProject.dao.UserDao;
import com.epam.rd.java.basic.finalProject.db.DBManager;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Card;
import com.epam.rd.java.basic.finalProject.exception.CardException;
import com.epam.rd.java.basic.finalProject.exception.UserException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardDaoImpl implements CardDao {

    private static final Logger LOGGER = Logger.getLogger(CardDaoImpl.class);

    private static final String INSERT_CARD = "INSERT INTO cards"
            + "(id, cardNumber, cvv, expiredDate, amount, userId) VALUES "
            + "(DEFAULT, ?, ?, ?, ?, ?)";
    private static final String FIND_CARD_BY_NUMBER = "SELECT * FROM cards WHERE cardNumber = ?";
    private static final String FIND_CARD_BY_ID = "SELECT * FROM cards WHERE id = ?";
    private static final String FIND_CARDS_BY_USER_ID = "SELECT * FROM cards WHERE userId = ? ORDER BY . LIMIT ? OFFSET ?";
    private static final String UPDATE_CARDS_AMOUNT = "UPDATE cards SET amount = ? WHERE id = ?";
    private static final String GET_USER_COUNTS_NUMBER = "SELECT count(*) FROM cards WHERE userId = ?";
    private static final int COLUMN_INDEX_ONE = 1;

    private UserDao userDao;

    public CardDaoImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Card insertCard(Card card, Connection connection) throws CardException {
        LOGGER.debug("CardDao insertCard started");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            int k = 1;
            preparedStatement = connection.prepareStatement(INSERT_CARD);
            preparedStatement.setString(k++, card.getCardNumber());
            preparedStatement.setInt(k++, card.getCvv());
            preparedStatement.setDate(k++, card.getExpiredDate());
            preparedStatement.setBigDecimal(k++, card.getAmount());
            preparedStatement.setInt(k, card.getUser().getId());
            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(COLUMN_INDEX_ONE);
                    card.setId(id);
                }
            }
        } catch (SQLException exception) {
            LOGGER.error("Cant insert Card", exception);
            throw new CardException("Cant insert Card");
        } finally {
            DBManager.closeStatement(preparedStatement);
            DBManager.closeResultSet(resultSet);
            LOGGER.debug("CardDAO insertCard finished");
        }
        return card;
    }

    @Override
    public Card findCardByNumber(String number) throws CardException {
        LOGGER.debug("CardDao findCardByNumber started");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Card card = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_CARD_BY_NUMBER);
            preparedStatement.setString(k, number);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                card = fillCard(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Cant findCardByNumber Card", exception);
            throw new CardException("Cant findCardByNumber Card");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("CardDAO findCardByNumber finished");
        }
        return card;
    }

    @Override
    public Card findCardById(int number) throws CardException {
        LOGGER.debug("CardDao findCardById started");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Card card = null;
        try {
            int k = 1;
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_CARD_BY_ID);
            preparedStatement.setInt(k, number);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                card = fillCard(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Cant findCardById Card", exception);
            throw new CardException("Cant findCardById Card");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("CardDAO findCardById finished");
        }
        return card;
    }

    @Override
    public List<Card> getUserCards(int userId, PaginationDTO paginationDTO) throws CardException {
        LOGGER.debug("CardDao showUserCards started");
        List<Card> cardList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StringBuilder query = new StringBuilder(FIND_CARDS_BY_USER_ID);
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
                cardList.add(fillCard(resultSet));
            }
        } catch (SQLException exception) {
            LOGGER.error("Cant showUserCards Card", exception);
            throw new CardException("Cant showUserCards Card");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
            LOGGER.debug("CardDAO showUserCards finished");
        }
        return cardList;
    }

    @Override
    public boolean updateCardAmount(BigDecimal amount, Connection connection, int cardId) throws CardException {
        LOGGER.debug("update Card Amount starts");
        boolean result;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_CARDS_AMOUNT);
            int k = 1;
            preparedStatement.setBigDecimal(k++, amount);
            preparedStatement.setInt(k, cardId);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException exception) {
            LOGGER.error("cant update Card Amount", exception);
            throw new CardException("cant update Card Amount");
        } finally {
            DBManager.closeStatement(preparedStatement);
        }
        return result;
    }

    @Override
    public int getUserCardsNumber(int userId) throws CardException {
        LOGGER.debug("Start Get User Cards Number");
        int cardsCount = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(GET_USER_COUNTS_NUMBER);
            int k = 1;
            preparedStatement.setInt(k, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cardsCount = resultSet.getInt(COLUMN_INDEX_ONE);
            }
        } catch (SQLException exception) {
            LOGGER.error("cant get user cards", exception);
            throw new CardException("There are no cards for user");
        } finally {
            DBManager.close(connection, preparedStatement, resultSet);
        }
        return cardsCount;
    }

    private Card fillCard(ResultSet resultSet) throws SQLException {
        Card card = new Card();
        card.setId(resultSet.getInt("id"));
        card.setCardNumber(resultSet.getString("cardNumber"));
        card.setCvv(resultSet.getInt("cvv"));
        card.setExpiredDate(resultSet.getDate("expiredDate"));
        card.setAmount(resultSet.getBigDecimal("amount"));
        try {
            card.setUser(userDao.findUserById(resultSet.getInt("userId")));
        } catch (UserException e) {
            LOGGER.error("CardDaoImpl cant find user and cant fill card", e);
        }
        return card;
    }
}
