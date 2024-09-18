package andender13.mazskinfinderapplication.repo;

import andender13.mazskinfinderapplication.entity.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeaponRepository extends JpaRepository<Weapon, Long> {
}
