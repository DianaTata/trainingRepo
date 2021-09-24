package com.epam.rd.java.basic.finalProject.mapper.impl;

import com.epam.rd.java.basic.finalProject.dto.CardDTO;
import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.Card;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.entity.User;
import com.epam.rd.java.basic.finalProject.mapper.Mapper;

public class CountMapper implements Mapper<CountDTO, Count> {
    private Mapper<CardDTO, Card> cardMapper;
    private Mapper<UserDTO, User> userMapper;

    public CountMapper(Mapper<CardDTO, Card> cardMapper, Mapper<UserDTO, User> userMapper) {
        this.cardMapper = cardMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Count mapToEntity(CountDTO countDTO) {
        Count count = new Count();
        count.setId(countDTO.getId());
        count.setCountName(countDTO.getCountName());
        count.setCountNumber(countDTO.getCountNumber());
        count.setAmount(countDTO.getAmount());
        count.setUser(userMapper.mapToEntity(countDTO.getUser()));
        count.setCard(cardMapper.mapToEntity(countDTO.getCard()));
        count.setStatusName(countDTO.getStatusName());
        return count;
    }

    @Override
    public CountDTO mapToDTO(Count count) {
        CountDTO countDTO = new CountDTO();
        countDTO.setId(count.getId());
        countDTO.setCountName(count.getCountName());
        countDTO.setCountNumber(count.getCountNumber());
        countDTO.setAmount(count.getAmount());
        countDTO.setUser(userMapper.mapToDTO(count.getUser()));
        countDTO.setCard(cardMapper.mapToDTO(count.getCard()));
        countDTO.setStatusName(count.getStatusName());
        return countDTO;
    }
}
