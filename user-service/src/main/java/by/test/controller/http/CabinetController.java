package by.test.controller.http;

import by.test.core.dto.AboutUserDto;
import by.test.core.dto.AuthorizationDto;
import by.test.core.dto.ChangePasswordDto;
import by.test.core.dto.UserDto;
import by.test.service.UserHolder;
import by.test.service.api.ICabinetService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cabinet")
public class CabinetController {

    private final ICabinetService cabinetService;

    public CabinetController(ICabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<?> create(@RequestBody UserDto userDto) {
        cabinetService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/verification")
    public ResponseEntity<?> get(@RequestParam("code") String code,
                                 @RequestParam("email") String email) {
        cabinetService.verification(code, email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> authorization(@RequestBody AuthorizationDto authorizationDTO) {

        String authorization = cabinetService.authorization(authorizationDTO);
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, authorization)
            .build();
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> exit(){
        /*
        Можно сделать сервис по удаления jwt
        токена из актуальных и добавление в
        черный список после выхода пользователя
        из аккаунта c датой, временем и прочим.
        */
        return ResponseEntity.ok().body("Вы успешно вышли из аккаунта!");
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody UserDto userDTO){
        String currentUser = UserHolder.getUser().getUsername();

        if(currentUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        cabinetService.updateInfo(currentUser, userDTO);
        return ResponseEntity.ok(
            "Вы: "
                + currentUser
                + " "
                + userDTO.getFio()
                + "успешно обновили свой профиль");
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<?> changePasswordUser(@RequestBody ChangePasswordDto changePasswordDto){
        cabinetService.changePassword(changePasswordDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/me")
    public ResponseEntity get() {
        AboutUserDto aboutUserDTO = cabinetService.getInfoMe();
        return ResponseEntity.status(HttpStatus.CREATED).body(aboutUserDTO);
    }
}