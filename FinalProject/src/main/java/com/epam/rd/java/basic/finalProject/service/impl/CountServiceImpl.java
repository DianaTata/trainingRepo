package com.epam.rd.java.basic.finalProject.service.impl;

import com.epam.rd.java.basic.finalProject.dao.CountDao;
import com.epam.rd.java.basic.finalProject.dto.CardDTO;
import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Card;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.exception.CountException;
import com.epam.rd.java.basic.finalProject.mapper.Mapper;
import com.epam.rd.java.basic.finalProject.service.CountService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CountServiceImpl implements CountService {

    private static final Logger LOGGER = Logger.getLogger(CountServiceImpl.class);

    private CountDao countDao;
    private Mapper<CountDTO, Count> countMapper;
    private Mapper<CardDTO, Card> cardMapper;

    public CountServiceImpl(CountDao countDao, Mapper<CountDTO, Count> countMapper, Mapper<CardDTO, Card> cardMapper) {
        this.countDao = countDao;
        this.countMapper = countMapper;
        this.cardMapper = cardMapper;
    }

    @Override
    public CountDTO getCountByCardId(int id) throws CountException {
        LOGGER.warn("started getCountByCardId");
        Count countByCardId = countDao.findCountByCardId(id);
        if (Objects.nonNull(countByCardId)) {
            return countMapper.mapToDTO(countByCardId);
        }
        return null;
    }

    @Override
    public boolean linkCountToCard(CardDTO cardDTO) throws CountException {
        LOGGER.warn("started linkCountToCard");
        Card card = cardMapper.mapToEntity(cardDTO);
        if (Objects.nonNull(card)) {
            return countDao.linkCountToCard(card);
        }
        return false;
    }

    @Override
    public boolean refillCount(int cardId, int countId, BigDecimal amount) throws CountException {
        LOGGER.warn("started refillCount");
        return countDao.refillCount(cardId, countId, amount);
    }

    @Override
    public CountDTO getCountById(int countId) throws CountException {
        LOGGER.warn("started getCountById");
        Count countByCountId = countDao.findCountById(countId);
        if (Objects.nonNull(countByCountId)) {
            return countMapper.mapToDTO(countByCountId);
        }
        return null;
    }

    @Override
    public List<CountDTO> getUserCounts(int userId, PaginationDTO paginationDTO) throws CountException {
        LOGGER.warn("started getUserCounts");
        List<Count> userCounts = countDao.getUserCounts(userId, paginationDTO);
        return userCounts.stream()
                .map(countMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CountDTO> getUserCounts(int userId, String status) throws CountException {
        LOGGER.warn("started getUserCounts");
        List<Count> userCounts = countDao.getUserCounts(userId, status);
        if (Objects.nonNull(userCounts)) {
            return userCounts.stream()
                    .map(countMapper::mapToDTO)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public CountDTO getCountByNumber(int number, String statusName) throws CountException {
        LOGGER.warn("started getCountByNumber");
        Count countByCountNumber = countDao.findCountByNumber(number, statusName);
        if (Objects.nonNull(countByCountNumber)) {
            return countMapper.mapToDTO(countByCountNumber);
        }
        return null;
    }

    @Override
    public boolean performBlockCount(int countId, String status) throws CountException {
        LOGGER.warn("started performBlockCount");
        return countDao.updateCountStatus(countId, status);
    }

    @Override
    public int getUserCountsNumber(int userId) throws CountException {
        LOGGER.warn("started get user counts");
            return countDao.getUserCountsNumber(userId);
    }

}
