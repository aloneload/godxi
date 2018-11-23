package godxi.repository;

import godxi.domain.GodxiLicense;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GodxiLicense entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GodxiLicenseRepository extends JpaRepository<GodxiLicense, Long> {

}
