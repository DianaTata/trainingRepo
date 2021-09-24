package com.epam.rd.java.basic.finalProject.tag;

import org.junit.Assert;
import org.junit.Test;

import javax.servlet.jsp.JspException;

public class DateTagTest {

    private DateTag dateTag = new DateTag();

    @Test
    public void shouldDoStartTag() throws JspException {
        Assert.assertEquals(0, dateTag.doStartTag());
    }
}