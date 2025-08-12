package by.test.controller.http;

import by.test.core.dto.AboutUserDto;
import by.test.core.dto.UserDto;
import by.test.dao.entity.UserEntity;
import by.test.service.api.IUserService;
import by.test.service.convert.ConverterEntityToUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;
    private final ConverterEntityToUserDTO convertToDto;


    public UserController(IUserService userService,
                          ConverterEntityToUserDTO convertToDto) {
        this.userService = userService;
        this.convertToDto = convertToDto;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDto userDTO){
        userService.create(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<AboutUserDto> get(@PathVariable("uuid") UUID uuid){
        Optional<UserEntity> accountEntity = (userService.findByMail(uuid));
        if(accountEntity.isPresent()){
            return ResponseEntity.ok(convertToDto.convertAboutUser(accountEntity.get()));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<?> put(@PathVariable("uuid") UUID uuid,
                                 @PathVariable("dt_update") Long dtUpdate,
                                 @RequestBody UserDto userDTO){
        userService.update(uuid, dtUpdate, userDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<?> delete(@PathVariable("uuid") UUID uuid){
        userService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}