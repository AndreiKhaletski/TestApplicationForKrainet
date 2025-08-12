package by.test.service.api;

import by.test.core.dto.AboutUserDto;
import by.test.core.dto.AuthorizationDto;
import by.test.core.dto.ChangePasswordDto;
import by.test.core.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ICabinetService {

    void verification(String code, String email);

    String authorization(AuthorizationDto authorizationDTO);

    AboutUserDto getInfoMe();

    void changePassword(ChangePasswordDto changePasswordDto);

    void exit(UUID uuid);

    void createUser(UserDto userDto);

    void updateInfo(String currentUser, UserDto userDTO);
}
