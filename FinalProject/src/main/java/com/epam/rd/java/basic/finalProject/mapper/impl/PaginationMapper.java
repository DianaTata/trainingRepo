package com.epam.rd.java.basic.finalProject.mapper.impl;

import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.mysql.jdbc.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class PaginationMapper {

    private static final Logger LOGGER = Logger.getLogger(PaginationMapper.class);
    public static final int DEFAULT_ITEMS_ON_PAGE = 5;
    public static final int MAX_ITEMS_ON_PAGE = 51;

    public PaginationDTO fillPaginationDTO(HttpServletRequest req) {
        PaginationDTO paginationDTO = new PaginationDTO();
        int currPage = 1;
        int limit = DEFAULT_ITEMS_ON_PAGE;
        String countItemsReq = req.getParameter("countItems");
        if (Objects.nonNull(countItemsReq)) {
            if (Integer.parseInt(countItemsReq) <= MAX_ITEMS_ON_PAGE) {
                limit = Integer.parseInt(countItemsReq);
            }
        }
        paginationDTO.setAmountOfItems(limit);
        String page = req.getParameter("page");
        if (!StringUtils.isNullOrEmpty(page)) {
            currPage = Integer.parseInt(page);
        }
        paginationDTO.setCurrentPage(currPage);
        int offset = (currPage - 1) * limit;
        paginationDTO.setOffset(offset);
        LOGGER.debug("Cant creation pagination");
        return paginationDTO;
    }
}
