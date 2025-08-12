package by.test.service.convert;

import by.test.core.dto.UserDto;
import by.test.dao.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ConverterDTOToUserEntity {

    public UserEntity convert(UserDto item){
        UserEntity userEntity = new UserEntity();
        userEntity.setUuid(item.getUuid());
        userEntity.setEmail(item.getEmail());
        userEntity.setFio(item.getFio());
        userEntity.setRole(item.getRole());
        userEntity.setStatus(item.getStatus());
        return userEntity;
    }
}
