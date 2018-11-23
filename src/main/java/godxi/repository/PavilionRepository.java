package godxi.repository;

import godxi.domain.Pavilion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pavilion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PavilionRepository extends JpaRepository<Pavilion, Long> {

}
