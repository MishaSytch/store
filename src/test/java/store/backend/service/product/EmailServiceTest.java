package store.backend.service.product;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.ResourceUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EmailServiceTest {
    private final String to = "test@example.com";
    private final String subject = "Test Subject";
    private final String text = "Test Message";

    private GreenMail greenMail;
    private Session session;


    @Autowired
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(ServerSetupTest.SMTP);
        greenMail.start();
        session = greenMail.getSmtp().createSession();
    }

    @AfterEach
    public void tearDown() {
        greenMail.stop();
    }

    @Test
    void sendSimpleMessage() throws MessagingException, IOException {
        emailService.sendSimpleMessage(to, subject, text);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("from@example.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("to@example.com"));
        message.setSubject("Test Subject");
        message.setText("Test Message");

        Transport.send(message);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(1, receivedMessages.length);
        assertEquals("Test Subject", receivedMessages[0].getSubject());
        assertEquals("Test Message", receivedMessages[0].getContent().toString().trim());
    }

    @Test
    void sendMessageWithAttachment() throws IOException, javax.mail.MessagingException {
        String attachment = "src.test.resources.testAttachment.txt";
        emailService.sendMessageWithAttachment(to, subject, text, attachment);

        MimeMessage message = new MimeMessage(session);
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setFrom(new InternetAddress("from@example.com"));
        messageHelper.setTo(new InternetAddress("to@example.com"));
        messageHelper.setSubject("Test Subject");
        messageHelper.setText("Test Message");
        FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(attachment));
        messageHelper.addAttachment("Purchase order", file);

        Transport.send(message);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(1, receivedMessages.length);
        assertEquals("Test Subject", receivedMessages[0].getSubject());
    }
}