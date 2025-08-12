package by.test.dao;

import by.test.dao.entity.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVerificationRepository extends JpaRepository<VerificationEntity, String> {
    VerificationEntity findByEmail(String email);
}
