package by.test.core.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationDto {
    @NotBlank(message = "Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз")
    private String email;
    @NotBlank(message = "Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз")
    private String password;
}
