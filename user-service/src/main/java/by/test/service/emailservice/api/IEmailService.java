package by.test.service.emailservice.api;

import by.test.dao.entity.VerificationEntity;

import java.util.List;

public interface IEmailService {
    void sendEmailMessage(VerificationEntity verificationEntity, String title, String text);

    void sendEmailMessageForRoleAdmin(String title, String text, String emailUser, String emailAdmin);
}
