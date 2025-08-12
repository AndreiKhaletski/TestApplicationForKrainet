package by.test.service.emailservice;


import by.test.dao.entity.VerificationEntity;
import by.test.service.emailservice.api.IEmailService;
import by.test.service.emailservice.properties.EmailProperties;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements IEmailService {

    private final EmailProperties emailProperties;

    public EmailService(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Override
    public void sendEmailMessage(VerificationEntity verificationEntity, String title, String text) {

        Session session = Session.getInstance(emailProperties.getProps(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    emailProperties.getUsername(),
                    emailProperties.getPassword()
                );
            }
        });

        try {
            Message messageToUser = new MimeMessage(session);
            messageToUser.setFrom(new InternetAddress(emailProperties.getUsername()));
            messageToUser.setRecipients(Message.RecipientType.TO, InternetAddress.parse(verificationEntity.getEmail()));
            messageToUser.setSubject(title);
            messageToUser.setText(text + verificationEntity.getCode());

            Transport.send(messageToUser);

        } catch (MessagingException e) {
            throw new RuntimeException("Ошибка при отправке email: " + e.getMessage(), e);
        }
    }

    @Override
    public void sendEmailMessageForRoleAdmin(String title, String text, String emailUser, String emailAdmin) {

        Session session = Session.getInstance(emailProperties.getProps(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    emailProperties.getUsername(),
                    emailProperties.getPassword()
                );
            }
        });

        try {
            Message messageToUser = new MimeMessage(session);
            messageToUser.setFrom(new InternetAddress(emailProperties.getUsername()));
            messageToUser.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAdmin));
            messageToUser.setSubject(title);
            messageToUser.setText(text + emailUser);

            Transport.send(messageToUser);

        } catch (MessagingException e) {
            throw new RuntimeException("Ошибка при отправке email: " + e.getMessage(), e);
        }
    }
}