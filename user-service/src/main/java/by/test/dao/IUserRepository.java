package by.test.dao;

import by.test.core.enums.EnumRole;
import by.test.dao.entity.UserEntity;
import by.test.dao.projection.IEmailsAdminProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findAllByEmail(String email);

    Optional<UserEntity> findByUuid(UUID uuid);

    List<IEmailsAdminProjection> findAllByRole(EnumRole enumRole);
}
