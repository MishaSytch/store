package store.backend.service.product;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import java.io.FileNotFoundException;

public interface EmailService {

    void sendSimpleEmail(final String toAddress, final String subject, final String message);
    void sendEmailWithAttachment(final String toAddress, final String subject, final String message, final String attachment) throws MessagingException, FileNotFoundException, javax.mail.MessagingException;
}
