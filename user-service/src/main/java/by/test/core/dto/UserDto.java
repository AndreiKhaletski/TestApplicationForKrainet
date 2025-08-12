package by.test.core.dto;

import by.test.core.enums.EnumRole;
import by.test.core.enums.EnumStatusRegistration;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID uuid;
    private Long dt_create;
    private Long dt_update;
    @NotBlank(message = "Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз")
    private String email;
    private String fio;
    private EnumRole role;
    private EnumStatusRegistration status;
    @NotBlank(message = "Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз")
    private String password;
}
