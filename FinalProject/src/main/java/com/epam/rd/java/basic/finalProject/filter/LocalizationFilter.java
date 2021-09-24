package com.epam.rd.java.basic.finalProject.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class LocalizationFilter implements Filter {

    private String defaultLocale;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultLocale = filterConfig.getInitParameter("default-locale");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String lang = req.getParameter("lang");
        Locale locale = configureLocale(req, lang);
        req.getSession().setAttribute("locale", locale);
        chain.doFilter(request, response);
    }

    private Locale configureLocale(HttpServletRequest req, String lang) {
        Locale locale;
        if (StringUtils.isNotBlank(lang)) {
            locale = new Locale(lang);
        } else {
            locale = (Locale) req.getSession().getAttribute("locale");
            if (Objects.isNull(locale)) {
                locale = new Locale(defaultLocale);
            }
        }
        return locale;
    }

    @Override
    public void destroy() {

    }
}
