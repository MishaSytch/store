package store.backend.service.product;

import java.io.FileNotFoundException;

public interface IEmailService {

    void sendSimpleMessage(final String toAddress, final String subject, final String message);
    void sendMessageWithAttachment(final String toAddress, final String subject, final String message, final String attachment) throws FileNotFoundException, javax.mail.MessagingException;
}
