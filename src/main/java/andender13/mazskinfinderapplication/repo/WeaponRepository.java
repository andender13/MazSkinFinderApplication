package andender13.mazskinfinderapplication.repo;

import andender13.mazskinfinderapplication.entity.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeaponRepository extends JpaRepository<Weapon, Long> {
    @Query("SELECT w FROM Weapon w JOIN FETCH w.skin where w.user.username =:username")
    List<Weapon> findAllByUserIdWithSkins(@Param("username") String username);

}
