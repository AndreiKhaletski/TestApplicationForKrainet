package by.test.service.api;

import by.test.core.dto.UserDto;
import by.test.dao.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    void create(UserDto userDTO);

    Optional<UserEntity> findByMail(UUID uuid);

    void update(UUID uuid, Long dtUpdate, UserDto userDTO);

    UserEntity findByMail(String email);

    void save (UserEntity userEntity);

    void delete(UUID uuid);

    List<String> getUsersAdmins();

}
