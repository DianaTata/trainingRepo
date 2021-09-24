package com.epam.rd.java.basic.finalProject.service.impl;

import com.epam.rd.java.basic.finalProject.dto.PaymentDTO;
import com.epam.rd.java.basic.finalProject.exception.SendEmailException;
import com.epam.rd.java.basic.finalProject.service.SendEmailService;
import com.epam.rd.java.basic.finalProject.util.PDFGenerator;
import com.epam.rd.java.basic.finalProject.util.PropertiesUtils;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SendEmailServiceImpl implements SendEmailService {

    private static final Logger LOGGER = Logger.getLogger(SendEmailServiceImpl.class);
    private static final String REPORT_PREFIX = "reports/report";
    private static final String POSTFIX = ".pdf";

    @Override
    public boolean sendEmailWithPdf(PaymentDTO paymentDTO) throws SendEmailException{
        LOGGER.warn("started sendEmailWithPdf");
        ByteArrayInputStream stream = PDFGenerator.generateReport(paymentDTO);
        String fileName = writeToFile(paymentDTO, stream);
        //String to = paymentDTO.getUser().getEmail();
        String to = "diana51187@gmail.com";
        Properties properties = PropertiesUtils.getProperties("app.properties");
        String from = properties.getProperty("email.from");
        String password = properties.getProperty("email.from.pass");
        Properties props = configureProperties();
        Session session = configureSession(props, from, password);
        return configureEmail(fileName, to, from, session);
    }

    private boolean configureEmail(String fileName, String to, String from, Session session) {
        boolean isSuccess = true;
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Payment report");
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Payment report");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(fileName);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            isSuccess = false;
        }
        return isSuccess;
    }

    private Session configureSession(Properties props, String sender, String password) {
        return Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sender, password);
                    }
                });
    }

    private String writeToFile(PaymentDTO paymentDTO, ByteArrayInputStream stream) {
        byte[] bytes = stream.readAllBytes();
        String fileName = REPORT_PREFIX + paymentDTO.getPaymentNumber() + POSTFIX;
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(bytes);
        } catch (IOException e) {
            LOGGER.warn("Can't write to file");
        }
        return fileName;
    }

    private Properties configureProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }
}
