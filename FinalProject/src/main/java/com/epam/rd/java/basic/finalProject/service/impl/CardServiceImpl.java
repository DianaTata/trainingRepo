package com.epam.rd.java.basic.finalProject.service.impl;

import com.epam.rd.java.basic.finalProject.dao.CardDao;
import com.epam.rd.java.basic.finalProject.dao.CountDao;
import com.epam.rd.java.basic.finalProject.dto.CardDTO;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Card;
import com.epam.rd.java.basic.finalProject.exception.CardException;
import com.epam.rd.java.basic.finalProject.exception.CountException;
import com.epam.rd.java.basic.finalProject.mapper.Mapper;
import com.epam.rd.java.basic.finalProject.service.CardService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CardServiceImpl implements CardService {

    private static final Logger LOGGER = Logger.getLogger(CardServiceImpl.class);

    private CardDao cardDao;
    private CountDao countDao;
    private Mapper<CardDTO, Card> cardMapper;

    public CardServiceImpl(CardDao cardDao, CountDao countDao, Mapper<CardDTO, Card> cardMapper) {
        this.cardDao = cardDao;
        this.countDao = countDao;
        this.cardMapper = cardMapper;
    }

    @Override
    public CardDTO getCardByNumber(String number) throws CardException {
        LOGGER.error("started getCardByNumber");
        Card cardByNumber = cardDao.findCardByNumber(number);
        if (Objects.nonNull(cardByNumber)) {
            return cardMapper.mapToDTO(cardByNumber);
        }
        LOGGER.info("success getCardByNumber");
        return null;
    }

    @Override
    public CardDTO getCardById(int id) throws CardException {
        LOGGER.error("started getCardById");
        Card card = cardDao.findCardById(id);
        if (Objects.nonNull(card)) {
            return cardMapper.mapToDTO(card);
        }
        LOGGER.info("success getCardById");
        return null;
    }

    @Override
    public List<CardDTO> getUserCards(int userId, PaginationDTO paginationDTO) throws CardException {
        LOGGER.error("started get user cards");
        List<Card> userCards = cardDao.getUserCards(userId, paginationDTO);
        return userCards.stream()
                .map(cardMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean withdrawMoney(int cardId, int countId, BigDecimal amount) throws CountException {
        LOGGER.warn("started withdrawMoney");
        return countDao.withdrawMoney(cardId, countId, amount);


    }

    @Override
    public int getUserCardsCount(int userId) throws CardException {
        LOGGER.warn("started get user cards");
        return cardDao.getUserCardsNumber(userId);
    }

}
