package com.epam.rd.java.basic.finalProject.mapper.impl;

import com.epam.rd.java.basic.finalProject.dto.CountDTO;
import com.epam.rd.java.basic.finalProject.dto.RequestDTO;
import com.epam.rd.java.basic.finalProject.dto.UserDTO;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.entity.Request;
import com.epam.rd.java.basic.finalProject.entity.User;
import com.epam.rd.java.basic.finalProject.mapper.Mapper;

public class RequestMapper implements Mapper<RequestDTO, Request> {
    private Mapper<UserDTO, User> userMapper;
    private Mapper<CountDTO, Count> countMapper;

    public RequestMapper(Mapper<UserDTO, User> userMapper, Mapper<CountDTO, Count> countMapper) {
        this.userMapper = userMapper;
        this.countMapper = countMapper;
    }

    @Override
    public Request mapToEntity(RequestDTO requestDTO) {
        Request request = new Request();
        request.setRequestNumber(requestDTO.getRequestNumber());
        request.setRequestDate(requestDTO.getRequestDate());
        request.setId(requestDTO.getId());
        request.setStatusName(requestDTO.getStatusName());
        request.setUser(userMapper.mapToEntity(requestDTO.getUser()));
        request.setCount(countMapper.mapToEntity(requestDTO.getCount()));
        return request;
    }

    @Override
    public RequestDTO mapToDTO(Request request) {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setRequestNumber(request.getRequestNumber());
        requestDTO.setRequestDate(request.getRequestDate());
        requestDTO.setId(request.getId());
        requestDTO.setStatusName(request.getStatusName());
        requestDTO.setUser(userMapper.mapToDTO(request.getUser()));
        requestDTO.setCount(countMapper.mapToDTO(request.getCount()));
        return requestDTO;
    }
}
