package com.epam.rd.java.basic.finalProject.service.impl;

import com.epam.rd.java.basic.finalProject.dao.CountDao;
import com.epam.rd.java.basic.finalProject.dao.RequestDao;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.dto.RequestDTO;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.entity.Request;
import com.epam.rd.java.basic.finalProject.exception.RequestException;
import com.epam.rd.java.basic.finalProject.mapper.Mapper;
import com.epam.rd.java.basic.finalProject.service.RequestService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestServiceImpl implements RequestService {

    private static final Logger LOGGER = Logger.getLogger(RequestServiceImpl.class);

    private RequestDao requestDao;
    private Mapper<RequestDTO, Request> requestMapper;
    private CountDao countDao;

    public RequestServiceImpl(RequestDao requestDao, Mapper<RequestDTO, Request> requestMapper, CountDao countDao) {
        this.requestDao = requestDao;
        this.requestMapper = requestMapper;
        this.countDao = countDao;
    }

    @Override
    public RequestDTO getRequestByCountIdAndStatus(int id, String statusName) throws RequestException {
        LOGGER.warn("started getRequestByCountIdAndStatus");
        Request request = requestDao.findRequestByCountIdAndStatus(id, statusName);
        if (Objects.nonNull(request)) {
            return requestMapper.mapToDTO(request);
        }
        return null;
    }

    @Override
    public RequestDTO createRequest(int countId) throws RequestException {
        LOGGER.warn("started createRequest");
        Count countById = countDao.findCountById(countId);
        Request request = requestDao.insertRequest(countById);
        return requestMapper.mapToDTO(request);
    }

    @Override
    public List<RequestDTO> getRequests(String statusName, PaginationDTO paginationDTO) throws RequestException {
        List<Request> requests = requestDao.getRequests(statusName, paginationDTO);
        LOGGER.warn("started getRequests");
        return requests.stream()
                .map(requestMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean performRequest(int countId, String countStatus, String requestStatus) throws RequestException {
        LOGGER.warn("started performRequest");
        return requestDao.performRequest(countId, countStatus, requestStatus);
    }

    @Override
    public int getUserRequestsCount(String statusName) throws RequestException {
        LOGGER.warn("started get user requests");
        return requestDao.getUserRequestsNumber(statusName);
    }

}
