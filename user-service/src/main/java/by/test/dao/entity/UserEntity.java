package by.test.dao.entity;

import by.test.core.enums.EnumRole;
import by.test.core.enums.EnumStatusRegistration;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "app")
public class UserEntity {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @CreationTimestamp
    @Column(name = "dt_create")
    private LocalDateTime dt_create;

    @UpdateTimestamp
    @Version
    @Column(name = "dt_update")
    private LocalDateTime dt_update;

    @Column(name = "email")
    private String email;
    @Column(name = "fio")
    private String fio;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private EnumRole role;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EnumStatusRegistration status;
    @Column(name = "password")
    private String password;

    public UserEntity() {
    }

    public UserEntity(UUID uuid,
                      LocalDateTime dt_create,
                      LocalDateTime dt_update,
                      String email,
                      String fio,
                      EnumRole role,
                      EnumStatusRegistration status,
                      String password) {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
        this.email = email;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDt_create() {
        return dt_create;
    }

    public void setDt_create(LocalDateTime dt_create) {
        this.dt_create = dt_create;
    }

    public LocalDateTime getDt_update() {
        return dt_update;
    }

    public void setDt_update(LocalDateTime dt_update) {
        this.dt_update = dt_update;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public EnumRole getRole() {
        return role;
    }

    public void setRole(EnumRole role) {
        this.role = role;
    }

    public EnumStatusRegistration getStatus() {
        return status;
    }

    public void setStatus(EnumStatusRegistration status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

