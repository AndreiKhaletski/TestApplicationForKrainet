package by.test.service.convert;

import by.test.core.dto.AboutUserDto;
import by.test.core.dto.UserDto;
import by.test.dao.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class ConverterEntityToUserDTO {

    public UserDto convertUser(UserEntity item){
        UserDto userDTO = new UserDto();
        userDTO.setUuid(item.getUuid());
        userDTO.setDt_create(item.getDt_create().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        userDTO.setDt_update(item.getDt_update().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        userDTO.setEmail(item.getEmail());
        userDTO.setFio(item.getFio());
        userDTO.setRole(item.getRole());
        userDTO.setStatus(item.getStatus());
        return userDTO;
    }


    public AboutUserDto convertAboutUser(UserEntity item){
        AboutUserDto userAboutDTO = new AboutUserDto();
        userAboutDTO.setUuid(item.getUuid());
        userAboutDTO.setDt_create(item.getDt_create().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        userAboutDTO.setDt_update(item.getDt_update().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        userAboutDTO.setEmail(item.getEmail());
        userAboutDTO.setFio(item.getFio());
        userAboutDTO.setRole(item.getRole());
        userAboutDTO.setStatus(item.getStatus());
        return userAboutDTO;
    }
}
