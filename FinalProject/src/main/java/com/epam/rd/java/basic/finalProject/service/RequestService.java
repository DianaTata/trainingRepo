package com.epam.rd.java.basic.finalProject.service;

import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.dto.RequestDTO;
import com.epam.rd.java.basic.finalProject.exception.RequestException;

import java.util.List;

/**
 * class to work with request operations
 */
public interface RequestService {

    /**
     * method to retrieve request by count id and status
     *
     * @param id         - count id
     * @param statusName - status request
     * @return request by count id and status
     */
    RequestDTO getRequestByCountIdAndStatus(int id, String statusName) throws RequestException;

    /**
     * method to create request
     *
     * @param countId - count id
     * @return request
     */
    RequestDTO createRequest(int countId) throws RequestException;


    /**
     * method to retrieve list requests
     *
     * @param statusName    - status request
     * @param paginationDTO -pagination
     * @return list requests
     */
    List<RequestDTO> getRequests(String statusName, PaginationDTO paginationDTO) throws RequestException;

    /**
     * method to perform status request
     *
     * @param countId       - count id
     * @param countStatus   - status count
     * @param requestStatus - status request
     * @return result operation perform request
     */
    boolean performRequest(int countId, String countStatus, String requestStatus) throws RequestException;

    /**
     * method to retrieve requests
     *
     * @param statusName - status request
     * @return amount requests
     */
    int getUserRequestsCount(String statusName) throws RequestException;
}
