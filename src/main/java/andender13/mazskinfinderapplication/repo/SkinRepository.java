package andender13.mazskinfinderapplication.repo;

import andender13.mazskinfinderapplication.entity.Skin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkinRepository extends JpaRepository<Skin, Long> {
    List<Skin> findAllByGunType(String gunType);

    @Query("SELECT DISTINCT s.gunType FROM Skin s")
    List<String> findDistinctGunTypes();

    @Query("SELECT DISTINCT s.name FROM Skin s WHERE s.gunType =:gunType")
    List<String> findSkinNamesByGunType(@Param("gunType") String gunType);
}
