package com.epam.rd.java.basic.finalProject.dao;

import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.entity.Request;
import com.epam.rd.java.basic.finalProject.exception.RequestException;

import java.sql.Connection;
import java.util.List;

public interface RequestDao {
    Request insertRequest(Count count) throws RequestException;

    Request findRequestByCountIdAndStatus(int id, String statusName) throws RequestException;

    List<Request> getRequests(String statusName, PaginationDTO paginationDTO) throws RequestException;

    boolean updateRequestStatus(int countId, String status, Connection connection) throws RequestException;

    boolean performRequest(int countId, String countStatus, String requestStatus) throws RequestException;

    int getUserRequestsNumber(String statusName) throws RequestException;

}
