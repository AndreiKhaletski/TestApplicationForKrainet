package by.test.core.dto;

import by.test.core.enums.EnumRole;
import by.test.core.enums.EnumStatusRegistration;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AboutUserDto {
    private UUID uuid;
    private Long dt_create;
    private Long dt_update;
    private String email;
    private String fio;
    private EnumRole role;
    private EnumStatusRegistration status;
    private String duration;
}