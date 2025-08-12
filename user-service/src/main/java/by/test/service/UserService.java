package by.test.service;

import by.test.core.dto.UserDto;
import by.test.core.enums.EnumRole;
import by.test.core.enums.EnumStatusRegistration;
import by.test.dao.IUserRepository;
import by.test.dao.entity.UserEntity;
import by.test.dao.projection.IEmailsAdminProjection;
import by.test.service.api.IUserService;
import by.test.service.api.IVerificationService;
import by.test.service.convert.ConverterDTOToUserEntity;
import by.test.service.convert.ConverterEntityToUserDTO;
import by.test.service.emailservice.TextMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ConverterDTOToUserEntity entityConvert;
    private final ConverterEntityToUserDTO dtoConvert;
    private final PasswordEncoder encoder;
    private final IVerificationService verificationService;

    public UserService(IUserRepository userRepository,
                       ConverterDTOToUserEntity entityConvert,
                       ConverterEntityToUserDTO dtoConvert,
                       PasswordEncoder encoder,
                       IVerificationService verificationService) {
        this.userRepository = userRepository;
        this.entityConvert = entityConvert;
        this.dtoConvert = dtoConvert;
        this.encoder = encoder;
        this.verificationService = verificationService;
    }

    @Transactional
    @Override
    public void create(UserDto userDTO) {
        UserEntity userEntity = entityConvert.convert(userDTO);

        if (userRepository.findAllByEmail(userEntity.getEmail()) == null) {

            userEntity.setUuid(UUID.randomUUID());
            userEntity.setPassword(encoder.encode(userDTO.getPassword()));

            if (userEntity.getStatus() == EnumStatusRegistration.WAITING_ACTIVATION) {
                TextMessage textMessage = new TextMessage();

                verificationService.create(
                    userEntity.getEmail(),
                    textMessage.WELCOME_TITLE,
                    textMessage.WELCOME_TEXT);
            }

            userRepository.save(userEntity);
        } else {
            throw new IllegalArgumentException("Такой пользователь уже зарегистрирован!");
        }
    }

    @Override
    public Optional<UserEntity> findByMail(UUID uuid) {
        return userRepository.findByUuid(uuid);
    }

    @Transactional
    @Override
    public void update(UUID uuid, Long dtUpdate, UserDto userDTO) {

        Optional<UserEntity> optional = userRepository.findByUuid(uuid);
        UserEntity entity = optional.get();
        updateProperties(entity, userDTO);

        LocalDateTime updatedDateTime = Instant
            .ofEpochMilli(dtUpdate)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
        entity.setDt_update(updatedDateTime);
        if (!encoder.matches(userDTO.getPassword(), entity.getPassword())) {
            entity.setPassword(encoder.encode(userDTO.getPassword()));
        }
        userRepository.saveAndFlush(entity);

    }

    @Transactional
    @Override
    public UserEntity findByMail(String mail) {
        return userRepository.findAllByEmail(mail);
    }

    @Override
    public void save(UserEntity userEntity) {
        userRepository.saveAndFlush(userEntity);
    }

    @Override
    public void delete(UUID uuid) {
        if (userRepository.existsById(uuid)) {
            userRepository.deleteById(uuid);
        } else {
            throw new IllegalArgumentException("Такого пользователя нет");
        }
    }

    @Override
    public List<String> getUsersAdmins() {

        //Используем интерфейс, т.к. целые сущности нет смысла вытягивать.
        List<IEmailsAdminProjection> admins = userRepository.findAllByRole(EnumRole.ROLE_ADMIN);
        List<String> emails = new ArrayList<>();

        for (IEmailsAdminProjection admin : admins) {
            emails.add(admin.getEmail());
        }
        return emails;
    }


    private void updateProperties(UserEntity entity, UserDto dto) {
        entity.setEmail(dto.getEmail());
        entity.setFio(dto.getFio());
        entity.setRole(dto.getRole());
        entity.setStatus(dto.getStatus());
        entity.setPassword(dto.getPassword());
    }
}