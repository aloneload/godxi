package godxi.repository;

import godxi.domain.Work;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Work entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {

}
