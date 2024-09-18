package andender13.mazskinfinderapplication.repo;

import andender13.mazskinfinderapplication.entity.User;
import andender13.mazskinfinderapplication.enums.AuthorizationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") AuthorizationStatus status);

    Optional<User> findUserByStatus(AuthorizationStatus status);

    Optional<User> findUserByUsernameAndStatus(String username, AuthorizationStatus status);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.telegram = :telegram WHERE u.id = :id")
    void updateTelegramById(@Param("id") Long id, @Param("telegram") String telegram);
}