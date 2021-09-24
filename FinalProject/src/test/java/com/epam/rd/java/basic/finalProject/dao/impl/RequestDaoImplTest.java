package com.epam.rd.java.basic.finalProject.dao.impl;

import com.epam.rd.java.basic.finalProject.dao.CardDao;
import com.epam.rd.java.basic.finalProject.dao.CountDao;
import com.epam.rd.java.basic.finalProject.dao.UserDao;
import com.epam.rd.java.basic.finalProject.db.DBManager;
import com.epam.rd.java.basic.finalProject.dto.PaginationDTO;
import com.epam.rd.java.basic.finalProject.entity.Count;
import com.epam.rd.java.basic.finalProject.entity.CountStatus;
import com.epam.rd.java.basic.finalProject.entity.Request;
import com.epam.rd.java.basic.finalProject.entity.RequestStatus;
import com.epam.rd.java.basic.finalProject.exception.CountException;
import com.epam.rd.java.basic.finalProject.exception.RequestException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestDaoImplTest {

    public static final int TEST_CVV = 123;
    public static final Date EXPIRED_DATE = Date.valueOf(LocalDate.now());

    @InjectMocks
    @Spy
    private RequestDaoImpl requestDao;

    @Mock
    private UserDao userDao;
    @Mock
    private CountDao countDao;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;

    private UserDao userDaoImpl = new UserDaoImpl();
    private CardDao cardDao = new CardDaoImpl(userDaoImpl);
    private CountDao countDaoImpl = new CountDaoImpl(cardDao, userDaoImpl);

    @Test
    public void shouldInsertRequest() throws SQLException, CountException, RequestException {
        Connection connection = DBManager.getInstance().getConnection();
        Count count = countDaoImpl.findCountById(38);
        Request request = requestDao.insertRequest(count);

        Assert.assertNotNull(request);
    }

    @Test
    public void shouldFindRequestByCountIdAndStatus() throws SQLException, RequestException {
        Connection connection = DBManager.getInstance().getConnection();
        Request request = requestDao.findRequestByCountIdAndStatus(38, RequestStatus.INPROGRESS.getName());

        Assert.assertNotNull(request);
    }

    @Test
    public void shouldGetRequests() {
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setAmountOfItems(5);
        paginationDTO.setOffset(0);
        List<Request> actual = requestDao.getRequests(RequestStatus.INPROGRESS.getName(), paginationDTO);

        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void shouldGetUserRequestsNumber() throws RequestException {
        int actual = requestDao.getUserRequestsNumber(RequestStatus.INPROGRESS.getName());

        Assert.assertTrue(actual != 0);
    }

    @Test
    public void shouldUpdatePaymentStatus() throws SQLException, RequestException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean actual = requestDao.updateRequestStatus(9, RequestStatus.DONE.getName(), connection);

        verify(preparedStatement).setString(1, RequestStatus.DONE.getName());
        verify(preparedStatement).setInt(2, 9);
        Assert.assertTrue(actual);
    }

    @Test
    public void shouldPerformRequest() throws RequestException {
        boolean actual = requestDao.performRequest(9, CountStatus.OPENED.getName(), RequestStatus.INPROGRESS.getName());

        Assert.assertTrue(actual);
    }

}