package store.backend.service.product;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface IEmailService {

    void sendSimpleMessage(final String toAddress, final String subject, final String message);
    void sendMessageWithAttachment(final String toAddress, final String subject, final String message, final String attachment);
}
