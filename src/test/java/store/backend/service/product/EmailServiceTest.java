package store.backend.service.product;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class EmailServiceTest {
    private final String to = "misha.sytch@mail.ru";
    private final String subject = "Test Subject";
    private final String text = "Test Message";

    private GreenMail greenMail;

    @Autowired
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(ServerSetupTest.SMTP);
        greenMail.start();
    }

    @Test
    void sendSimpleMessage() {
        emailService.sendSimpleMessage(to, subject, text);

        assertThat(greenMail.getReceivedMessages()).hasSize(1);
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        try {
            assertThat(receivedMessage.getSubject()).isEqualTo(subject);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        try {
            assertThat(receivedMessage.getContent().toString()).contains(text);
        } catch (IOException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void sendMessageWithAttachment() throws FileNotFoundException, javax.mail.MessagingException {
        String attachment = "src.test.resources.testAttachment.txt";
        emailService.sendMessageWithAttachment(to, subject, text, attachment);

        assertThat(greenMail.getReceivedMessages()).hasSize(1);
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        try {
            assertThat(receivedMessage.getSubject()).isEqualTo(subject);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        try {
            assertThat(receivedMessage.getContent().toString()).contains(text);
        } catch (IOException | MessagingException e) {
            throw new RuntimeException(e);
        }

        assertThat(greenMail.getReceivedMessages()).hasSize(1);

    }
}