package com.epam.rd.java.basic.finalProject.tag;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DateTag extends TagSupport {

    private static final Logger LOGGER = Logger.getLogger(DateTag.class);
    private static final long serialVersionUID = 1L;

    @Override
    public int doStartTag() throws JspException {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = new Date();
            if (Objects.nonNull(pageContext)) {
                pageContext.getOut().print("<b>" + dateFormat.format(date) + "</b>");
            } else {
                LOGGER.warn("pageContext.getOut = null");
            }
        } catch (IOException ioException) {
            throw new JspException("Error: " + ioException.getMessage());
        }
        return SKIP_BODY;
    }
}