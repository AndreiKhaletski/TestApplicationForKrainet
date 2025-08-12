package by.test.service;

import by.test.core.dto.AboutUserDto;
import by.test.core.dto.AuthorizationDto;
import by.test.core.dto.ChangePasswordDto;
import by.test.core.dto.UserDto;
import by.test.core.enums.EnumRole;
import by.test.core.enums.EnumStatusRegistration;
import by.test.dao.entity.UserEntity;
import by.test.service.api.ICabinetService;
import by.test.service.api.IUserService;
import by.test.service.api.IVerificationService;
import by.test.service.convert.ConverterEntityToUserDTO;
import by.test.service.emailservice.EmailService;
import by.test.service.emailservice.TextMessage;
import by.test.service.jwt.JwtTokenHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class CabinetService implements ICabinetService {

    private final IVerificationService verificationService;
    private final JwtTokenHandler jwtTokenHandler;
    private final IUserService userService;
    private final PasswordEncoder encoder;
    private final ConverterEntityToUserDTO dtoConvert;
    private final EmailService emailService;

    public CabinetService(IVerificationService verificationService,
                          JwtTokenHandler jwtTokenHandler,
                          IUserService userService,
                          PasswordEncoder encoder,
                          ConverterEntityToUserDTO dtoConvert, EmailService emailService) {
        this.verificationService = verificationService;
        this.jwtTokenHandler = jwtTokenHandler;
        this.userService = userService;
        this.encoder = encoder;
        this.dtoConvert = dtoConvert;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public void createUser(UserDto userDto) {
        userDto.setRole(EnumRole.ROLE_USER);
        userService.create(userDto);
    }

    @Override
    @Transactional
    public void verification(String code, String mail) {
        UserEntity userEntity = userService.findByMail(mail);

        if (Objects.equals(code, verificationService.get(mail))) {
            userEntity.setStatus(EnumStatusRegistration.ACTIVATED);
            userService.save(userEntity);

            //Отправляем уведомления каждому пользователю с ролью ADMIN
            List<String> usersWithRoleAdmin = userService.getUsersAdmins();

            for (String emailAdmin : usersWithRoleAdmin) {
                TextMessage textMessage = new TextMessage();

                emailService.sendEmailMessageForRoleAdmin(
                    textMessage.REGISTRATION_NOTIFICATION_TITLE,
                    textMessage.REGISTRATION_NOTIFICATION_TEXT,
                    userEntity.getEmail(),
                    emailAdmin);
            }
        } else {
            throw new IllegalArgumentException("Неверный код верификации");
        }
    }

    @Transactional
    @Override
    public String authorization(AuthorizationDto authorizationDTO) {
        UserEntity userEntity = userService.findByMail(authorizationDTO.getEmail());

        if (!encoder.matches(authorizationDTO.getPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("Неверный пользователь или пароль");
        }

        if (userEntity.getStatus() == EnumStatusRegistration.ACTIVATED) {

            String token = jwtTokenHandler.generateAccessToken(
                authorizationDTO.getEmail(),
                String.valueOf(userEntity.getRole()));

            return token;
        } else {
            throw new IllegalArgumentException("Необходимо подтвердить аккаунт для входа в систему");
        }
    }

    @Override
    public void updateInfo(String currentUser, UserDto userDTO) {
        UserEntity userEntity = userService.findByMail(currentUser);

        if(userEntity != null){
            if(userDTO.getEmail() != null){
                userEntity.setEmail(userDTO.getEmail());
            }
            if (userDTO.getFio() != null) {
                userEntity.setFio(userDTO.getFio());
            }
        }else {
            throw new IllegalArgumentException("Пользователь не найден!");
        }

        userService.save(userEntity);
    }

    @Override
    public AboutUserDto getInfoMe() {
        AboutUserDto dto = dtoConvert.convertAboutUser(userService.findByMail(UserHolder.getUser().getUsername()));
        return dto;
    }

    @Transactional
    @Override
    public void changePassword(ChangePasswordDto changePasswordDto) {
        UserEntity userEntity = userService.findByMail(UserHolder.getUser().getUsername());

        if (!encoder.matches(changePasswordDto.getCurrentPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("Текущий пароль не совпадает с паролем вашего аккаунта!");
        }

        if (Objects.equals(changePasswordDto.getNewPassword(), changePasswordDto.getConfirmNewPassword())) {
            userEntity.setPassword(encoder.encode(changePasswordDto.getNewPassword()));
            userService.save(userEntity);
        } else {
            throw new IllegalArgumentException("Поля нового пароля не совпадают!");
        }
    }

    @Override
    public void exit(UUID uuid) {

    }
}