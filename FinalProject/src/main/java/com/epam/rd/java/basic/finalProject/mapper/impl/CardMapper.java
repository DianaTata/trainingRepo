package com.epam.rd.java.basic.finalProject.mapper.impl;

import com.epam.rd.java.basic.finalProject.dto.CardDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.Card;
import com.epam.rd.java.basic.finalProject.entity.User;
import com.epam.rd.java.basic.finalProject.mapper.Mapper;

public class CardMapper implements Mapper<CardDTO, Card> {

    private Mapper<UserDTO, User> userMapper;

    public CardMapper(Mapper<UserDTO, User> userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public Card mapToEntity(CardDTO cardDTO) {
        Card card = new Card();
        card.setId(cardDTO.getId());
        card.setCardNumber(cardDTO.getCardNumber());
        card.setCvv(cardDTO.getCvv());
        card.setExpiredDate(cardDTO.getExpiredDate());
        card.setAmount(cardDTO.getAmount());
        card.setUser(userMapper.mapToEntity(cardDTO.getUserDTO()));
        return card;
    }

    @Override
    public CardDTO mapToDTO(Card card) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(card.getId());
        cardDTO.setCardNumber(card.getCardNumber());
        cardDTO.setCvv(card.getCvv());
        cardDTO.setExpiredDate(card.getExpiredDate());
        cardDTO.setAmount(card.getAmount());
        cardDTO.setUserDTO(userMapper.mapToDTO(card.getUser()));
        return cardDTO;
    }
}
