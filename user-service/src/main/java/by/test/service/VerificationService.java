package by.test.service;

import by.test.core.enums.EnumStatusSendEmail;
import by.test.dao.IVerificationRepository;
import by.test.dao.entity.VerificationEntity;
import by.test.service.api.IVerificationService;
import by.test.service.emailservice.TextMessage;
import by.test.service.emailservice.api.IEmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.ExecutorService;

@Service
@Transactional(readOnly = true)
public class VerificationService implements IVerificationService {

    private final IVerificationRepository verificationRepository;
    private final IEmailService emailService;
    private final ExecutorService executorService;

    public VerificationService(IVerificationRepository verificationRepository,
                               IEmailService emailService,
                               ExecutorService executorService) {
        this.verificationRepository = verificationRepository;
        this.emailService = emailService;
        this.executorService = executorService;
    }

    @Override
    @Transactional
    public void create(String mail, String title, String text) {
        VerificationEntity verificationEntity = new VerificationEntity();
        verificationEntity.setEmail(mail);
        verificationEntity.setCode(String.valueOf(new Random().nextInt(100000)));
        verificationEntity.setStatus(EnumStatusSendEmail.LOADED);
        verificationRepository.saveAndFlush(verificationEntity);

        executorService.submit(() -> {
            try {
                emailService.sendEmailMessage(verificationEntity, title, text);
                verificationEntity.setStatus(EnumStatusSendEmail.OK);
                verificationRepository.saveAndFlush(verificationEntity);

            }catch (Exception e){
                verificationEntity.setStatus(EnumStatusSendEmail.ERROR);
                verificationRepository.saveAndFlush(verificationEntity);

                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public String get(String mail) {
        return verificationRepository.findByEmail(mail).getCode();
    }
}
